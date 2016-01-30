package com.unal.larim.Data;

import java.io.Serializable;

/**
 * Created by JuanCamilo on 15/07/2015.
 */
public class Participant implements Serializable {
    private long ID;
    private String name;
    private String email;
    private String institution;
    private String country;
    private String type;
    private String helpType;
    private String resume;
    private int image;

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

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getHelpType() {
        return helpType;
    }

    public void setHelpType(String helpType) {
        this.helpType = helpType;
    }

    public String getResume() {
        return resume;
    }

    public int getImage() {
        return image;
    }

    /***
     * @param name        full name of participant
     * @param email       of participant
     * @param institution
     * @param country     code
     * @param type
     * @param ID     participantID
     * @param resume      html text with all description
     * @param image       resource identifier of participant image
     */
    public Participant(String name, String email, String institution,
                       String country, String type,String resume, int image, long ID) {
        this.name = name;
        this.email = email;
        this.institution = institution;
        this.country = country;
        if (type == null) {
            type = "null";
        }
        this.type = type;
        this.ID =ID;
        if (resume == null) {
            resume = "null";
        }
        this.resume = resume;
        this.image = image;
    }

    @Override
    public String toString() {
        return name;
    }
}