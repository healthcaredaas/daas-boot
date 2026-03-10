package cn.healthcaredaas.data.cloud.core.ssl;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @Description: ssl socket client utils
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/7/8 15:48
 * @Modify：
 */
@Slf4j
public class SSLSocketClientUtils {

    public static SSLSocketFactory getSocketFactory(TrustManager manager) {
        SSLSocketFactory socketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{manager}, new SecureRandom());
            socketFactory = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.error("get socket factory error", e);
        }
        return socketFactory;
    }

    public static X509TrustManager getX509TrustManager() {
        return new X509TrustManager() {

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    public static HostnameVerifier getHostnameVerifier() {
        return (s, sslSession) -> true;
    }
}
