package com.npt.rms.util;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.arch.bean.OraganEntity;
import java.sql.*;
import java.text.Collator;
import java.util.*;

public class JdbcConnection {
    private static Connection conn=null;
    private static ResultSet rs=null;
    private static PreparedStatement pst=null;
    private static String USERNAMR = "credit_alpha";
    private static String PASSWORD = "its78888";
    private static String DRVIER = "oracle.jdbc.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@1.10.4.36:1521:etldb";
    private static List<OraganEntity> list=null;
    private static JdbcConnection jdbcConnection=null;

    private JdbcConnection(){

    }

    /**
     * 获取单例模式
     */
    public static JdbcConnection getInstance(){
        synchronized (JdbcConnection.class){
            if(jdbcConnection==null){
                jdbcConnection=new JdbcConnection();
            }
            return jdbcConnection;
        }
    }

    /**
     * 获取Connection对象
     *
     * @return
     */
    public Connection getConnection() {
        try {
            Class.forName(DRVIER);
            conn = DriverManager.getConnection(URL, USERNAMR, PASSWORD);
            System.out.println("成功连接数据库");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("class not find !", e);
        } catch (SQLException e) {
            throw new RuntimeException("get connection error!", e);
        }
        return conn;
    }

    /**
     * 释放资源
     */
    public void ReleaseResource() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新数据信息
     */
    public  void updateInfo(String sql){
        try {
            conn = getConnection();
            PreparedStatement pstm=conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ReleaseResource();
        }
    }

    /**
     * 获取数据信息
     */
    public List<OraganEntity> SelectData(Long orgId) {
        try {
            conn = getConnection();
            list = new ArrayList<>();
            if(orgId==null){
                orgId=new Long(-1);
            }
            if(null == orgId || orgId == NptCommonUtil.getDefaultParentId()){
                String querySql="select a1.id,a1.org_addr,a1.org_code,a1.org_name,a1.parent_id,1 as is_open from NPT_ORGANIZATION a1 where a1.parent_id="+orgId+"";
                PreparedStatement pstm=conn.prepareStatement(querySql);
                ResultSet rst= pstm.executeQuery();
                list=getResult(rst);
                orgId=list.get(0).getID().longValue();
                if(pstm!=null){
                    pstm.close();
                }
                if(rst!=null){
                    rst.close();
                }
            }
            StringBuilder sql=new StringBuilder();
            sql.append("select a1.id,a1.org_addr,a1.org_code,a1.org_name,a1.parent_id,");
            sql.append("case when (select count(1) from NPT_ORGANIZATION a2 where a2.parent_id=a1.id)>0 then 1 else 0 end is_open from NPT_ORGANIZATION a1 ");
            sql.append("  where a1.parent_id="+orgId+" order by a1.id");
            pst = conn.prepareStatement(sql.toString());
            rs = pst.executeQuery();
            list=getResult(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource();
        }
        sortByChina(list);
        return list;
    }

    /**
     * 按照中文名称进行排序
     * @param list
     */
    public  void sortByChina(List<OraganEntity> list){
        // 对结果按别名排序
        Collections.sort(list, new Comparator<OraganEntity>() {
            public int compare(OraganEntity o1, OraganEntity o2) {
                Comparator<Object> comparator = Collator.getInstance(java.util.Locale.CHINA);
                return comparator.compare(o1.getORG_NAME(), o2.getORG_NAME());
            }
            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
    }

    private List<OraganEntity> getResult(ResultSet rst){
        try {
            while (rst.next()){
                OraganEntity obj=new OraganEntity();
                obj.setID(rst.getInt("ID"));
                obj.setORG_ADDR(rst.getString("ORG_ADDR"));
                obj.setORG_CODE(rst.getString("ORG_CODE"));
                obj.setORG_NAME(rst.getString("ORG_NAME"));
                obj.setPARENT_ID(rst.getInt("PARENT_ID"));
                obj.setIS_OPEN(rst.getBoolean("IS_OPEN"));
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}