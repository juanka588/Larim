package com.unal.larim.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.unal.larim.LN.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JuanCamilo on 20/07/2015.
 */
public class Conference implements Parcelable {
    private String title;
    private String place;
    private String hour;
    private Date date;
    private long chairmanID;
    private boolean scheduled;
    private long paperID;
    private String initials;
    private String description;

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

    protected Conference(Parcel in) {
        title = in.readString();
        place = in.readString();
        hour = in.readString();
        chairmanID = in.readLong();
        scheduled = in.readByte() != 0;
        paperID = in.readLong();
        initials = in.readString();
        description = in.readString();
        date = new Date(in.readLong());
    }

    public static final Creator<Conference> CREATOR = new Creator<Conference>() {
        @Override
        public Conference createFromParcel(Parcel in) {
            return new Conference(in);
        }

        @Override
        public Conference[] newArray(int size) {
            return new Conference[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getChairmanID() {
        return chairmanID;
    }

    public void setChairmanID(long chairmanID) {
        this.chairmanID = chairmanID;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    public long getPaperID() {
        return paperID;
    }

    public void setPaperID(long paperID) {
        this.paperID = paperID;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(place);
        dest.writeString(hour);
        dest.writeLong(chairmanID);
        dest.writeByte((byte) (scheduled ? 1 : 0));
        dest.writeLong(paperID);
        dest.writeString(initials);
        dest.writeString(description);
        dest.writeLong(date.getTime());
    }
}

