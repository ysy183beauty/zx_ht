package com.npt.bridge.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.npt.bridge.dict.NptDict;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/28 16:07
 * 描述:
 */
public class NptHttpUtil {
    public static final String NPT_REMOTE_PARAM_NAME = "NPT_REMOTE_PARAM";
    public static final String NPT_REMOTE_SECURITY_FLAG = "NPT_REMOTE_FLAG";
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    public static final Integer HTTP_TRANS_ROWS = 1000;

    /**
     * 作者：OWEN
     * 时间：2016/11/28 11:24
     * 描述:
     * 封包
     */
    public static String pack(String data) {
        NptHttpDataPack dataPack = new NptHttpDataPack();
        dataPack.setNptSecurityFlag(NPT_REMOTE_SECURITY_FLAG);
        dataPack.setSysDate(NptCommonUtil.getCurrentSysDate());
        dataPack.setRealData(data);

        String eyeDate = JSON.toJSONString(dataPack);
        return NptHttpUtil.encode(NptHttpUtil.getDesCodeKey(), eyeDate);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/28 11:24
     * 描述:
     * 拆包,不全法或失败时返回Null
     */
    public static NptHttpDataPack unpack(String json) {
        String eyeDate = NptHttpUtil.decode(NptHttpUtil.getDesCodeKey(), json);

        NptHttpDataPack dataPack = JSON.parseObject(eyeDate, NptHttpDataPack.class);

        if (null != dataPack && !dataPack.getNptSecurityFlag().equals(NptHttpUtil.NPT_REMOTE_SECURITY_FLAG)) {
            return null;
        }
        return dataPack;
    }


    public static final String getDesCodeKey() {
        return "974cd88b-4693-4a52-81d1-676635166999";
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception
     */
    public static String encode(String key, String data) {
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes("utf-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes("utf-8"));
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data.getBytes("utf-8"));
            return byte2hex(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * DES算法，加密,密钥自动获取
     *
     * @param data 待加密字符串
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception
     */
    public static String encode(String data) {
        String key=getDesCodeKey();

        return encode(key,data);
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static String decode(String key, String data) {
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes("utf-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes("utf-8"));
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return new String(cipher.doFinal(hex2byte(data.getBytes("utf-8"))), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * DES算法，解密,密钥自动获取
     * @param data 待解密字符串
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static String decode(String data) {
        String key=getDesCodeKey();

        return decode(key,data);
    }

    /**
     * 二行制转字符串
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException();
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    public static NptDict httpPost(String url, String json) {
        if (null == url || url.isEmpty()) {
            return NptDict.RST_INVALID_PARAMS;
        }
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
        urlParams.add(new BasicNameValuePair(NPT_REMOTE_PARAM_NAME, json));

        try {
            HttpEntity postParams = new UrlEncodedFormEntity(urlParams, HTTP.UTF_8);
            post.setEntity(postParams);
        } catch (UnsupportedEncodingException e) {
            return NptDict.RST_EXCEPTION(e.getMessage());
        }

        try {
            HttpResponse response = client.execute(post);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

            String inputLine;
            StringBuffer stringBuffer = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                stringBuffer.append(inputLine);
            }
            reader.close();
            client.getConnectionManager().shutdown();
            return NptDict.RST_SUCCESS(stringBuffer.toString());
        } catch (Exception e) {
//            e.printStackTrace();
            return NptDict.RST_EXCEPTION(e.getMessage());
        }
    }

    public static final Pattern IPV4_PATTERN = Pattern.compile(
            "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    public static final Pattern IPV6_STD_PATTERN = Pattern.compile(
            "^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");



    /**
     * http post 请求访问第三方平台
     * @param rdpURL
     * @param jsonObject
     * @return
     * @throws IOException
     */
    public static String readRdpInterfacePost(String rdpURL, JSONObject jsonObject) throws Exception {
        HttpClient httpclient =new SSLClient();
        // 接口URL
        String url = rdpURL;
        HttpPost httpPost = new HttpPost(url);
        // 设置 HTTP POST 请求参数必须用 NameValuePair 对象
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // 服务调用授权 KEY
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
        }
        // 设置 HTTP POST 请求参数
        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        // 设置回调函数，将 responseData 转成 utf-8 编码
        ResponseHandler<String> responseHandler = new BasicResponseHandler() {
            @Override
            public String handleResponse(final HttpResponse response)
                    throws HttpResponseException, IOException {
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() >= 300) {
                    throw new HttpResponseException(statusLine.getStatusCode(),
                            statusLine.getReasonPhrase());
                }
                HttpEntity entity = response.getEntity();
                if (entity == null) {
                    return null;
                }
                String charSet = EntityUtils.getContentCharSet(entity);
                String responseData = EntityUtils.toString(entity);
                return new String(responseData.getBytes(charSet), HTTP.UTF_8);
            }
        };
        String responseData;
        try {
            responseData = httpclient.execute(httpPost, responseHandler);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return responseData;
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/29 16:58
     *  note:
     *          发送http get请求
     */
    public static String httpGet(String url) {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = null;
        //发送请求
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
//            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            response = httpclient.execute(httpget);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            System.out.println("--------------------------------------");
            // 打印响应状态
            System.out.println(response.getStatusLine());
            if (entity != null) {
                return EntityUtils.toString(entity, "utf-8");
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    //测试
    public static void main(String args[]) {
        //待加密内容
        String str = "its78888";
        //密码，长度要是8的倍数

        String result = NptHttpUtil.encode(str);
        System.out.println("加密后："+new String(result));

        String result1 = NptHttpUtil.encode("admin");
        System.out.println("加密后："+new String(result1));
        //直接将如上内容解密
        String r=result.toString();
        try {
            String decryResult = NptHttpUtil.decode(r);
            System.out.println("解密后："+new String(decryResult));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
