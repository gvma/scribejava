package com.github.scribejava.apis.service;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.oauth.OAuth20Service;
import java.io.OutputStream;

public class TutByOAuthService extends OAuth20Service {

    /**
     * @deprecated use {@link #TutByOAuthService(com.github.scribejava.core.builder.api.DefaultApi20, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.io.OutputStream, java.lang.String, java.lang.String,
     * java.lang.String, com.github.scribejava.core.httpclient.HttpClientConfig,
     * com.github.scribejava.core.httpclient.HttpClient)}
     */
    @Deprecated
    public TutByOAuthService(DefaultApi20 api, OAuthConfig config) {
        this(api, config.getApiKey(), config.getApiSecret(), config.getCallback(), config.getScope(),
                config.getDebugStream(), config.getState(), config.getResponseType(), config.getUserAgent(),
                config.getHttpClientConfig(), config.getHttpClient());
    }

    public TutByOAuthService(DefaultApi20 api, String apiKey, String apiSecret, String callback, String scope,
            OutputStream debugStream, String state, String responseType, String userAgent,
            HttpClientConfig httpClientConfig, HttpClient httpClient) {
        super(api, apiKey, apiSecret, callback, scope, debugStream, state, responseType, userAgent, httpClientConfig,
                httpClient);
    }

    @Override
    public void signRequest(String accessToken, OAuthRequest request) {
        request.addQuerystringParameter(OAuthConstants.TOKEN, accessToken);
    }
}
