package com.unal.larim.Data;

import java.util.ArrayList;

/**
 * Created by JuanCamilo on 27/07/2015.
 */
public class Session {

    public String initials;
    public String title;
    public ArrayList<Conference> conferences;

    public Session(String initials, String title, ArrayList<Conference> conferences) {
        this.initials = initials;
        this.title = title;
        this.conferences = conferences;
    }

}
