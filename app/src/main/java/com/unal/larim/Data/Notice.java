package com.unal.larim.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JuanCamilo on 29/06/2015.
 */
public class Notice implements Parcelable {
    private String title;
    private String content;
    private boolean checked;
    private String id;
    private String url;

    public Notice(String id, String title, String content, boolean checked, String url) {
        this.title = title;
        this.content = content;
        this.checked = checked;
        this.id = id;
        this.url = url;
    }

    public Notice(String id, String title, String content, String checked, String url) {
        this(id, title, content, checked.equals("1"), url);
    }

    protected Notice(Parcel in) {
        title = in.readString();
        content = in.readString();
        checked = in.readByte() != 0;
        id = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeByte((byte) (checked ? 1 : 0));
        dest.writeString(id);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notice> CREATOR = new Creator<Notice>() {
        @Override
        public Notice createFromParcel(Parcel in) {
            return new Notice(in);
        }

        @Override
        public Notice[] newArray(int size) {
            return new Notice[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

