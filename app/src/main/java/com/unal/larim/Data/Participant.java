package com.unal.larim.Data;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by JuanCamilo on 15/07/2015.
 */
public class Participant implements Parcelable {
    private long ID;
    private String name;
    private String email;
    private String institution;
    private String country;
    private String type;
    private String helpType;
    private String resume;
    private String image;


    /***
     * @param name        full name of participant
     * @param email       of participant
     * @param institution
     * @param country     code
     * @param type
     * @param ID          participantID
     * @param resume      html text with all description
     * @param image       resource identifier of participant image
     */
    public Participant(String name, String email, String institution,
                       String country, String type, String resume, String image, long ID) {
        this.name = name;
        this.email = email;
        this.institution = institution;
        this.country = country;
        if (type == null) {
            type = "null";
        }
        this.type = type;
        this.ID = ID;
        if (resume == null) {
            resume = "null";
        }
        this.resume = resume;
        this.image = image;
    }

    protected Participant(Parcel in) {
        ID = in.readLong();
        name = in.readString();
        email = in.readString();
        institution = in.readString();
        country = in.readString();
        type = in.readString();
        helpType = in.readString();
        resume = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(ID);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(institution);
        dest.writeString(country);
        dest.writeString(type);
        dest.writeString(helpType);
        dest.writeString(resume);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Participant> CREATOR = new Creator<Participant>() {
        @Override
        public Participant createFromParcel(Parcel in) {
            return new Participant(in);
        }

        @Override
        public Participant[] newArray(int size) {
            return new Participant[size];
        }
    };

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

    public String getImage() {
        return image;
    }


    @Override
    public String toString() {
        return name;
    }
}