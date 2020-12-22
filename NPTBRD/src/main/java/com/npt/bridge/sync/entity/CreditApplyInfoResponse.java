package com.npt.bridge.sync.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/26
 * 备注:
 */
public class CreditApplyInfoResponse {
    private String tbName;
    private List<Object> tbColValues;

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }

    public List<Object> getTbColValues() {
        return tbColValues;
    }

    public void setTbColValues(List<Object> tbColValues) {
        this.tbColValues = tbColValues;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/26 下午05:51
     * 备注: 生成insert语句
     */
    public List<String> toInsertSql() {
        List<String> result = new ArrayList<>();
        StringBuilder sql;
        StringBuilder keys;
        StringBuilder values;
        if (tbColValues.size() == 0) {
            return result;
        }
        //获取字段名
        Map map = (Map) tbColValues.get(0);
        keys = new StringBuilder();
        for (Iterator iterator = map.keySet().iterator(); iterator.hasNext(); ) {
            String fieldName = String.valueOf(iterator.next());
            keys.append(fieldName);
            if (iterator.hasNext()) {
                keys.append(", ");
            }
        }
        //获取字段值
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //只获取一条数据
//        for (Object tbColValue : tbColValues) {
            map = (Map) tbColValues.get(0);
            values = new StringBuilder();
            for (Iterator iterator = map.values().iterator(); iterator.hasNext(); ) {
                Object fieldValue = iterator.next();
                if (fieldValue instanceof Timestamp) {
                    df.format((Timestamp) fieldValue);
                    values.append("to_date('").append(df.format((Timestamp) fieldValue)).append("','yyyy-mm-dd hh24:mi:ss')");
                } else if (String.valueOf(fieldValue).equals("-")) {
                    values.append("null");
                } else {
                    values.append("'").append(String.valueOf(fieldValue)).append("'");
                }

                if (iterator.hasNext()) {
                    values.append(", ");
                }
            }

            sql = new StringBuilder();
            sql.append("insert into ").append(tbName).append(" (")
                    .append(keys).append(") values(")
                    .append(values).append(")");
            result.add(sql.toString());
//        }

        return result;
    }
}
