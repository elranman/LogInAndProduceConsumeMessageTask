package com.project.authentication.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class BasicAuthenticationUtil {

    public static String encryptToMD5(String str) {
        return DigestUtils.md5Hex(str);
    }
}
