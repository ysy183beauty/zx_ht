package com.jeecms.cms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
/**
 * author: jiao
 * date:   2018/1/3 9:17
 * 基于http client 4.2.3叮咚云相关接口
 * note:
 */
public class SendMobileMsg {


        // 查账户信息的http地址111
        private static String URL_GET_USER_INFO = "https://api.dingdongcloud.com/v1/user/get";

        // 查询账户余额的http地址
        private static String URL_GET_BALANCE = "https://api.dingdongcloud.com/v1/sms/querybalance";

        // 验证码短信发送接口的http地址
        private static String URL_SEND_YZM = "https://api.dingdongcloud.com/v1/sms/captcha/send";

        private static String URL_SEND_YYYZM = "https://api.dingdongcloud.com/v1/voice/captcha/send";
        //短信状态查询

        private static String URL_GET_SEND_STATUS = "https://api.dingdongcloud.com/v1/sms/report/get";

        // 通知短信发送接口的http地址
        private static String URL_SEND_TZ = "https://api.dingdongcloud.com/v1/sms/notice/send";

        // 营销短信发送接口的http地址
        private static String URL_SEND_YX = "https://api.dingdongcloud.com/v1/sms/marketing/send";

        // 编码格式。发送编码格式统一用UTF-8
        private static String ENCODING = "UTF-8";


        private static String APIKEY="124285363edfd9dea43eae7b91a49555";

        public static void main(String[] args) throws IOException, URISyntaxException {

            // 修改为您的apikey. apikey可在官网（https://www.dingdongcloud.com)登录后获取
            String apikey = "************************";

            // 修改为您要发送的手机号
            String mobile = "13*********";

            /**************** 查账户信息调用示例 *****************/
            // System.out.println(DingdongCloudApis.getUserInfo(apikey));

            /**************** 查账户余额调用示例 *****************/
            // System.out.println(DingdongCloudApis.getUserInfo(apikey));

            /**************** 发送验证码短信 *****************/
            // 设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
            String yzmContent = "【叮咚云】您的验证码是：1234";

            // 发验证码短信调用示例
            System.out.println(SendMobileMsg.sendYzm(apikey, mobile, yzmContent));

            /**************** 发送语音验证码短信 *****************/
            // 必须纯数字4-6位
            String yyContent = "1234";

            // 发短信调用示例
            System.out.println(SendMobileMsg.sendYyYzm(apikey, mobile, yyContent));

            /**************** 发送通知短信 *****************/
            // 设置您要发送的内容
            String tzContent = "【叮咚云】您已成功注册叮咚云，请联系支持人员安排对接测试。";

            // 发短信调用示例
            System.out.println(SendMobileMsg.sendTz(apikey, mobile, tzContent));

            /**************** 发送营销短信 *****************/
            // 设置您要发送的内容，短信末尾必须带有“退订回T”
            String yxContent = "【叮咚云】您已成功注册叮咚云，请联系支持人员安排对接测试。退订回t";

            // 发短信调用示例
            System.out.println(SendMobileMsg.sendYx(apikey, mobile, yxContent));
        }



        /**
         * 查询账户信息接口
         *
         * @param apikey
         * @return
         */
        public static String getUserInfo(String apikey) {

            NameValuePair[] data = { new NameValuePair("apikey", apikey) };
            return doPost(URL_GET_USER_INFO, data);
        }

        /**
         * 查询账户余额接口
         *
         * @param apikey
         * @return
         */
        public static String getBalance(String apikey) {

            NameValuePair[] data = { new NameValuePair("apikey", apikey) };
            return doPost(URL_GET_BALANCE, data);
        }

    public static Boolean sendDaLiYzm(String mobile, String content) {

//        接口调用JSON返回示例：
//        {
//            "code": 1,                      //返回结果，code为1，表示成功，其他请参考返回值说明
//           "msg": "成功",                  //返回结果说明
//                "result": "201602161455000004" //此次短信验证码发送的批次号，可用于查询短信发送状态
//        }
        String returnStr = sendYzm(APIKEY,mobile,content);

        String[] array=returnStr.split(",");
        String code=array[0].split(":")[1];
        if("1".equals(code)){
            String uuid=array[2].split(":")[1];
           return getSendStatus(uuid);

        }else {
          return false;
        }
    }

