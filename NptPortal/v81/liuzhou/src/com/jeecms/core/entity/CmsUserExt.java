package com.jeecms.core.entity;

import org.apache.commons.lang.StringUtils;

import com.jeecms.core.entity.base.BaseCmsUserExt;

public class CmsUserExt extends BaseCmsUserExt {
	private static final long serialVersionUID = 1L;

    //生成一个新对象
    public CmsUserExt copyToNewUserExt() {
        CmsUserExt cmsUserExt=new CmsUserExt();

        cmsUserExt.setId(this.getId());
        cmsUserExt.setIdCard(this.getIdCard());
        cmsUserExt.setIdCardImg(this.getIdCardImg());
        cmsUserExt.setRealname(this.getRealname());
        cmsUserExt.setType(this.getType());
        cmsUserExt.setFlag(this.getFlag());
        cmsUserExt.setMobile(this.getMobile());

        cmsUserExt.setUserImg(this.getUserImg());
        cmsUserExt.setPhone(this.getPhone());
        cmsUserExt.setUserSignature(this.getUserSignature());
        cmsUserExt.setQq(this.getQq());
        cmsUserExt.setGender(this.getGender());
        cmsUserExt.setBirthday(this.getBirthday());
        cmsUserExt.setIntro(this.getIntro());
        cmsUserExt.setComefrom(this.getComefrom());
        cmsUserExt.setMsn(this.getMsn());
        cmsUserExt.setFailComment(this.getFailComment());

        // one to one
        cmsUserExt.setUser(this.getUser());

        return cmsUserExt;
    }

    public void blankToNull() {
		// 将空串设置为null，便于前台处理。
		if (StringUtils.isBlank(getRealname())) {
			setRealname(null);
		}
		if (StringUtils.isBlank(getIntro())) {
			setIntro(null);
		}
		if (StringUtils.isBlank(getComefrom())) {
			setComefrom(null);
		}
		if (StringUtils.isBlank(getMobile())) {
			setMobile(null);
		}
		if (StringUtils.isBlank(getPhone())) {
			setPhone(null);
		}
		if (StringUtils.isBlank(getMsn())) {
			setMsn(null);
		}
		if (StringUtils.isBlank(getQq())) {
			setQq(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsUserExt () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsUserExt (Integer id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}