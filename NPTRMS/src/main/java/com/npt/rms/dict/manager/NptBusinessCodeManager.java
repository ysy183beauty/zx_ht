package com.npt.rms.dict.manager;

import com.npt.bridge.dict.NptBusinessCode;
import com.npt.rms.base.manager.NptCachedManager;

import java.util.Collection;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/17 21:24
 * 备注：
 */
public interface NptBusinessCodeManager extends NptCachedManager<NptBusinessCode> {

    /**
     *  码表最后一次同步时间
     */
    Long CODE_LAST_SYNC_STAMP_ID = -99999999L;

    /**
     *作者：OWEN
     *时间：2016/11/28 17:27
     *描述:
     *      通过码类型与码值获取唯一的码信息
     */
    NptBusinessCode getCode(String codeValue, Long fieldId);

    /**
     *作者：OWEN
     *时间：2016/11/28 17:28
     *描述:
     *      加载指定码类型的所有码信息
     */
    Collection<NptBusinessCode> listCode(Long fieldId);

    Collection<NptBusinessCode> listAll();

    Collection<NptBusinessCode> listIncrement(Date date);

    void updateSyncStamp(Date date);


    /**
     * 作者: 张磊
     * 日期: 2017/03/20 下午09:17
     * 备注: 删除并保存码表
     */
    void deleteAndSaveCode(Long fieldId, Collection<NptBusinessCode> codes);
}
