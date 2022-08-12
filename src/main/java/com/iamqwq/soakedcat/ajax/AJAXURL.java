package com.iamqwq.soakedcat.ajax;

import java.io.IOException;

public abstract class AJAXURL {

     private String url;

     public abstract AJAXResponse connect() throws IOException;

}
