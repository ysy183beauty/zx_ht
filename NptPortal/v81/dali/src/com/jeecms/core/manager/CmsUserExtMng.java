package com.jeecms.core.manager;

import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.CmsUserExt;
import com.npt.bridge.sync.entity.CreditCmsUser;

import java.util.List;

public interface CmsUserExtMng {
	public CmsUserExt findById(Integer userId);
	
	public CmsUserExt save(CmsUserExt ext, CmsUser user);

	public CmsUserExt update(CmsUserExt ext, CmsUser user);

    public CmsUserExt updateUser(CmsUserExt ext, CmsUser user);

	public  boolean mobileExist(String mobile);

    public  boolean mobileExist(String mobile,Integer extId);

    public  boolean idCardExist(String idCard);

    public  boolean idCardExist(String idCard,Integer extId);

    public boolean changeMobile(String mobile,Integer id);

    public List<CmsUserExt> getSyncUser();

    public boolean updateSyncUser(int id,String time,String syncFlag);

    public boolean updateSyncUserResult(int id,String time,String syncFlag,String flag,String failComment);

    public List<CreditCmsUser> getSyncUserList();


    //认证状态
    public boolean changeflag(Integer id,String flag,String msg);

    boolean realNameExist(String realname, Integer id);

    boolean realNameExist(String realname);
}