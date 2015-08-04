package com.unal.larim.Data;

/**
 * Created by JuanCamilo on 03/08/2015.
 */
public class Paper {
    public String title;
    public String keywords;
    public String pdfURL;
    public String participantID;

    public Paper(String title, String keywords, String pdfURL, String participantID) {
        this.title = title;
        this.keywords = keywords;
        this.pdfURL = pdfURL;
        this.participantID = participantID;
    }
}
