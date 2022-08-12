package com.iamqwq.soakedcat.ajax;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public abstract class AJAXResponse {

    private JSONObject response;

    public abstract JSONObject getJSON() throws InterruptedException, IOException;

}
