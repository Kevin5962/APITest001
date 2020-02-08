package utils;

import com.alibaba.fastjson.JSONObject;
import constant.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Author: FengkaiXiao
 * Date: 2019-11-08
 * Time: 22:15
 */
public class HttpUtils {
    //httpclient工具模拟postman，发送get请求，接收响应。
    /**
     * 1.创建request连接
     * 2.填写URL和请求参数
     * 3.添加请求头和请求体（如果有）
     * 4.发送请求
     * 5.获取响应报文
     * 6.格式化
     * 7.在控制台输出报文
     */
    public static String call(String url, String type, String params, String ContentType) throws Exception {
        String body = "";
        String charset = "UTF-8";
        HashMap<String,String> jsonHeaders = new HashMap<>();
        jsonHeaders.put("X-Lemonban-Media-Type", Constants.X_LEMONBAN_MEDIA_TYPE);
        jsonHeaders.put("Content-Type", "application/json");
        HashMap<String,String> formHeaders = new HashMap<>();
        formHeaders.put("X-Lemonban-Media-Type", Constants.X_LEMONBAN_MEDIA_TYPE);
        formHeaders.put("Content-Type", "application/x-www-form-urlencoded");
        HashMap<String,String> Headers = new HashMap<>();
        if("json".equalsIgnoreCase(ContentType)){
            Headers = jsonHeaders;
            if("post".equalsIgnoreCase(type)){
               body = HttpUtils.testPost(url,params,Headers,charset);}
            else if("delete".equalsIgnoreCase(type)){
                body = HttpUtils.testDelete(url,params,Headers,charset);}
            else if("put".equalsIgnoreCase(type)){
                body = HttpUtils.testPut(url,params,Headers,charset);}
            else if("patch".equalsIgnoreCase(type)){
                body = HttpUtils.testPatch(url,params,Headers,charset);}
            else if("get".equalsIgnoreCase(type)){
                /**将请求参数转换为key1=value1&key2=value2*/
                params = HttpUtils.json2KeyValue(params);
                body = HttpUtils.testGet(url,params,Headers,charset);}
        }
        else if("form".equalsIgnoreCase(ContentType)){
            /**将请求参数转换为key1=value1&key2=value2*/
            params = HttpUtils.json2KeyValue(params);
            Headers = formHeaders;
            if("post".equalsIgnoreCase(type)){
                body = HttpUtils.testPost(url,params,Headers,charset);}
            else if("delete".equalsIgnoreCase(type)){
                body = HttpUtils.testDelete(url,params,Headers,charset);}
            else if("put".equalsIgnoreCase(type)){
                body = HttpUtils.testPut(url,params,Headers,charset);}
            else if("patch".equalsIgnoreCase(type)){
                body = HttpUtils.testPatch(url,params,Headers,charset);}
            else if("get".equalsIgnoreCase(type)){
                body = HttpUtils.testGet(url,params,Headers,charset);}
        }
        return body;
    }

    //static 作用在方法，静态方法，可以不创建对象直接调用方法。只需要类名就可以调用。
    @Test
    public static String testGet(String url, String params, HashMap<String,String> headers, String charset) throws Exception {
        HttpGet get = new HttpGet(url+"?"+params);
        /**遍历加入通用请求头*/
        for(Map.Entry<String,String> entry:headers.entrySet()){
            get.setHeader(entry.getKey(),entry.getValue());
        }
        /**加入鉴权请求头--反射*/
        System.out.println("加入TokenHeader");
        AuthenticationUtils.addTokenInHeader(get);
        //get不能自己发出去，HttpClients是一个处理HttpClient的工具类
        CloseableHttpClient client = HttpClients.createDefault();
        //用客户端发送get请求，得到一个响应，response包含整个响应的所有内容
        CloseableHttpResponse response = client.execute(get);
        //EntityUtils是一个处理Entity对象的工具类
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println("状态码："+response.getStatusLine().getStatusCode());
        System.out.println("响应头："+ Arrays.toString(response.getAllHeaders()));
        System.out.println("响应体："+ body);
        return body;

    }

    @Test
    public static String testPost(String url,String params, HashMap<String,String> headers, String charset) throws Exception {
        HttpPost post = new HttpPost(url);
        /**遍历加入通用请求头*/
        for(Map.Entry<String,String> entry:headers.entrySet()){
            post.setHeader(entry.getKey(),entry.getValue());
        }

        /**加入鉴权请求头--反射*/
        System.out.println("加入TokenHeader");
        AuthenticationUtils.addTokenInHeader(post);

        //添加参数到请求体
        StringEntity stringEntity = new StringEntity(params);
        post.setEntity(stringEntity);
        //匿名对象，少写代码偷懒的方式。
        post.setEntity(new StringEntity(params,charset));
        //post不能自己发出去，HttpClients是一个处理HttpClient的工具类
        CloseableHttpClient client = HttpClients.createDefault();
        //用客户端发送post请求，得到一个响应，response包含整个响应的内容
        CloseableHttpResponse response = client.execute(post);
        //EntityUtils是一个处理Entity对象的工具类
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println("状态码："+response.getStatusLine().getStatusCode());
        System.out.println("响应头："+ Arrays.toString(response.getAllHeaders()));
        System.out.println("响应体："+ body);

        /**存储token和memberId*/
        AuthenticationUtils.storeToken(body);

        return body;
    }

