package com.lb.test.httpclient;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * @ClassName GetDemo
 * @Description @TODO  最简单的HTTP客户端,用来演示通过GET或者POST方式访问某个页面
 * @Author liubing
 * @Date 2019/11/1 09:56
 * @Version 1.0
 **/
public class GetDemo {
    public static void main(String[] args) throws  Exception {

        // GetDemo.check_username_queuename(args[0],args[1], args[2] );
        for(int i  = 0; i <= 500; i++){
            httpClent("http://192.168.176.247:8181/api/v1/user/validateUserQueue","liubing02", "root.queue_test_dc", "dev_test_dc");
            System.out.println("\n=====================" + i);
            Thread.sleep(3000);
        }
    }

    private static void httpClent(String serviceUrl, String userName, String queueName, String workSpace) {

        // 判断队列名, 补全[root.]
        // if (!queueName.startsWith("root.")) queueName = "root." + queueName;

        // 参数
        StringBuffer params = new StringBuffer();
        // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
        params.append("userName=").append(userName);
        params.append("&queueName=").append(queueName);
        params.append("&workSpace=").append(workSpace);

        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);//连接超时时间
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);//数据传输时间

        HttpResponse response = null;

        try {
            //创建HttpClient对象
            response = httpclient.execute(new HttpGet(serviceUrl + "?" + params.toString()));
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String s = EntityUtils.toString(responseEntity, "UTF-8");
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + s);
                System.out.println("响应code:" + response.getStatusLine().getStatusCode());
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("DataCenter Error Message: Service ValidateUserQueue Status Code:  \n" + response.getStatusLine());
                }

                // status 1，完全匹配；0，
                // message 提示信息
                // workSpace 连接库
                // queueName 队列

                JSONObject jo = JSON.parseObject(s);
                if (jo.getInteger("status") == 1) {
                    // 完全匹配
                    System.out.println(jo.getString("message"));
                } else {
                    // 没有完全匹配, 修改hive配置
                    System.out.println(jo.getString("queueName"));
                }
            }
        } catch (Exception e) {
            System.out.println("DataCenter Error Message: Service ValidateUserQueue connect timed out !!");
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.getEntity().getContent().close();
                }
                if (httpclient != null) {
                    httpclient.getConnectionManager().shutdown();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


/**
 * httpclient 4.3 +
 *
 * @param userName
 * @param queueName
 * @param workSpace
 */
//    private static void check_username_queuename(String userName, String queueName, String workSpace) {
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//
//
//        // userName=mubaoping&queueName=root.queue_qz_ba&workSpace=dev_test_1123_04
//
//
//        // 判断队列名, 补全[root.]
//        // if (!queueName.startsWith("root.")) queueName = "root." + queueName;
//
//        // 参数
//        StringBuffer params = new StringBuffer();
//        // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
//        params.append("userName=").append(userName);
//        params.append("&queueName=").append(queueName);
//        params.append("&workSpace=").append(workSpace);
//
//
//        // 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
//        HttpGet httpGet = new HttpGet("http://192.168.176.247:8181/api/v1/user/validateUserQueue?" + params.toString());
//
//        // 响应模型
//        CloseableHttpResponse response = null;
//        try {
//            // 配置信息
//            RequestConfig requestConfig = RequestConfig.custom()
//                    // 设置连接超时时间(单位毫秒)
//                    .setConnectTimeout(3000)
//                    // 设置请求超时时间(单位毫秒)
//                    .setConnectionRequestTimeout(3000)
//                    // socket读写超时时间(单位毫秒)
//                    .setSocketTimeout(3000)
//                    // 设置是否允许重定向(默认为true)
//                    .setRedirectsEnabled(true).build();
//
//            // 将上面的配置信息 运用到这个Get请求里
//            httpGet.setConfig(requestConfig);
//
//            // 由客户端执行(发送)Get请求
//            response = httpClient.execute(httpGet);
//
//            // 从响应模型中获取响应实体
//            HttpEntity responseEntity = response.getEntity();
//            System.out.println("响应状态为:" + response.getStatusLine());
//            // 判断响应状态
//            if (response.getStatusLine().getStatusCode() != 200) {
//                throw new RuntimeException("DataCenter Error Message: Service ValidateUserQueue Status Code:  \n" + response.getStatusLine());
//            }
//            {
//                if (responseEntity != null) {
//
//                    String s = EntityUtils.toString(responseEntity);
//                    System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//                    System.out.println("响应内容为:" + s);
//
//                    // status 1，完全匹配；0，
//                    // message 提示信息
//                    // workSpace 连接库
//                    // queueName 队列
//
//                    JSONObject jo = JSON.parseObject(s);
//                    if (jo.getInteger("status") == 1) {
//                        // 完全匹配
//                        System.out.println(jo.getString("message"));
//                    } else {
//                        // 没有完全匹配, 修改hive配置
//                        System.out.println(jo.getString("queueName"));
//                    }
//                }
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println("DataCenter Error Message: Service ValidateUserQueue connect timed out !!");
//            try {
//                // 释放资源
//                if (httpClient != null) {
//                    httpClient.close();
//                }
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}