package domain;

import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by Arscd on 07.12.2015.
 */
public class Clip {
    private String name;
    private String pictureURL;
    private Drawable picture;
    private List<String> artists;
    private Long viewCount;

    public Clip(String name, String pictureURL, List<String> artists, Long viewCount) {
        this.name = name;
        this.artists = artists;
        this.viewCount = viewCount;
        this.pictureURL = pictureURL;
    }

    public String listTotal(List<String> artists) {
        String total = "";
        if (!artists.isEmpty()) {
            for (String s : artists) {
                total += s;
            }
        } else total = "неизвестный исполнитель";
        return total;
    }


    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArtists() {

        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(Drawable picture) {
        this.picture = picture;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

}
