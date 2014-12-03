package com.turbomanage.storm.apt;

/**
 * Created by galex on 12/3/14.
 */
public class StringUtils {

    /**
     * Capitalizes the first letter to create a valid getter/setter name.
     *
     * @param anyName
     * @return String
     */
    public static String capFirst(String anyName) {
        // obscure Java convention:
        // if second letter capitalized, leave it alone
        if (anyName.length() > 1)
            if (anyName.charAt(1) >= 'A' && anyName.charAt(1) <= 'Z')
                return anyName;
        String capFirstLetter = anyName.substring(0, 1).toUpperCase();
        return capFirstLetter + anyName.substring(1);
    }
}
