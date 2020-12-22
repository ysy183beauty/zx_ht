package com.npt.dsp.manager;

import com.npt.dsp.bean.NptDataScanResult;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/28 12:05
 * 备注：
 */
public interface NptDataScanManager {
     NptDataScanResult mountTables();

     NptDataScanResult mountColumns();
}
