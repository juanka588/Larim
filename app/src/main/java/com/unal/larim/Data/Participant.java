package com.unal.larim.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by JuanCamilo on 15/07/2015.
 */
public class Participant implements Serializable {
    public String name;
    public String email;
    public String institution;
    public String country;
    public String type;
    /* every paper has his own set of keywords each one in one line separated by comas*/
    public ArrayList<String> keywords;
    public ArrayList<String> paperUrls;

    public Participant(String name, String email,
                       String institution, String country,String type) {
        this.name = name;
        this.email = email;
        this.institution = institution;
        this.country = country;
        this.type = type;

    }

    @Override
    public String toString() {
        return name;
    }
}