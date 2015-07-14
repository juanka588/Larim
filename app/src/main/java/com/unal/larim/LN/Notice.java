package com.unal.larim.LN;

import java.io.Serializable;

/**
 * Created by JuanCamilo on 29/06/2015.
 */
public class Notice implements Serializable {
    public String title;
    public String content;
    public boolean checked;
    public String id;

    public Notice(String title, String content, boolean checked, String id) {
        this.title = title;
        this.content = content;
        this.checked = checked;
        this.id = id;
    }

    public Notice(String title, String content, String checked, String id) {
        this.title = title;
        this.content = content;
        this.checked = checked.equals("true");
        this.id = id;
    }
}

