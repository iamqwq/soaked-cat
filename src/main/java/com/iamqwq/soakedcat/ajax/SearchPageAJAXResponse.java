package com.iamqwq.soakedcat.ajax;

import com.iamqwq.soakedcat.SearchFilter;
import com.iamqwq.soakedcat.enumeration.EnumImageType;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashSet;

public class SearchPageAJAXResponse extends AJAXResponse {

    private JSONObject jsonObject;
    private SearchFilter filter;
    private HashSet<String> urls;

    public SearchPageAJAXResponse(JSONObject jsonObject, SearchFilter filter) {
        this.jsonObject = jsonObject;
        this.filter = filter;
        urls = new HashSet<>();
    }

    @Override
    public JSONObject getJSON() {

        return jsonObject;
    }

    public HashSet<String> getArtworksIDs() {

        if(filter.getImageType().equals(EnumImageType.ALL)) {
            jsonObject = jsonObject.getJSONObject("illustManga");
        } else if(filter.getImageType().equals(EnumImageType.ILLUSTRATION)) {
            jsonObject = jsonObject.getJSONObject("illust");
        } else if(filter.getImageType().equals(EnumImageType.MANGA)) {
            jsonObject = jsonObject.getJSONObject("manga");
        }
        JSONArray urlsArray = jsonObject.getJSONArray("data");

        for(int i = 0; i < urlsArray.size(); i++) {
            urls.add(urlsArray.getJSONObject(i).getString("id"));
        }

        return urls;
    }

}
