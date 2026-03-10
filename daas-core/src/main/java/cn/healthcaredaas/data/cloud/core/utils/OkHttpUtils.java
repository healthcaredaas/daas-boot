package cn.healthcaredaas.data.cloud.core.utils;

import cn.healthcaredaas.data.cloud.core.ssl.SSLSocketClientUtils;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: okhttp工具类
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/7/8 15:45
 * @Modify：
 */
@Slf4j
public class OkHttpUtils {

    private static final OkHttpClient client;

    private static final MediaType JSONMediaType = MediaType.parse("application/json; charset=utf-8");

    static {
        // 支持https请求，绕过验证
        X509TrustManager manager = SSLSocketClientUtils.getX509TrustManager();
        client = new OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .sslSocketFactory(SSLSocketClientUtils.getSocketFactory(manager), manager)// 忽略校验
                .hostnameVerifier(SSLSocketClientUtils.getHostnameVerifier())//忽略校验
                .build();
    }

    /**
     * Get请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, HashMap<String, Object> params) throws IOException {
        return doGet(url, null, params);
    }

    /**
     * Post请求
     *
     * @param url
     * @param json
     * @return
     */
    public static String doPost(String url, JSONObject json) throws IOException {
        return doPost(url, null, json);
    }

    /**
     * Post请求
     *
     * @param url
     * @param paramMap
     * @return
     */
    public static String doFormPost(String url, Map<String, Object> paramMap) throws IOException {
        return doFormPost(url, null, paramMap);
    }

    /**
     * 带用户名密码认证的Get请求
     *
     * @param url
     * @param params
     * @param username
     * @param password
     * @return
     */
    public static String doGet(String url, HashMap<String, Object> params, String username, String password)
            throws IOException {
        final String credential = Credentials.basic(username, password);
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", credential);
        return doGet(url, headers, params);
    }

    /**
     * 带请求头的get请求
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> headers, HashMap<String, Object> params)
            throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (MapUtil.isNotEmpty(params)) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                urlBuilder.addQueryParameter(param.getKey(), String.valueOf(param.getValue()));
            }
        }
        Request.Builder requestBuilder = new Request.Builder().url(urlBuilder.build());
        if (MapUtil.isNotEmpty(headers)) {
            headers.forEach((k, v) -> requestBuilder.addHeader(k, String.valueOf(v)));
        }
        try {
            Response response = client.newCall(requestBuilder.build()).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                response.close();
                return res;
            }
            response.close();
        } catch (IOException e) {
            log.error("请求错误：url:[{}]， 错误信息：{}", url, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("请求错误：url:[{}]", url);
        }
        return null;
    }

    /**
     * 带用户名密码认证的post请求
     *
     * @param url
     * @param json
     * @param username
     * @param password
     * @return
     */
    public static String doPost(String url, JSONObject json, String username, String password) throws IOException {
        final String credential = Credentials.basic(username, password);
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", credential);
        return doPost(url, headers, json);
    }

    /**
     * 带请求头的Post请求
     *
     * @param url
     * @param headers
     * @param json
     * @return
     */
    public static String doPost(String url, Map<String, Object> headers, JSONObject json) throws IOException {
        RequestBody body = RequestBody.create(JSONMediaType, json.toJSONString());
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (MapUtil.isNotEmpty(headers)) {
            headers.forEach((k, v) -> requestBuilder.addHeader(k, String.valueOf(v)));
        }
        Request request = requestBuilder.post(body).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                response.close();
                return res;
            }
            response.close();
        } catch (IOException e) {
            log.error("请求错误：url:[{}]， 错误信息：{}", url, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("请求错误：url:[{}]", url);
        }
        return null;
    }

    /**
     * 带请求头的普通Post请求
     * 普通参数请求的时候，可以省略不写MediaType，其为
     * MediaType.parse("application/x-www-form-urlencoded; charset=utf-8")
     *
     * @param url
     * @param headers
     * @param paramMap
     * @return
     */
    public static String doFormPost(String url, Map<String, Object> headers, Map<String, Object> paramMap)
            throws IOException {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (MapUtil.isNotEmpty(headers)) {
            headers.forEach((k, v) -> requestBuilder.addHeader(k, String.valueOf(v)));
        }
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (MapUtil.isNotEmpty(paramMap)) {
            paramMap.forEach((k, v) -> formBodyBuilder.add(k, String.valueOf(v)));
        }
        Request request = requestBuilder.post(formBodyBuilder.build()).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                response.close();
                return res;
            } else {
                log.warn("请求失败：{}", response.body().string());
            }
            response.close();
        } catch (IOException e) {
            log.error("请求错误：url:[{}]， 错误信息：{}", url, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("请求错误：url:[{}]， 错误信息：{}", url, e.getMessage());
        }
        return null;
    }
}
