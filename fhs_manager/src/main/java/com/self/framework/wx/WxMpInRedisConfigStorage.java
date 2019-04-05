package com.self.framework.wx;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.ToStringUtils;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WxMpInRedisConfigStorage implements WxMpConfigStorage {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final static String ACCESS_TOKEN_KEY = "WECHAT_ACCESS_TOKEN_";

    private final static String JSAPI_TICKET_KEY = "WECHAT_JSAPI_TICKET_";

    private final static String CARDAPI_TICKET_KEY = "WECHAT_CARDAPI_TICKET_";

    private static final String ACCESS_TOKEN_EXPIRES_TIME_KEY = "WX_CP_ACCESS_TOKEN_EXPIRES_TIME";

    private static final String JS_API_TICKET_EXPIRES_TIME_KEY = "WX_CP_JS_API_TICKET_EXPIRES_TIME";

    private final static String CARDAPI_TICKET_EXPIRE_KEY = "WEBCHAT_CARDAPI_TICKET_EXPIRE";


    private RedisTemplate redisTemplate;


    protected volatile String appId;
    protected volatile String secret;
    protected volatile String token;
    protected volatile String templateId;

    protected volatile String aesKey;

    protected volatile String oauth2redirectUri;

    protected volatile String httpProxyHost;
    protected volatile int httpProxyPort;
    protected volatile String httpProxyUsername;
    protected volatile String httpProxyPassword;


    /**
     * 临时文件目录
     */
    protected volatile File tmpDirFile;

    protected volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

    protected Lock accessTokenLock = new ReentrantLock();
    protected Lock jsapiTicketLock = new ReentrantLock();
    protected Lock cardApiTicketLock = new ReentrantLock();

    private RedisSerializer<String> redisSerializer;
    private RedisConnection redisConnection;
    public WxMpInRedisConfigStorage() {
    }
    public WxMpInRedisConfigStorage(String host, int port) {
    }

    public WxMpInRedisConfigStorage(LettucePoolingClientConfiguration poolConfig, String host, int port) {
    }

    public WxMpInRedisConfigStorage(LettucePoolingClientConfiguration poolConfig, String host, int port, int timeout, String password) {
    }

    public void destroy() {
    }

    public WxMpInRedisConfigStorage(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisSerializer = redisTemplate.getStringSerializer();
        this.redisConnection = redisTemplate.getConnectionFactory().getConnection();
    }

    public Lock getCardApiTicketLock() {
        return this.cardApiTicketLock;
    }

    @Override
    public Lock getJsapiTicketLock() {
        return this.jsapiTicketLock;
    }

    @Override
    public Lock getAccessTokenLock() {
        return this.accessTokenLock;
    }


    @Override
    public String getAccessToken() {

        Throwable var2 = null;

        String var3;
        try {
            var3 = getString(ACCESS_TOKEN_KEY);
            LOG.info("获取accessToken:" + var3);
        } catch (Throwable var12) {
            var2 = var12;
            throw var12;
        }
        return var3;
    }


    @Override
    public boolean isAccessTokenExpired() {
        Throwable var2 = null;

        boolean var4;
        try {
            String expiresTimeStr = getString(ACCESS_TOKEN_EXPIRES_TIME_KEY);
            if (expiresTimeStr != null) {
                Long expiresTime = Long.valueOf(Long.parseLong(expiresTimeStr));
                boolean var5 = System.currentTimeMillis() > expiresTime.longValue();
                return var5;
            }

            var4 = true;
        } catch (Throwable var15) {
            var2 = var15;
            throw var15;
        }
        LOG.info("accessToken 是否过期:" + var4);
        return var4;
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        Throwable var4 = null;

        try {
            LOG.info("更新accessToken：" + accessToken);
            setString(ACCESS_TOKEN_KEY, accessToken);
            setString(ACCESS_TOKEN_EXPIRES_TIME_KEY, System.currentTimeMillis() + (long) (expiresInSeconds - 200) * 1000L + "");
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        }

    }

    @Override
    public void expireAccessToken() {
        Throwable var2 = null;
        LOG.info("设置accessToken立刻过期" );
        try {
            setString(ACCESS_TOKEN_EXPIRES_TIME_KEY, "0");
        } catch (Throwable var11) {
            var2 = var11;
            throw var11;
        }
    }

    @Override
    public void updateAccessToken(WxAccessToken accessToken) {
        this.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
    }

    @Override
    public String getJsapiTicket() {
        Throwable var2 = null;

        String var3;
        try {
            var3 = getString(JSAPI_TICKET_KEY);

        } catch (Throwable var12) {
            var2 = var12;
            throw var12;
        }
        return var3;
    }


    @Override
    public boolean isJsapiTicketExpired() {

        Throwable var2 = null;
        boolean var4;
        try {
            String expiresTimeStr = getString(JS_API_TICKET_EXPIRES_TIME_KEY);
            if (expiresTimeStr != null) {
                Long expiresTime = Long.valueOf(Long.parseLong(expiresTimeStr));
                boolean var5 = System.currentTimeMillis() > expiresTime.longValue();
                return var5;
            }

            var4 = true;
        } catch (Throwable var15) {
            var2 = var15;
            throw var15;
        }
        return var4;
    }


    @Override
    public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {

        Throwable var4 = null;

        try {
            setString(JSAPI_TICKET_KEY, jsapiTicket);
            setString(JS_API_TICKET_EXPIRES_TIME_KEY, System.currentTimeMillis() + (long) (expiresInSeconds - 200) * 1000L + "");
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        }
    }

    @Override
    public void expireJsapiTicket() {

        Throwable var2 = null;

        try {
            setString(JS_API_TICKET_EXPIRES_TIME_KEY, "0");
        } catch (Throwable var11) {
            var2 = var11;
            throw var11;
        }
    }

    @Override
    public String getCardApiTicket() {

        Throwable var2 = null;

        String var3;
        try {
            var3 = getString(CARDAPI_TICKET_KEY);

        } catch (Throwable var12) {
            var2 = var12;
            throw var12;
        }
        return var3;
    }


    @Override
    public boolean isCardApiTicketExpired() {

        Throwable var2 = null;
        boolean var4;
        try {
            String expiresTimeStr = getString(CARDAPI_TICKET_EXPIRE_KEY);
            if (expiresTimeStr != null) {
                Long expiresTime = Long.valueOf(Long.parseLong(expiresTimeStr));
                boolean var5 = System.currentTimeMillis() > expiresTime.longValue();
                return var5;
            }

            var4 = true;
        } catch (Throwable var15) {
            var2 = var15;
            throw var15;
        }
        return var4;

    }

    @Override
    public synchronized void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {

        Throwable var4 = null;

        try {
            setString(CARDAPI_TICKET_KEY, cardApiTicket);
            setString(CARDAPI_TICKET_EXPIRE_KEY, System.currentTimeMillis() + (long) (expiresInSeconds - 200) * 1000L + "");
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        }

    }


    @Override
    public void expireCardApiTicket() {

        Throwable var2 = null;

        try {
            setString(CARDAPI_TICKET_EXPIRE_KEY, "0");
        } catch (Throwable var11) {
            var2 = var11;
            throw var11;
        }

    }

    private String getString(String keyStr) {
        return (String) redisTemplate.opsForValue().get(keyStr);
    }

    private void setString(String keyStr, String valueStr) {
        redisTemplate.opsForValue().set(keyStr, valueStr);
    }


    @Override
    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getTemplateId() {
        return this.templateId;
    }

    @Override
    public long getExpiresTime() {
        Throwable var2 = null;

        long var5;
        try {
            String expiresTimeStr = getString(ACCESS_TOKEN_EXPIRES_TIME_KEY);
            if (expiresTimeStr == null) {
                long var18 = 0L;
                return var18;
            }

            Long expiresTime = Long.valueOf(Long.parseLong(expiresTimeStr));
            var5 = expiresTime.longValue();
        } catch (Throwable var16) {
            var2 = var16;
            throw var16;
        }
        return var5;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }


    @Override
    public String getAesKey() {
        return this.aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    @Override
    public String getOauth2redirectUri() {
        return this.oauth2redirectUri;
    }

    public void setOauth2redirectUri(String oauth2redirectUri) {
        this.oauth2redirectUri = oauth2redirectUri;
    }

    @Override
    public String getHttpProxyHost() {
        return this.httpProxyHost;
    }

    public void setHttpProxyHost(String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
    }

    @Override
    public int getHttpProxyPort() {
        return this.httpProxyPort;
    }

    public void setHttpProxyPort(int httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    @Override
    public String getHttpProxyUsername() {
        return this.httpProxyUsername;
    }

    public void setHttpProxyUsername(String httpProxyUsername) {
        this.httpProxyUsername = httpProxyUsername;
    }

    @Override
    public String getHttpProxyPassword() {
        return this.httpProxyPassword;
    }

    public void setHttpProxyPassword(String httpProxyPassword) {
        this.httpProxyPassword = httpProxyPassword;
    }

    @Override
    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }

    @Override
    public File getTmpDirFile() {
        return this.tmpDirFile;
    }

    public void setTmpDirFile(File tmpDirFile) {
        this.tmpDirFile = tmpDirFile;
    }

    @Override
    public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
        return this.apacheHttpClientBuilder;
    }

    public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
        this.apacheHttpClientBuilder = apacheHttpClientBuilder;
    }


    @Override
    public boolean autoRefreshToken() {
        return true;
    }

}
