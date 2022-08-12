package com.iamqwq.soakedcat.downloader;

import com.iamqwq.soakedcat.SearchFilter;
import com.iamqwq.soakedcat.ajax.ArtworkPageAJAXResponse;
import com.iamqwq.soakedcat.ajax.ArtworkPageAJAXURL;
import com.iamqwq.soakedcat.ajax.SearchPageAJAXResponse;
import com.iamqwq.soakedcat.ajax.SearchPageAJAXURL;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class SearchPageDownloader extends Downloader {

    private int threadAmount;

    private SearchFilter filter;

    public SearchPageDownloader(SearchFilter filter) {
        this.filter = filter;
    }

    @Override
    public void download() throws IOException, InterruptedException {

        /* FIXME: There is no control over the number of pages retrieved here(i < 10) */
        for(int i = 1; i < 10; i++) {
            SearchPageAJAXResponse searchResponse = new SearchPageAJAXURL(filter, i).connect();
            HashSet<String> artworkIDs = searchResponse.getArtworksIDs();

            for(String artworkID : artworkIDs) {
                ArtworkPageAJAXResponse artworkResponse = new ArtworkPageAJAXURL(artworkID, filter).connect();
                HashSet<String> artworkImageURLs = artworkResponse.getArtworkImagesURLs();

                Iterator<String> iterator = artworkImageURLs.iterator();
                ArtworkImageDownloader artworkImageDownloader = new ArtworkImageDownloader(iterator);
                while(iterator.hasNext()) {
                    while(threadAmount >= 4) {
                        Thread.sleep(500);
                    }
                    new Thread(artworkImageDownloader).start();
                }
                while(threadAmount != 0) {
                    Thread.sleep(500);
                }
                Thread.sleep(2000);
            }
        }
    }
}
