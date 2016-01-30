package com.unal.larim.Data;

/**
 * Created by JuanCamilo on 03/08/2015.
 */
public class Paper {
    public String title;
    public String keywords;
    public String pdfURL;
    public int participantID;

    public Paper(String title, String keywords, String pdfURL, int participantID) {
        this.title = title;
        this.keywords = keywords;
        this.pdfURL = pdfURL;
        this.participantID = participantID;
    }

    @Override
    public String toString() {
        return title+" keywords: "+keywords;
    }
}
