package com.unal.larim.Data;

import java.io.Serializable;

/**
 * Created by JuanCamilo on 15/07/2015.
 */
public class Participant implements Serializable {
    private String name;
    private String email;
    private String institution;
    private String country;
    private String type;
    private int paperID;
    private String helpType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPaperID() {
        return paperID;
    }

    public void setPaperID(int paperID) {
        this.paperID = paperID;
    }

    public String getHelpType() {
        return helpType;
    }

    public void setHelpType(String helpType) {
        this.helpType = helpType;
    }

    public Participant(String name, String email,
                       String institution, String country, String type) {
        this(name, email, institution, country, type, -1, null);
    }

    public Participant(String name, String email,
                       String institution, String country, String type, int paperID, String helpType) {
        this.name = name;
        this.email = email;
        this.institution = institution;
        this.country = country;
        if (type == null) {
            type = "null";
        }
        this.type = type;
        this.paperID = paperID;
        this.helpType = helpType;
    }

    @Override
    public String toString() {
        return name;
    }
}