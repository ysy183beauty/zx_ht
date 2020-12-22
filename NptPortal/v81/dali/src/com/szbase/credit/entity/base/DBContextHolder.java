package com.szbase.credit.entity.base;

public class DBContextHolder{
    public static final String DATA_SOURCE_ZX = "dataSourceZx";
    public static final String DATA_SOURCE_MH = "dataSourceMh";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDBType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDBType() {
        return contextHolder.get();
    }

    public static void clearDBType() {
        contextHolder.remove();
    }
}
