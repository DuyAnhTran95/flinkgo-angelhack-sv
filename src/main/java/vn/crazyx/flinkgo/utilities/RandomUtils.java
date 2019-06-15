package vn.crazyx.flinkgo.utilities;

import java.security.SecureRandom;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtils {
    public static String genRandomString(int randomStrLength) {
        char[] possibleCharacters = 
                (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&-_=+|./?"))
                .toCharArray();
        String randomStr = RandomStringUtils.random(randomStrLength, 0, 
                possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom());
        return randomStr;
    }
}
