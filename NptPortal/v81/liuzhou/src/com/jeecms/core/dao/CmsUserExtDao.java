package com.jeecms.core.dao;

import com.jeecms.common.hibernate4.Updater;
import com.jeecms.core.entity.CmsUserExt;

import java.util.List;

public interface CmsUserExtDao {
	public CmsUserExt findById(Integer id);

	public CmsUserExt save(CmsUserExt bean);

	public CmsUserExt updateByUpdater(Updater<CmsUserExt> updater);

    public CmsUserExt mobileExist(String mobile);

    public CmsUserExt idCardExist(String idCard);

    public int updateUser(CmsUserExt bean);

    public int changeMobile(String mobile,Integer id);


    public List<CmsUserExt> getSyncUser();

    public int updateSyncUser(int id,String time,String flag);

    public int updateSyncUserResult(int id,String time,String syncFlag,String flag,String failComment);

        //认证状态
    public int changeflag(Integer id,String flag,String msg);

    CmsUserExt realNameExist(String realname);
}