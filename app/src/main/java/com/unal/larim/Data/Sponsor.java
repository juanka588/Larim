package com.unal.larim.Data;

import java.io.Serializable;

/**
 * Created by JuanCamilo on 09/07/2015.
 */
public class Sponsor implements Serializable {
    public String name;
    public int icon;
    public String url;
    public String content;

    public Sponsor(String name, int icon, String url, String content) {
        this.name = name;
        this.icon = icon;
        this.url = url;
        this.content = content;
    }
}
