package com.iamqwq.soakedcat.ajax;

import com.iamqwq.soakedcat.config.Config;
import com.iamqwq.soakedcat.SearchFilter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Logger;

public class ArtworkPageAJAXResponse extends AJAXResponse {

    Logger logger = Logger.getLogger("iamqwq");

    private JSONObject jsonObject;
    private String id;
    private SearchFilter filter;
    private HashSet<String> urls;

    public ArtworkPageAJAXResponse(JSONObject jsonObject, String id, SearchFilter filter) {

        this.jsonObject = jsonObject;
        this.id = id;
        this.filter = filter;
        this.urls = new HashSet<>();
    }

    @Override
    public JSONObject getJSON() {

        return jsonObject;
    }

    public HashSet<String> getArtworkImagesURLs() throws IOException {

        JSONArray urlsArray = jsonObject.getJSONArray("body");

        if(filter.getBookmarkAmount() != 0 || filter.getMaxPageAmount() != 0) {

            boolean isEligible = true;

            Document document = Jsoup.connect("https://www.pixiv.net/bookmark_detail.php?illust_id=" + id).cookies(Config.cookies).get();
            logger.info("Now checking : https://www.pixiv.net/bookmark_detail.php?illust_id=" + id);

            // Check if amount of bookmark is eligible
            if(filter.getBookmarkAmount() != 0) {

                Elements bookmarkAmountElement = document.getElementsByClass("count-badge");

                int bookMarkAmount = 0;
                for(Element e : bookmarkAmountElement) {
                    int endOfAmount = e.text().lastIndexOf("人");
                    if(endOfAmount < 0) {
                        bookMarkAmount = 0;
                    } else {
                        bookMarkAmount = Integer.parseInt(e.text().substring(0, endOfAmount));
                    }
                    break;
                }
                if(bookMarkAmount <= filter.getBookmarkAmount()) {
                    logger.info("Thread:" + Thread.currentThread().getName() + " >> bookMarkAmount: " + bookMarkAmount + " <= " + filter.getBookmarkAmount() + " - DISCARD");
                    isEligible = false;
                } else {
                    logger.info("Thread:" + Thread.currentThread().getName() + " >> bookMarkAmount: " + bookMarkAmount + " > " + filter.getBookmarkAmount() + " - SAVE");
                }
            }

            // Check if amount of total pages is eligible
            if(filter.getMaxPageAmount() != 0) {

                Elements totalPagesAmountElement = document.getElementsByClass("page-count").tagName("span");

                for(Element e : totalPagesAmountElement) {
                    int totalPages = Integer.parseInt(e.text());
                    if(totalPages > filter.getMaxPageAmount()) {
                        logger.info("Thread:" + Thread.currentThread().getName() + " >> totalPage: " + totalPages + " > " + filter.getMaxPageAmount() + " - DISCARD");
                        isEligible = false;
                    } else {
                        logger.info("Thread:" + Thread.currentThread().getName() + " >> totalPage: " + totalPages + " <= " + filter.getMaxPageAmount() + " - SAVE");
                    }
                    break;
                }
            }

            if(isEligible) {
                for(int i = 0; i < urlsArray.size(); i++) {
                    urls.add(urlsArray.getJSONObject(i).getJSONObject("urls").getString("original"));
                }
            }
        }

        return urls;
    }

}
