package com.unal.larim.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JuanCamilo on 15/10/2015.
 */
public class AuditoriumPlace implements Parcelable{
    private int icon;
    private String image;
    private String title;

    public AuditoriumPlace(int icon, String image, String title) {
        this.icon = icon;
        this.image = image;
        this.title = title;
    }

    protected AuditoriumPlace(Parcel in) {
        icon = in.readInt();
        image = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(icon);
        dest.writeString(image);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AuditoriumPlace> CREATOR = new Creator<AuditoriumPlace>() {
        @Override
        public AuditoriumPlace createFromParcel(Parcel in) {
            return new AuditoriumPlace(in);
        }

        @Override
        public AuditoriumPlace[] newArray(int size) {
            return new AuditoriumPlace[size];
        }
    };

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
