package com.npt.dsp.dao;

import java.util.List;
import java.util.Map;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/28 12:15
 * 备注：
 */
public interface NptDataScanDao {

    List<Map<String,String>> loadDataTypeByOrg(String orgCode);

    List<Map<String,String>> loadDataField(String dataDBName);

    void backupDataTypeAndFields();
}
