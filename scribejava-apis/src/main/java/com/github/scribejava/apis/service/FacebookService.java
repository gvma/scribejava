package com.github.scribejava.apis.service;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.oauth.OAuth20Service;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class FacebookService extends OAuth20Service {

    /**
     * @deprecated use {@link #FacebookService(com.github.scribejava.core.builder.api.DefaultApi20, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.io.OutputStream, java.lang.String, java.lang.String,
     * java.lang.String, com.github.scribejava.core.httpclient.HttpClientConfig,
     * com.github.scribejava.core.httpclient.HttpClient)}
     */
    @Deprecated
    public FacebookService(DefaultApi20 api, OAuthConfig config) {
        this(api, config.getApiKey(), config.getApiSecret(), config.getCallback(), config.getScope(),
                config.getDebugStream(), config.getState(), config.getResponseType(), config.getUserAgent(),
                config.getHttpClientConfig(), config.getHttpClient());
    }

    public FacebookService(DefaultApi20 api, String apiKey, String apiSecret, String callback, String scope,
            OutputStream debugStream, String state, String responseType, String userAgent,
            HttpClientConfig httpClientConfig, HttpClient httpClient) {
        super(api, apiKey, apiSecret, callback, scope, debugStream, state, responseType, userAgent, httpClientConfig,
                httpClient);
    }

    @Override
    public void signRequest(String accessToken, OAuthRequest request) {
        super.signRequest(accessToken, request);

        final Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA256");
            final SecretKeySpec secretKey = new SecretKeySpec(getApiSecret().getBytes(), "HmacSHA256");
            mac.init(secretKey);

            final Formatter appsecretProof = new Formatter();

            for (byte b : mac.doFinal(accessToken.getBytes())) {
                appsecretProof.format("%02x", b);
            }

            request.addParameter("appsecret_proof", appsecretProof.toString());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("There is a problem while generating Facebook appsecret_proof.", e);
        }
    }
}
