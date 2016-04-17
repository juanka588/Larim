package com.unal.larim.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JuanCamilo on 09/07/2015.
 */
public class Sponsor implements Parcelable {
    private String name;
    private int icon;
    private String url;
    private String content;

    public Sponsor(String name, int icon, String url, String content) {
        this.name = name;
        this.icon = icon;
        this.url = url;
        this.content = content;
    }

    protected Sponsor(Parcel in) {
        name = in.readString();
        icon = in.readInt();
        url = in.readString();
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(icon);
        dest.writeString(url);
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Sponsor> CREATOR = new Creator<Sponsor>() {
        @Override
        public Sponsor createFromParcel(Parcel in) {
            return new Sponsor(in);
        }

        @Override
        public Sponsor[] newArray(int size) {
            return new Sponsor[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
