package com.iamqwq.soakedcat.config;

import java.util.LinkedHashMap;

public class Config {

    public static LinkedHashMap<String, String> cookies = new LinkedHashMap<>();

    static {
        cookies.put("PHPSESSID", ""); // your cookie here
    }

    public static final String PIXIV_DOWNLOAD_PATH = "D:\\Download\\Pixiv"; // download path

}
