package com.unal.larim.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by JuanCamilo on 15/07/2015.
 */
public class Participant implements Serializable {
    public final static String TYPE_NORMAL = "";
    public final static String TYPE_SCIENTIFIC_ORGANIZING_COMMMITTE = "SC";
    public final static String TYPE_LOCAL_ORGANIZING_COMMITTE = "LC";
    public final static String TYPE_REVIEWS_TALK = "RT";
    public final static String TYPE_INVITED_TALK = "IT";
    public final static String TYPE_EXTERNAL_LOGISTICS_SUPPORT = "EC";
    public final static String TYPE_INTERNAL_LOGISTICS_SUPPORT = "A";
    public String name;
    public String email;
    public String institution;
    public String country;
    public String type;
    /* every paper has his own set of keywords each one in one line separated by comas*/
    public ArrayList<String> keywords;
    public ArrayList<String> paperUrls;

    public Participant(String name, String email,
                       String institution, String country) {
        this.name = name;
        this.email = email;
        this.institution = institution;
        this.country = country;
        this.type = TYPE_NORMAL;

    }

    @Override
    public String toString() {
        return name;
    }
}