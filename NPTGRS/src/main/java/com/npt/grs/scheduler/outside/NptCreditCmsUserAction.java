package com.npt.grs.scheduler.outside;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.sync.entity.CreditCmsUser;
import com.npt.bridge.sync.entity.CreditCmsUserResponse;
import com.npt.bridge.sync.entity.NptSyncBase;
import com.npt.bridge.util.ImageBase64Utils;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.credit.manager.CreditCmsUserManager;
import com.npt.grs.model.service.NptGRSBaseModelService;
import com.npt.rms.base.NptRmsCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.summer.extend.orm.Condition;
import org.summer.extend.orm.Pagination;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/22
 * 备注:
 */
@Controller("npt.grs.scheduler.outside.creditCmsUser")
public class NptCreditCmsUserAction extends NptSyncAction<CreditCmsUser, Integer> {

    private static final String PATH = "/WEB-INF/attachments/idCard/";
    
    @Resource(name = "rmsCommonService")
    public void setCommonService(NptRmsCommonService commonService) {
        super.commonService = commonService;
    }

    @Autowired
    public void setManager(CreditCmsUserManager manager) {
        super.manager = manager;
    }

    @Autowired
    private NptGRSBaseModelService baseModelService;

    private CreditCmsUser data;

    public CreditCmsUser getData() {
        return data;
    }

    public void setData(CreditCmsUser data) {
        this.data = data;
    }

    @Override
    protected Class<CreditCmsUser> getEntityClass() {
        return CreditCmsUser.class;
    }

    @Override
    protected NptDict getSyncDict() {
        return NptDict.RMT_SYNC_CMSUSER;
    }

    @Override
    protected NptDict getSyncOkDict() {
        return NptDict.RMT_SYNC_CMSUSER_OK;
    }

    @Override
    protected NptDict getSyncResponseDict() {
        return NptDict.RMT_SYNC_CMSUSER_REP;
    }

    @Override
    protected NptDict getSyncLogDict() {
        return NptDict.LGA_SYNC_CMSUSER;
    }

    @Override
    protected NptDict getSyncResponseLogDict() {
        return NptDict.LGA_SYNC_CMSUSER_REP;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/22 下午08:08
     * 备注:
     */
    public String index() {
        return list();
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/22 下午08:09
     * 备注:
     */
    public String list() {
        Integer beginIndex = getPageBeginIndex();
        Integer currPage = getPageCurrentPage();
        Integer pageSize = getPageSize();

        Collection<Condition> conditions = new ArrayList<>();
        String syncStatus = this.getParameter(NptSyncBase.PROPERTY_SYNC_STATUS);
        if (syncStatus != null && !syncStatus.isEmpty()) {
            if (Integer.parseInt(syncStatus) == NptDict.RCS_NOTSYNED.getCode()) {
                conditions.add(Conditions.eq(NptSyncBase.PROPERTY_SYNC_STATUS, NptDict.RCS_NOTSYNED.getCode()));
            } else {
                conditions.add(Conditions.ne(NptSyncBase.PROPERTY_SYNC_STATUS, NptDict.RCS_NOTSYNED.getCode()));
            }
        }

        conditions.add(Conditions.desc(NptSyncBase.PROPERTY_CREATE_TIME));

        Pagination<CreditCmsUser> dataPagination = manager.findAll(beginIndex, pageSize, conditions.toArray(new Condition[conditions.size()]));
        this.setAttribute("dataPagination", createPaginationResult(dataPagination, currPage, pageSize));
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/23 下午03:53
     * 备注:
     */
    public String editPage() {
        Integer id = this.getIntParameter(NptSyncBase.PROPERTY_ID);
        CreditCmsUser user = manager.findById(id);
        String idCard = user.getIdCard();
        List<String> idCardImgs = JSONArray.parseArray(user.getIdCardImg(), String.class);
        Map<String, String> mapFile = new HashMap<>();
        for (String idCardImg : idCardImgs) {
            String cardPath = this.getSession().getServletContext().getRealPath("") + PATH + idCard + idCardImg;
            if (new File(cardPath).exists()) {
                try {
                    mapFile.put(idCardImg, ImageBase64Utils.imageToBase64(cardPath));
                } catch (IOException e) {
                    mapFile.put(idCardImg, null);
                    e.printStackTrace();
                }
            } else {
                mapFile.put(idCardImg, null);
            }
        }
        user.setMapFile(mapFile);
        this.setAttribute("_USER", user);

        this.setAttribute("_USER_FLAG", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.USER_AUTH));

        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/23 下午04:17
     * 备注:
     */
    public void edit() {
        try {
            int id = data.getId();
            CreditCmsUser user = manager.findById(id);
            user.setFlag(data.getFlag());
            user.setFailComment(data.getFailComment());
            user.setSyncStatus(NptDict.RCS_NEEDSYNC.getCode());
            manager.update(user);
            this.outputSuccessResult();
        } catch (Exception e) {
            this.outputErrorResult("保存失败");
            e.printStackTrace();
        }
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 上午10:35
     * 备注:
     */
    @Override
    protected boolean updateInfo(CreditCmsUser old, CreditCmsUser info) {

        //1.保存照片
        boolean savePic = isSavePic(info);

        return savePic && super.updateInfo(old, info);
    }

    private boolean isSavePic(CreditCmsUser info) {
        boolean savePic = true;
        Map<String, String> mapFile = info.getMapFile();
        for (Map.Entry<String, String> entry : mapFile.entrySet()) {
            String filePath = NptCreditCmsUserAction.class.getClassLoader().getResource("").getPath().replace("/WEB-INF/classes/", PATH) + info.getIdCard() + entry.getKey();
            try {
                ImageBase64Utils.base64ToImageFile(entry.getValue(), filePath);
            } catch (IOException e) {
                savePic = false;
                e.printStackTrace();
            }
        }
        info.setIdCardImg(JSON.toJSONString(mapFile.keySet()));
        return savePic;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/21 下午08:42
     * 备注:
     */
    @Override
    protected boolean saveInfo(CreditCmsUser info) {
        //保存

        //1.保存照片
        boolean savePic = isSavePic(info);

        //2.保存用户
        return savePic && super.saveInfo(info);

    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/22 下午02:35
     * 备注:
     */
    @Override
    protected Object generateResponse(List<CreditCmsUser> infos) {
        //获取回复信息
        List<CreditCmsUserResponse> responseList = new ArrayList<>();
        for (CreditCmsUser info : infos) {
            CreditCmsUserResponse response = new CreditCmsUserResponse();
            response.setFlag(info.getFlag());
            response.setFailComment(info.getFailComment());
            response.setId(info.getId());
            responseList.add(response);
        }
        return responseList;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/30 下午04:42
     * 备注:
     */
    @Override
    protected JSONObject toJSON(CreditCmsUser user) {
        JSONObject result = new JSONObject();
        result.put("id", user.getId());
        result.put("realname", user.getRealname());
        result.put("mobile", user.getMobile());
        result.put("idCard", user.getIdCard());
        result.put("type", user.getType());
        result.put("flag", user.getFlag());
        result.put("failComment", user.getFailComment());
        result.put("mapFile", user.getMapFile());
        result.put("syncStatus", user.getSyncStatus());
        return result;
    }
}
