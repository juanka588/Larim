package com.unal.larim.Data;

import com.unal.larim.LN.Util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JuanCamilo on 20/07/2015.
 */
public class Conference implements Serializable {
    public String title;
    public String place;
    public String hour;
    public Date date;
    public long chairmanID;
    public boolean scheduled;
    public long paperID;
    public String initials;
    public String description;

    /*@param title indicates the conference title
    * @param hour text in format HH:MM:SS
    * @param date in format EPOCH*/
    public Conference(long paperID, String title, String place, String hour, String date, long chairmanID
            , String scheduled, String initials, String description) {
        this.hour = hour;
        this.title = title;
        this.place = place;
        this.chairmanID = chairmanID;
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a EEEE");
        try {
            this.date = new Date(Long.parseLong(date) * 1000);
            Util.log("Current date", simpleFormat.format(this.date));
        } catch (Exception e) {
            this.date = Calendar.getInstance().getTime();
            Util.log("Error Fechas", e.toString());
            Util.log("valor por defecto fecha", this.date.getTime() + "");
        }
        this.scheduled = scheduled.equals("true");
        this.paperID = paperID;
        this.initials = initials;
        this.description = description;

    }

    /*@param session indicates the session abbreviation
    * @param hour text in number format
    * @param scheduled references if the conference is active or not*/
    public Conference(long paperID, String title, String place, String hour, Date date, long chairmanID
            , boolean scheduled, String initials, String description) {
        this.hour = hour;
        this.title = title;
        this.place = place;
        this.chairmanID = chairmanID;
        this.date = date;
        this.scheduled = scheduled;
        this.paperID = paperID;
        this.initials = initials;
        this.description = description;
    }
}
