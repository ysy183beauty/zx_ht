package com.credit.CreditCard.controller;

import com.alibaba.fastjson.JSONObject;
import com.credit.CreditCard.entity.NptCreditCard;
import com.credit.CreditCard.entity.NptCreditSearchLog;
import com.credit.CreditCard.service.NptCreditCardService;
import com.credit.CreditCard.user.NptCreditCardUser;
import com.credit.FTLBox.NPTCreditFTLBox;
import com.google.zxing.BarcodeFormat;
import com.jeecms.common.util.ZXingCode;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.common.web.springmvc.RealPathResolver;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.web.util.CmsUtils;
import com.npt.bridge.bean.NptPaginationData;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dataBinder.NptWebDetailBlock;
import com.npt.bridge.dataBinder.NptWebDetailGroup;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.bridge.util.RequestUtil;
import com.npt.querier.impl.NptCreditCardQuerierBase;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/6/13
 * 备注:
 */
@Controller
@RequestMapping(value = "/card")
public class NptCreditCardController {
    @Autowired
    private NptCreditCardQuerierBase querier;
    @Autowired
    private NptCreditCardService creditCardService;
    @Autowired
    private RealPathResolver realPathResolver;

    public static final String PATH = "/r/cms/www/red/img/card/";

    /**
     *  author: zhanglei
     *  date:   2017/06/14 16:10
     *  note:
     *          搜索
     */
    @RequestMapping(value = "/search.do", method = RequestMethod.GET)
    public String search(String key, Integer currentPage, Integer pageSize, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);
        NptPaginationData<NptCreditCard> paginationData = creditCardService.search(NptDict.PDM_UNKNOW, key, currentPage, pageSize);
        fillCardInfo(user, paginationData.getResults());
        modelMap.put("paginationData", paginationData);
        return NPTCreditFTLBox.FTL_CREDIT_CARD_SEARCH;
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/14 16:10
     *  note:
     *          名片广场首页
     */
    @RequestMapping(value = "/plaza.do", method = RequestMethod.GET)
    public String plaza(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);

        Collection<NptCreditSearchLog> hotSearch = creditCardService.getCreditHotSearch(30);

        Collection<NptCreditCard> bsHot = creditCardService.getCreditTalent(NptDict.PDM_ENTERPRISE, 5);
        Collection<NptCreditCard> psHot = creditCardService.getCreditTalent(NptDict.PDM_PERSONAL, 5);

        fillCardInfo(user, bsHot);
        fillCardInfo(user, psHot);

        modelMap.put("hotSearch", hotSearch);
        modelMap.put("bsHot", bsHot);
        modelMap.put("psHot", psHot);

