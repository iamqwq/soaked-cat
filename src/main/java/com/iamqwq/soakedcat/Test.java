package com.iamqwq.soakedcat;

import com.iamqwq.soakedcat.downloader.SearchPageDownloader;
import com.iamqwq.soakedcat.enumeration.EnumImageType;
import com.iamqwq.soakedcat.enumeration.EnumSearchMode;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.setProperty("socksProxyHost", "127.0.0.1");
        System.setProperty("socksProxyPort", "10010");

        SearchFilter filter = new SearchFilter("水着", EnumImageType.ILLUSTRATION, EnumSearchMode.ALL, 100, 10);

        new SearchPageDownloader(filter).download();

    }
}
