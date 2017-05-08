package com.newtouch.commons.shiro;

import com.newtouch.commons.utils.Digests;
import com.newtouch.commons.utils.EncodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/31.
 */
public abstract class ShiroService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ShiroService.class);

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    public abstract ShiroPrincipal findPrincipalByUsername(String username) throws Exception;
    public abstract void initRoleAndPermission(ShiroPrincipal principal) throws Exception;

    public abstract Map<String, Set<String>> getAllResourcePermissions() throws Exception;

    /**
     * @return 生成随机的salt
     */
    public String generateSalt() {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        return EncodeUtils.hexEncode(salt);
    }
    /**
     * 设定安全的密码,并经过1024次 sha-1 hash
     */
    private String entryptPassword(String password, byte[] salt) {
        byte[] hashPassword = Digests.sha1(password.getBytes(), salt, HASH_INTERATIONS);
        return EncodeUtils.hexEncode(hashPassword);
    }
}
