package com.unal.larim.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by JuanCamilo on 15/07/2015.
 */
public class Participant implements Serializable {
    public final static String TYPE_NORMAL = "0";
    public final static String TYPE_COMITE1 = "1";
    public final static String TYPE_COMITE2 = "2";
    public final static String TYPE_COMITE3 = "3";
    public String id;
    public String last_name;
    public String first_name;
    public String email;
    public String institution;
    public String country;
    public String type;
    public ArrayList<String> papers;
    /* every paper has his own set of keywords each one in one line separated by comas*/
    public ArrayList<String> keywords;
    public ArrayList<String> paperUrls;

    public Participant(String id, String first_name, String last_name, String email,
                       String institution, String country) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.institution = institution;
        this.country = country;
        this.papers = new ArrayList<>();
        this.keywords = new ArrayList<>();
        this.paperUrls = new ArrayList<>();
        papers.add("Solar System Creation");
        papers.add("HV range for CGA");
        this.type = TYPE_NORMAL;

    }

    public Participant(String first_name, String last_name, String email,
                       String institution, String paper, String keywords,
                       String url, String country, String type) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.institution = institution;
        this.country = country;
        this.papers = new ArrayList<>();
        this.keywords = new ArrayList<>();
        this.paperUrls = new ArrayList<>();
        papers.add(paper);
        this.keywords.add(keywords);
        paperUrls.add(url);
        this.type = type;
    }

    @Override
    public String toString() {
        return last_name + " " + first_name;
    }

    public String getKeywords() {
        StringBuilder sb = new StringBuilder();
        for (String cad : keywords) {
            sb.append(cad);
            sb.append(',');
        }
        return sb.toString();
    }
}