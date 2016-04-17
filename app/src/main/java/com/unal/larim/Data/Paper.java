package com.unal.larim.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JuanCamilo on 03/08/2015.
 */
public class Paper implements Parcelable{
    private String title;
    private String keywords;
    private String pdfURL;
    private int participantID;

    public Paper(String title, String keywords, String pdfURL, int participantID) {
        this.title = title;
        this.keywords = keywords;
        this.pdfURL = pdfURL;
        this.participantID = participantID;
    }

    protected Paper(Parcel in) {
        title = in.readString();
        keywords = in.readString();
        pdfURL = in.readString();
        participantID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(keywords);
        dest.writeString(pdfURL);
        dest.writeInt(participantID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Paper> CREATOR = new Creator<Paper>() {
        @Override
        public Paper createFromParcel(Parcel in) {
            return new Paper(in);
        }

        @Override
        public Paper[] newArray(int size) {
            return new Paper[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getPdfURL() {
        return pdfURL;
    }

    public void setPdfURL(String pdfURL) {
        this.pdfURL = pdfURL;
    }

    public int getParticipantID() {
        return participantID;
    }

    public void setParticipantID(int participantID) {
        this.participantID = participantID;
    }

    @Override
    public String toString() {
        return title + " keywords: " + keywords;
    }
}
