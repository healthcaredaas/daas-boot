package cn.healthcaredaas.data.cloud.security.authentication.jwt;

import cn.healthcaredaas.data.cloud.security.core.enums.Certificate;
import cn.healthcaredaas.data.cloud.security.authentication.properties.OAuth2Properties;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**

 * @ClassName： JwtTokenSupport.java
 * @Author： chenpan
 * @Date：2024/12/6 16:12
 * @Modify：
 */
@Configuration
public class JwtTokenSupport {

    @Bean
    @Order(Integer.MIN_VALUE)
    public JWKSource<SecurityContext> jwkSource(OAuth2Properties oAuth2Properties) throws Exception {
        OAuth2Properties.Jwk jwk = oAuth2Properties.getJwk();

        KeyPair keyPair;
        if (jwk.getCertificate() == Certificate.CUSTOM) {
            ClassPathResource resource = new ClassPathResource(jwk.getJksKeyStore());
            KeyStore keyStore = KeyStore.getInstance("JKS");
            try (InputStream inputStream = resource.getInputStream()) {
                keyStore.load(inputStream, jwk.getJksStorePassword().toCharArray());
            }
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(
                    jwk.getJksKeyAlias(),
                    new KeyStore.PasswordProtection(jwk.getJksKeyPassword().toCharArray())
            );
            keyPair = new KeyPair(privateKeyEntry.getCertificate().getPublicKey(), privateKeyEntry.getPrivateKey());
        } else {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(jwk.getJksKeyAlias())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    /**
     * jwt 解码
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }
}
