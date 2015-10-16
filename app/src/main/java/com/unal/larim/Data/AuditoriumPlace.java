package com.unal.larim.Data;

/**
 * Created by JuanCamilo on 15/10/2015.
 */
public class AuditoriumPlace {
    private int icon;
    private String image;
    private String title;

    public AuditoriumPlace(int icon, String image, String title) {
        this.icon = icon;
        this.image = image;
        this.title = title;
    }

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
