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
    public String chairman;
    public boolean scheduled;
    public String paperID;
    public String initials;
    public String description;

    /*@param title indicates the conference title
    * @param hour text in format HH:MM:SS
    * @param date in format YYYY-MM-DD*/
    public Conference(String paperID, String title, String place, String hour, String date, String chairman
            , String scheduled, String initials, String description) {
        this.hour = hour;
        this.title = title;
        this.place = place;
        this.chairman = chairman;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm a EEEE");
        try {
            this.date = new Date (Long.parseLong(date));
            Util.log("Current date", dateformat.format(this.date));
        } catch (Exception e) {
            this.date = Calendar.getInstance().getTime();
            Util.log("Error Fechas", e.toString());
            Util.log("valor por defecto fecha", this.date.getTime()+"");
        }
        this.scheduled = scheduled.equals("true");
        this.paperID = paperID;
        this.initials = initials;
        this.description = description;

    }

    /*@param session indicates the session abbreviation
    * @param hour text in number format
    * @param scheduled references if the conference is active or not*/
    public Conference(String paperID, String title, String place, String hour, Date date, String chairman
            , boolean scheduled, String initials, String description) {
        this.hour = hour;
        this.title = title;
        this.place = place;
        this.chairman = chairman;
        this.date = date;
        this.scheduled = scheduled;
        this.paperID = paperID;
        this.initials = initials;
        this.description = description;
    }
}
