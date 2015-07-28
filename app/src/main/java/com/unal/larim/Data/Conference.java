package com.unal.larim.Data;

import com.unal.larim.LN.Util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JuanCamilo on 20/07/2015.
 */
public class Conference implements Serializable {
    public String session;
    public String hour;
    public String title;
    public String place;
    public String chairman;
    public boolean scheduled;
    public Date date;
    public Participant author;

    /*@param session indicates the session abbreviation
    * @param hour text in format HH:MM:SS
    * @param date in format YYYY-MM-DD*/
    public Conference(String session, String hour, String title, String place, String chairman,
                      String date, boolean scheduled, Participant author) {
        this.session = session;
        this.hour = hour;
        this.title = title;
        this.place = place;
        this.chairman = chairman;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newdate = dateformat.parse(date);
            this.date = newdate;
        } catch (ParseException e) {
            Util.log("Error Fechas", e.toString());
        }
        this.scheduled = scheduled;
        this.author = author;

    }
}
