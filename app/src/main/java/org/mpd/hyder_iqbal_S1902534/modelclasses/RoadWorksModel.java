//Iqbal_Haider_S1902534
package org.mpd.hyder_iqbal_S1902534.modelclasses;

import java.io.Serializable;

public class RoadWorksModel implements Serializable {
     String title;
    String description;
    String link;
    String pubDate;
    String locPoints;

    public RoadWorksModel(String title, String description, String link, String pubDate, String locPoints) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
        this.locPoints = locPoints;
    }

    public RoadWorksModel() {
    }

    public String getLocPoints() {
        return locPoints;
    }

    public void setLocPoints(String locPoints) {
        this.locPoints = locPoints;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