    @Test
    public static String testPatch(String url,String params, HashMap<String,String> headers, String charset) throws Exception {
        HttpPatch patch = new HttpPatch(url);
        /**遍历加入通用请求头*/
        for(Map.Entry<String,String> entry:headers.entrySet()){
            patch.setHeader(entry.getKey(),entry.getValue());
        }
        /**加入鉴权请求头--反射*/
        System.out.println("加入TokenHeader");
        AuthenticationUtils.addTokenInHeader(patch);
        //添加参数到请求体
        StringEntity stringEntity = new StringEntity(params);
        patch.setEntity(stringEntity);
        //匿名对象，少写代码偷懒的方式。
        patch.setEntity(new StringEntity(params,charset));
        //post不能自己发出去，HttpClients是一个处理HttpClient的工具类
        CloseableHttpClient client = HttpClients.createDefault();
        //用客户端发送post请求，得到一个响应，response包含整个响应的内容
        CloseableHttpResponse response = client.execute(patch);
        //EntityUtils是一个处理Entity对象的工具类
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println("状态码："+response.getStatusLine().getStatusCode());
        System.out.println("响应头："+ Arrays.toString(response.getAllHeaders()));
        System.out.println("响应体："+ body);
        return body;
    }

    @Test
    public static String testPut(String url,String params, HashMap<String,String> headers, String charset) throws Exception {
        HttpPut put = new HttpPut(url);
        /**遍历加入通用请求头*/
        for(Map.Entry<String,String> entry:headers.entrySet()){
            put.setHeader(entry.getKey(),entry.getValue());
        }
        /**加入鉴权请求头--反射*/
        System.out.println("加入TokenHeader");
        AuthenticationUtils.addTokenInHeader(put);
        //添加参数到请求体
        StringEntity stringEntity = new StringEntity(params);
        put.setEntity(stringEntity);
        //匿名对象，少写代码偷懒的方式。
        put.setEntity(new StringEntity(params,charset));
        //post不能自己发出去，HttpClients是一个处理HttpClient的工具类
        CloseableHttpClient client = HttpClients.createDefault();
        //用客户端发送post请求，得到一个响应，response包含整个响应的内容
        CloseableHttpResponse response = client.execute(put);
        //EntityUtils是一个处理Entity对象的工具类
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println("状态码："+response.getStatusLine().getStatusCode());
        System.out.println("响应头："+ Arrays.toString(response.getAllHeaders()));
        System.out.println("响应体："+ body);
        return body;
    }
    //https://blog.csdn.net/zzq900503/article/details/89711999
    @Test
    public static String testDelete(String url,String params, HashMap<String,String> headers, String charset) throws Exception {
//        HttpDelete delete = new HttpDelete(url);
        HttpDeleteWithBody delete = new HttpDeleteWithBody(url);
        /**遍历加入通用请求头*/
        for(Map.Entry<String,String> entry:headers.entrySet()){
            delete.setHeader(entry.getKey(),entry.getValue());
        }
        /**加入鉴权请求头--反射*/
        System.out.println("加入TokenHeader");
        AuthenticationUtils.addTokenInHeader(delete);
        //添加参数到请求体
        StringEntity stringEntity = new StringEntity(params);
        delete.setEntity(stringEntity);
        //匿名对象，少写代码偷懒的方式。
        delete.setEntity(new StringEntity(params,charset));
        //post不能自己发出去，HttpClients是一个处理HttpClient的工具类
        CloseableHttpClient client = HttpClients.createDefault();
        //用客户端发送post请求，得到一个响应，response包含整个响应的内容
        CloseableHttpResponse response = client.execute(delete);
        //EntityUtils是一个处理Entity对象的工具类
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println("状态码："+response.getStatusLine().getStatusCode());
        System.out.println("响应头："+ Arrays.toString(response.getAllHeaders()));
        System.out.println("响应体："+ body);
        return body;
    }

    public static String json2KeyValue(String json){
        Map<String,String> map = JSONObject.parseObject(json,Map.class);
        String result = "";
        //mobilephone=13212413333&pwd=123456&regName=xiaohua&xxx=123
        for(Map.Entry<String,String> entry:map.entrySet()){
            if(result.length()>0){
                result += "&";
            }
            result += entry.getKey()+"="+entry.getValue();
        }
        return result;
    }

}
