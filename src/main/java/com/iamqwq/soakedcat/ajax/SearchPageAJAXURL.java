package com.iamqwq.soakedcat.ajax;

import com.iamqwq.soakedcat.config.Config;
import com.iamqwq.soakedcat.SearchFilter;
import com.iamqwq.soakedcat.enumeration.EnumImageType;
import com.iamqwq.soakedcat.enumeration.EnumSearchMode;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchPageAJAXURL extends AJAXURL {

    private String url;
    private SearchFilter filter;

    // e.g. https://www.pixiv.net/ajax/search/artworks/#SEARCH_CONTENT#&order=date_d&mode=all&p=1&s_mode=s_tag_full&type=all&lang=zh
    private static String SEARCH_ROOT = "https://www.pixiv.net/ajax/search/";

    public SearchPageAJAXURL(SearchFilter filter, int pageNum) throws UnsupportedEncodingException {

        url = SEARCH_ROOT;
        this.filter = filter;

        if(filter.getImageType().equals(EnumImageType.ALL)) {
            url = url.concat("artworks/");
        } else if(filter.getImageType().equals(EnumImageType.ILLUSTRATION)) {
            url = url.concat("illustrations/");
        } else if(filter.getImageType().equals(EnumImageType.MANGA)) {
            url = url.concat("manga/");
        }

        url = url.concat(URLEncoder.encode(filter.getKeyword(), "UTF-8"));
        url = url.concat("?word=" + URLEncoder.encode(filter.getKeyword(), "UTF-8"));

        url = url.concat("&order=date_d");

        if(filter.getSearchMode().equals(EnumSearchMode.ALL)) {
            url = url.concat("&mode=all");
        } else if(filter.getSearchMode().equals(EnumSearchMode.SAFE)) {
            url = url.concat("&mode=safe");
        } else if(filter.getSearchMode().equals(EnumSearchMode.R18)) {
            url = url.concat("&mode=r18");
        }

        url = url.concat("&p=" + pageNum + "&s_mode=s_tag_full");

        if(filter.getImageType().equals(EnumImageType.ALL)) {
            url = url.concat("&type=all");
        } else if (filter.getImageType().equals(EnumImageType.ILLUSTRATION)) {
            url = url.concat("&type=illust_and_ugoira");
        } else if(filter.getImageType().equals(EnumImageType.MANGA)) {
            url = url.concat("&type=manga");
        }

        url = url.concat("&lang=" + filter.getLanguage());
    }


    @Override
    public SearchPageAJAXResponse connect() throws IOException {

        JSONObject jsonObject = JSONObject.parseObject(Jsoup.connect(url).ignoreContentType(true).cookies(Config.cookies).execute().body());
        jsonObject = jsonObject.getJSONObject("body");

        return new SearchPageAJAXResponse(jsonObject, filter);
    }
}
