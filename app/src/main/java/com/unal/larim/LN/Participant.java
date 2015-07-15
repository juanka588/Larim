package com.unal.larim.LN;

import java.util.ArrayList;

/**
 * Created by JuanCamilo on 15/07/2015.
 */
public class Participant {
    public String id;
    public String last_name;
    public String first_name;
    public String email;
    public String institution;
    public String country;
    public ArrayList<String> papers;
    /* every paper has his own set of keywords each one in one line separated by comas*/
    public ArrayList<String> keywords;

    public Participant(String id, String first_name, String last_name, String email, String institution) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.institution = institution;
        this.papers = new ArrayList<>();
        this.keywords = new ArrayList<>();
        papers.add("https://www.google.com/;Solar System Creation");
        papers.add("https://www.google.com/;Big Data Analysis for Stars Formation");
        papers.add("https://www.google.com/;HV range for CGA");
    }

    @Override
    public String toString() {
        return last_name + " " + first_name;
    }
}