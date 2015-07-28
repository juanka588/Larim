package com.unal.larim.Data;

import java.io.Serializable;

/**
 * Created by JuanCamilo on 29/06/2015.
 */
public class Notice implements Serializable {
    public String title;
    public String content;
    public boolean checked;
    public String id;
    public String url;

    public Notice(String title, String content, boolean checked, String id, String url) {
        this.title = title;
        this.content = content;
        this.checked = checked;
        this.id = id;
        this.url = url;
    }

    public Notice(String title, String content, String checked, String id, String url) {
        this(title, content, checked.equals("true"), id, url);
    }
}