        return NPTCreditFTLBox.FTL_CREDIT_CARD_PLAZA;
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/15 10:56
     *  note:
     *
     */
    private void fillCardInfo(CmsUser user, Collection<NptCreditCard> bsHot) {
        for (NptCreditCard card : bsHot) {
            // 关系
            if(user != null){
                card.setRelation(creditCardService.getRelation(user.getId(), card.getUserId()).getCode());
            }

            // 描述
            try {
                NptWebBridgeBean bean = new NptWebBridgeBean();
                bean.setPrimaryKeyValue(String.valueOf(card.getUkValue()));

                //TODO 获取索引字段的详情
                querier.loadBaseModelAuthGroupsByUK(bean, querier.getThisModel(card.getCardType()), false);

                List dataList = (List) bean.getDataList();
                NptWebDetailGroup nptWebDetailGroup = (NptWebDetailGroup) dataList.get(0);
                Collection<NptWebDetailBlock> blockList = nptWebDetailGroup.getBlockList();
                NptWebDetailBlock next = blockList.iterator().next();
                List dataArray = (List) next.getDataArray();
                NptWebFieldDataArray nptWebFieldDataArray = (NptWebFieldDataArray) dataArray.get(0);
                Iterator<NptWebFieldDataArray.NptWebFieldData> iterator = nptWebFieldDataArray.getDataArray().iterator();
                card.setCardDesc(iterator.next().getValue() + " " + iterator.next().getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/14 16:10
     *  note:
     *          我的名片首页
     */
    @RequestMapping(value = "/myCard.do", method = RequestMethod.GET)
    public String myCard(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);

        String domain = RequestUtil.getDomain(request);
        modelMap.put("domain", domain);
        if (user != null && CmsUtils.checkUserIsAuthentification(request)) {
            // 我的名片
            NptWebBridgeBean bean = new NptWebBridgeBean();
            NptCreditCard creditCard = creditCardService.findCreditCardById(user.getId());
            if(creditCard == null){
                NptCreditCardUser cardUser = new NptCreditCardUser();
                cardUser.setUserType(user.getType().equals("company") ? NptDict.PDM_ENTERPRISE.getCode() : NptDict.PDM_PERSONAL.getCode());
                cardUser.setUserIdCard(user.getIdCard());
                cardUser.setUserId(user.getId());
                cardUser.setVerified(true);
                NptDict nptDict = creditCardService.newCard(cardUser);
                if(!nptDict.equals(NptDict.RST_SUCCESS)){
                    return NPTCreditFTLBox.FTL_CREDIT_SYSTEM_ERROR;
                }
                creditCard = creditCardService.findCreditCardById(user.getId());
            }
            if (creditCard == null) {
                return NPTCreditFTLBox.FTL_CREDIT_SYSTEM_ERROR;
            }
            modelMap.put("creditCard", creditCard);

            // 二维码
            String uId = creditCard.getCardType() + "_" + creditCard.getUkValue();
            String url = domain + "credit/card/mCI.do?t=" + creditCard.getCardType() + "&k=" + creditCard.getUkValue();
            createCodeImg(uId, url);

            bean.setPrimaryKeyValue(String.valueOf(creditCard.getUkValue()));


            NptBaseModel model = querier.getThisModel(creditCard.getCardType());
            if (model == null) {
                return NPTCreditFTLBox.FTL_CREDIT_MODEL_NOT_FOUND;
            }

            //TODO 获取索引字段的详情
            querier.loadBaseModelAuthGroupsByUK(bean, model, false);

            // 信用名片
            createCardImg(creditCard, bean);

            // 关注，粉丝
            NptPaginationData<NptCreditCard> following = creditCardService.getFollowing(user.getId(), 1, 10);
            NptPaginationData<NptCreditCard> follower = creditCardService.getFollower(user.getId(), 1, 10);
            if (following.getTotalCount() > 0) {
                fillCardInfo(user, following.getResults());
            }
            if (follower.getTotalCount() > 0) {
                fillCardInfo(user, follower.getResults());
            }

            modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
            modelMap.put("following", following);
            modelMap.put("follower", follower);
        } else {
            modelMap.put("returnUrl", "/credit/card/myCard.do");
            return NPTCreditFTLBox.FTL_CREDIT_NOTVERIFIED;
        }

        return NPTCreditFTLBox.FTL_CREDIT_CARD_MYCARD;
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/15 15:52
     *  note:
     *          正在关注/关注的人分页
     */
    @RequestMapping(value = "/page.do", method = RequestMethod.GET)
    public String followPage(String followType, Integer currentPage, Integer pageSize, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);

        // 关注，粉丝
        NptPaginationData<NptCreditCard> following;
        if (followType.equals("following")) {
            following = creditCardService.getFollowing(user.getId(), currentPage, pageSize);
        } else {
            following = creditCardService.getFollower(user.getId(), currentPage, pageSize);
        }

        if (following.getTotalCount() > 0) {
            fillCardInfo(user, following.getResults());
        }

        modelMap.put("following", following);
        return NPTCreditFTLBox.FTL_CREDIT_CARD_LIST;
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/14 16:11
     *  note:
     *          关注某人
     */
    @RequestMapping(value = "/follow.do", method = RequestMethod.POST)
    public void follow(int toUserId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null && CmsUtils.checkUserIsAuthentification(request)) {
            NptDict result = creditCardService.follow(user.getId(), toUserId);
            ResponseUtils.renderJson(response, JSONObject.toJSONString(result));
        } else {
            ResponseUtils.renderJson(response, JSONObject.toJSONString(NptDict.RST_NOTVERIFIED));
        }
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/14 16:11
     *  note:
     *          取消关注某人
     */
    @RequestMapping(value = "/unFollow.do", method = RequestMethod.POST)
    public void unFollow(int toUserId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null && CmsUtils.checkUserIsAuthentification(request)) {
            NptDict result = creditCardService.unFollow(user.getId(), toUserId);
            ResponseUtils.renderJson(response, JSONObject.toJSONString(result));
        } else {
            ResponseUtils.renderJson(response, JSONObject.toJSONString(NptDict.RST_NOTVERIFIED));
        }
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/15 13:50
     *  note:
     *          个人基本信息
     */
    @RequestMapping(value = "/mCI.do")
    public String cardInfo(int t, String k, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        NptBaseModel searchModel = querier.getThisModel(t);

        NptWebBridgeBean bean = new NptWebBridgeBean();
        bean.setPrimaryKeyValue(k);
        NptDict result = querier.loadBaseModelAuthGroupsByUK(bean, searchModel, false);
        modelMap.put("_RESULT", result);
        modelMap.put("_BEAN", bean);


        return NPTCreditFTLBox.FTL_CREDIT_CARD_INFO;
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/15 14:31
     *  note:
     *          创建信用名片二维码
     */
    private void createCodeImg(String uId, String content) {
        int[] sizeList = {40, 60, 100};
        try {
            FileUtils.forceMkdir(new File(realPathResolver.get(PATH)));
            for (int size : sizeList) {
                String tempFileName = PATH + uId + "@" + size + "x" + size + ".png";
                File file = new File(realPathResolver.get(tempFileName));
                if (!file.exists()) {
                    ZXingCode zp = ZXingCode.getInstance();
                    BufferedImage bim = zp.getQRCODEBufferedImage(content, BarcodeFormat.QR_CODE, size, size,
                            zp.getDecodeHintType());
                    ImageIO.write(bim, "png", file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/16 11:11
     *  note:
     *          创建信用名片图片
     * @param creditCard
     * @param bean
     */
    private void createCardImg(NptCreditCard creditCard, NptWebBridgeBean bean) {
        int width = 460;
        int height = 170;

        String uId = creditCard.getCardType() + "_" + creditCard.getUkValue();
        String cardFileName = PATH + uId + "_card.png";
        File file = new File(realPathResolver.get(cardFileName));
        if (!file.exists()) {
            try {
                AttributedString ats;

                //创建一个画布
                BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                //获取画布的画笔
                Graphics2D g2 = bi.createGraphics();

                //开始绘图
                g2.setBackground(Color.decode("0x999999"));
                g2.clearRect(0, 0, width, height);
                g2.setPaint(Color.WHITE);

                File qrFile = new File(realPathResolver.get(PATH + uId + "@60x60.png"));
                BufferedImage read = ImageIO.read(qrFile);


                if (creditCard.getImgPath() != null && new File(creditCard.getImgPath()).exists()) {
                    g2.drawImage(ImageIO.read(new File(creditCard.getImgPath())), 15, 20, 117, 130, null);
                    g2.drawImage(read, 390, 10, null);
                } else {
                    g2.drawImage(ImageIO.read(new File(realPathResolver.get("/r/cms/www/red/img/lz/xymp/u1140.png"))), 15, 20, 117, 130, null);
                    g2.drawImage(read, 390, 10, null);
                    ats = new AttributedString("头像");
                    ats.addAttribute(TextAttribute.FONT, new Font("微软雅黑", Font.PLAIN, 14));
                    g2.setColor(Color.BLACK);
                    g2.drawString(ats.getIterator(), 60, 89);
                    g2.setPaint(Color.WHITE);
                }

                Iterator<NptWebFieldDataArray.NptWebFieldData> iterator = ((NptWebFieldDataArray) ((List) (((NptWebDetailGroup) ((List) bean.getDataList()).get(0)).getBlockList().iterator().next().getDataArray())).get(0)).getDataArray().iterator();

                ats = new AttributedString(bean.getTitle());
                ats.addAttribute(TextAttribute.FONT, new Font("微软雅黑", Font.BOLD, 18));
                g2.drawString(ats.getIterator(), 147, 40);

                int i = 0;
                while (iterator.hasNext() && i++ < 5) {
                    NptWebFieldDataArray.NptWebFieldData next = iterator.next();
                    ats = new AttributedString(next.getTitle() + ": " + next.getValue());
                    ats.addAttribute(TextAttribute.FONT, new Font("微软雅黑", Font.PLAIN, 14));
                    g2.drawString(ats.getIterator(), 147, 20 * (i + 3));
                }

                g2.dispose();
                //将生成的图片保存为jpg格式的文件。ImageIO支持jpg、png、gif等格式
                ImageIO.write(bi, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}