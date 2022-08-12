package com.iamqwq.soakedcat.downloader;

import com.iamqwq.soakedcat.config.Config;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import static com.iamqwq.soakedcat.config.Config.cookies;

public class ArtworkImageDownloader implements Runnable {

    private final Object object = new Object();
    public int threadAmount;

    private Iterator<String> iterator;

    public ArtworkImageDownloader(Iterator<String> iterator) {
        this.iterator = iterator;
    }

    public void download(String url) throws IOException {
        BufferedInputStream bis = null;
        FileOutputStream fis = null;
        try {
            Connection.Response response = Jsoup.connect(url).cookies(cookies).header("referer", "https://www.pixiv.com").ignoreContentType(true).execute();
            bis = response.bodyStream();
            File file = new File(Config.PIXIV_DOWNLOAD_PATH, url.substring(url.lastIndexOf("/")));
            fis = new FileOutputStream(file);
            int temp;
            while((temp = bis.read()) != -1) {
                fis.write(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fis.close();
            bis.close();
        }
    }

    @Override
    public void run() {
        if(iterator.hasNext()) {
            try {
                synchronized(object) {
                    ++threadAmount;
                }
                download(iterator.next());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                synchronized(object) {
                    --threadAmount;
                }
            }
        }
    }
}
