package com.jeecms.core.manager.impl;

import com.jeecms.cms.action.member.MemberAct;
import com.jeecms.common.hibernate4.Updater;
import com.jeecms.common.web.springmvc.RealPathResolver;
import com.jeecms.core.dao.CmsUserExtDao;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.CmsUserExt;
import com.jeecms.core.manager.CmsUserExtMng;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.sync.entity.CreditCmsUser;
import com.npt.bridge.util.ImageBase64Utils;
import com.npt.bridge.util.NptHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class CmsUserExtMngImpl implements CmsUserExtMng {

    @Autowired
    private RealPathResolver realPathResolver;

	@Transactional(readOnly = true)
	public CmsUserExt findById(Integer userId){//解密用户电话身份证

		return DecryptUserExt(dao.findById(userId));
	}
	
	public CmsUserExt save(CmsUserExt ext, CmsUser user) {
	    user=encryptUser(user);
		ext.blankToNull();
		ext.setUser(user);
		ext=encryptUserExt(ext);//加密保存
		dao.save(ext);

		return DecryptUserExt(ext);//解密返回
	}

    public CmsUserExt updateUser(CmsUserExt ext, CmsUser user) {
        CmsUserExt entity = dao.findById(user.getId());//加密的
        if (entity == null) {
            entity = save(ext, user);//加解密
            user.getUserExtSet().add(entity);
            return entity;
        } else {
            ext=encryptUserExt(ext);//加密
            int sum =dao.updateUser(ext);
            CmsUserExt cmsUserExt= DecryptUserExt(ext);//解密
            if(sum==1){
                return cmsUserExt;
            }

            return null;//解密
        }
    }

	public CmsUserExt update(CmsUserExt ext, CmsUser user) {
		CmsUserExt entity = dao.findById(user.getId());//加密的
		if (entity == null) {
			entity = save(ext, user);//加解密
			user.getUserExtSet().add(entity);
			return entity;
		} else {
            ext=encryptUserExt(ext);//加密
            Updater<CmsUserExt> updater = new Updater<CmsUserExt>(ext);
            //	updater.include("gender");
            //	updater.include("birthday");
            ext = dao.updateByUpdater(updater);
            ext.blankToNull();

			return DecryptUserExt(ext);//解密
		}
	}

    public  boolean mobileExist(String mobile){
	    mobile=NptHttpUtil.encode(mobile);
	    CmsUserExt ext=dao.mobileExist(mobile);
	    if(ext==null){
	        return true;
        }

        return false;
    }

    //更新数据时校验
    public  boolean mobileExist(String mobile,Integer extId){
        mobile=NptHttpUtil.encode(mobile);
        CmsUserExt ext=dao.mobileExist(mobile);
        if(ext==null){
            return true;
        }
        if(ext.getId().equals(extId)){
            return true;
        }

        return false;
    }


    public  boolean idCardExist(String idCard){
        idCard=NptHttpUtil.encode(idCard);
        CmsUserExt ext=dao.idCardExist(idCard);
        if(ext==null){
            return true;
        }

        return false;
    }


    //更新数据时校验
    public  boolean idCardExist(String idCard,Integer extId){
        idCard=NptHttpUtil.encode(idCard);
        CmsUserExt ext=dao.idCardExist(idCard);
        if(ext==null){
            return true;
        }
        if(ext.getId().equals(extId)){
            return true;
        }

        return false;
    }

    public List<CmsUserExt> getSyncUser(){

        return DecryptUserExtList(dao.getSyncUser());
    }


    public boolean updateSyncUser(int id,String time,String flag){

        int sum=dao.updateSyncUser(id,time,flag);
        if(sum==1){
            return true;
        }

        return false;
    }

    public boolean updateSyncUserResult(int id,String time,String syncFlag,String flag,String failComment){

        int sum=dao.updateSyncUserResult(id,time,syncFlag,flag,failComment);
        if(sum==1){
            return true;
        }

        return false;
    }


    public List<CreditCmsUser> getSyncUserList(){
        List<CmsUserExt> list = getSyncUser();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int len=list.size();
        List<CreditCmsUser> listU = new ArrayList<CreditCmsUser>();

        for (int i = 0; i <len ; i++) {
            CreditCmsUser user = new CreditCmsUser();
            CmsUserExt ext=list.get(i);
            user.setId(ext.getId());
            user.setRealname(ext.getRealname());
            user.setMobile(ext.getMobile());

            user.setIdCard(ext.getIdCard());
            user.setIdCardImg(ext.getIdCardImg());
            user.setType(ext.getType());
            user.setFlag(ext.getFlag());
            user.setFailComment(ext.getFailComment());

            user.setMapFile(getSyncUserImg(ext.getId(),ext.getIdCardImg(),ext.getIdCard()));
            if(user.getMapFile()!=null){
                listU.add(user);
            }else{
                updateSyncUser(ext.getId(),formatter.format(new Date()), NptDict.RCS_FAILSYNC.getCode()+"");
            }

        }

        return listU;
    }

    public Map<String,String> getSyncUserImg(int id,String idCardImg,String idCard){
        String[] fileName = idCardImg.split(MemberAct.IMG_PATH_SPLIT);
        int len =fileName.length;
        Map<String,String> map=new HashMap<String,String>();

        for (int i = 0; i < len; i++) {
            boolean isget=true;
            String file= null;
            try {
                file = ImageBase64Utils.imageToBase64(realPathResolver.get(MemberAct.PATH+idCard+fileName[i]));
            } catch (IOException e) {
                e.printStackTrace();
                isget=false;
            }
            if(isget) {
                map.put(fileName[i], file);
            }else{
               return null;
            }

        }
        return map;
    }

    public boolean changeMobile(String mobile,Integer id){
        mobile=NptHttpUtil.encode(mobile);
        int sum=dao.changeMobile(mobile,id);
        if(sum==1){
            return true;
        }

        return false;
    }

    //认证状态
    public boolean changeflag(Integer id,String flag,String msg){
        int sum=dao.changeflag(id,flag,msg);
        if(sum==1){
            return true;
        }

        return false;
    }

    @Override
    public boolean realNameExist(String realname, Integer extId) {
        CmsUserExt ext=dao.realNameExist(realname);
        if(ext==null){
            return true;
        }
        if(ext.getId().equals(extId)){
            return true;
        }

        return false;
    }

    @Override
    public boolean realNameExist(String realname) {
        CmsUserExt ext=dao.realNameExist(realname);
        if(ext==null){
            return true;
        }

        return false;
    }

    private CmsUserExtDao dao;

	@Autowired
	public void setDao(CmsUserExtDao dao) {
		this.dao = dao;
	}


    private static  List<CmsUserExt> DecryptUserExtList(List<CmsUserExt> list){
        List<CmsUserExt> list2 = new ArrayList<CmsUserExt>();
        int len = list.size();
        for (int i = 0; i < len; i++) {
            CmsUserExt user = list.get(i);
            list2.add(DecryptUserExt(user));
        }
        return list2;
    }
    private static  CmsUserExt DecryptUserExt(CmsUserExt userExt){
        if(userExt==null){
            return userExt;
        }
        CmsUserExt ext=userExt.copyToNewUserExt();
        String idCard = ext.getIdCard();
        String mobile = ext.getMobile();
        try {
            if(idCard!=null && !"".equals(idCard)){
                ext.setIdCard(NptHttpUtil.decode(idCard));
            }
            if(mobile!=null && !"".equals(mobile)){
                ext.setMobile(NptHttpUtil.decode(mobile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ext;
    }

    private static CmsUserExt encryptUserExt(CmsUserExt userExt){
        if(userExt==null){
            return userExt;
        }

        CmsUserExt ext=userExt.copyToNewUserExt();
        String idCard = ext.getIdCard();
        String mobile = ext.getMobile();
        try {
            if(idCard!=null && !"".equals(idCard)){
                ext.setIdCard(NptHttpUtil.encode(idCard));
            }
            if(mobile!=null && !"".equals(mobile)){
                ext.setMobile(NptHttpUtil.encode(mobile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ext;
    }

    public static  CmsUser DecryptUser(CmsUser user){//解密user扩展字段
        if(user==null){
            return user;
        }
        CmsUserExt ext=user.getUserExt();
        if(ext==null){
            return user;
        }

        //修改内部数据
        Set<CmsUserExt> set = user.getUserExtSet();
        set.clear();
        set.add(DecryptUserExt(ext));

        return user;
    }

    public static CmsUser encryptUser(CmsUser user){
        if(user==null){
            return user;
        }
        CmsUserExt ext=user.getUserExt();
        if(ext==null){
            return user;
        }

        //修改内部数据
        Set<CmsUserExt> set = user.getUserExtSet();
        if(set!=null){
            set.clear();
        }
        set.add(encryptUserExt(ext));

        return user;
    }
}