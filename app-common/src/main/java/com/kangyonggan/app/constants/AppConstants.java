package com.kangyonggan.app.constants;

/**
 * @author kangyonggan
 * @since 8/9/18
 */
public interface AppConstants {

    /**
     * Hash Interations
     */
    int HASH_INTERATIONS = 2;

    /**
     * Salt Size
     */
    int SALT_SIZE = 8;

    /**
     * 把验证码存放在session中的key
     */
    String KEY_CAPTCHA = "key-captcha";

    /**
     * 用户在session中的key
     */
    String KEY_SESSION_USER = "session-user";

    /**
     * 视频阅读标识
     */
    String KEY_VIDEO_READ = "APP:VIDEO:";

}
