package com.iamqwq.soakedcat.ajax;

import com.iamqwq.soakedcat.config.Config;
import com.iamqwq.soakedcat.SearchFilter;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;

public class ArtworkPageAJAXURL extends AJAXURL {

    private String id;
    private SearchFilter filter;

    private static String ARTWORK_ROOT = "https://www.pixiv.net/ajax/";

    public ArtworkPageAJAXURL(String id) {

        this.id = id;
        this.filter = new SearchFilter();
    }

    public ArtworkPageAJAXURL(String id, SearchFilter filter) {

        this.id = id;
        this.filter = filter;
    }

    @Override
    public ArtworkPageAJAXResponse connect() throws IOException {

        String url = ARTWORK_ROOT;

        url = url.concat("illust/");
        url = url.concat(id + "/");
        url = url.concat("pages?lang=zh/");

        JSONObject jsonObject = JSONObject.parseObject(Jsoup.connect(url).ignoreContentType(true).cookies(Config.cookies).execute().body());

        return new ArtworkPageAJAXResponse(jsonObject, id, filter);
    }

}