    /**
     * 获取短信发送状态
     * @param uuid
     *            短信发送内容（必须经过utf-8格式编码)
     * @return json格式字符串
     */
    public static Boolean getSendStatus(String uuid) {


        NameValuePair[] data = { new NameValuePair("apikey", APIKEY),

                new NameValuePair("ser_no", uuid),};

        String returnStr = doPost(URL_GET_SEND_STATUS, data);

        String[] array=returnStr.split(",");
        String code=array[0].split(":")[1];
        if("1".equals(code)){
            return true;
        }else {
            return false;
        }

    }

        /**
         * 发送验证码短信
         *
         * @param apikey
         *            apikey
         * @param mobile
         *            手机号码(唯一，不许多个)
         * @param content
         *            短信发送内容（必须经过utf-8格式编码)
         * @return json格式字符串
         */
        public static String sendYzm(String apikey, String mobile, String content) {

            if (StringUtils.isNotBlank(content)) {
                try {
                    content = URLEncoder.encode(content, ENCODING);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            NameValuePair[] data = { new NameValuePair("apikey", apikey),

                    new NameValuePair("mobile", mobile),

                    new NameValuePair("content", content) };

            return doPost(URL_SEND_YZM, data);
        }

        /**
         * 发送语音验证码
         *
         * @param apikey
         * @param mobile
         *            手机号码(唯一，不许多个)
         * @param content
         *            短信发送内容(必须纯数字4-6位)
         * @return
         */
        public static String sendYyYzm(String apikey, String mobile, String content) {

            NameValuePair[] data = { new NameValuePair("apikey", apikey),

                    new NameValuePair("mobile", mobile),

                    new NameValuePair("content", content) };

            return doPost(URL_SEND_YYYZM, data);
        }

        /**
         * 发送通知短信
         *
         * @param apikey
         *            apikey
         * @param mobile
         *            手机号码（多个号码用英文半角逗号分开，最多可提交1000个）
         * @param content
         *            短信发送内容（必须经过utf-8格式编码)
         * @return json格式字符串
         */
        public static String sendTz(String apikey, String mobile, String content) {

            if (StringUtils.isNotBlank(content)) {
                try {
                    content = URLEncoder.encode(content, ENCODING);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            NameValuePair[] data = { new NameValuePair("apikey", apikey),

                    new NameValuePair("mobile", mobile),

                    new NameValuePair("content", content) };

            return doPost(URL_SEND_TZ, data);
        }

        /**
         * 发送营销短信
         *
         * @param apikey
         *            apikey
         * @param mobile
         *            手机号码（多个号码用英文半角逗号分开，最多可提交1000个）
         * @param content
         *            短信发送内容（必须经过utf-8格式编码，短信末尾必须带有“退订回T”）
         * @return json格式字符串
         */
        public static String sendYx(String apikey, String mobile, String content) {

            if (StringUtils.isNotBlank(content)) {
                try {
                    content = URLEncoder.encode(content, ENCODING);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            NameValuePair[] data = { new NameValuePair("apikey", apikey),

                    new NameValuePair("mobile", mobile),

                    new NameValuePair("content", content) };

            return doPost(URL_SEND_YX, data);
        }

        /**
         * 基于HttpClient的post函数
         * PH
         * @param url
         *            提交的URL
         *
         * @param data
         *            提交NameValuePair参数
         * @return 提交响应
         */
        private static String doPost(String url, NameValuePair[] data) {

            HttpClient client = new HttpClient();
            PostMethod method = new PostMethod(url);
            // method.setRequestHeader("ContentType",
            // "application/x-www-form-urlencoded;charset=UTF-8");
            method.setRequestBody(data);
            // client.getParams().setContentCharset("UTF-8");
            client.getParams().setConnectionManagerTimeout(10000);
            try {
                client.executeMethod(method);
                return method.getResponseBodyAsString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
