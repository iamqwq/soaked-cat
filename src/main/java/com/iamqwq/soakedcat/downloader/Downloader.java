package com.iamqwq.soakedcat.downloader;

import java.io.IOException;

public abstract class Downloader {

    public abstract void download() throws IOException, InterruptedException;

}
