package com.gagan.school.home.adapters.model;

import com.gagan.school.library.view.adapter.RvItems;

import java.io.Serializable;

public class NotificationModel implements RvItems, Serializable {
    String notification_type;
    String notificactionDetails;
    private String heading;
    private String data;
    private String time;

    public void setType(String notification_type) {
        this.notification_type = notification_type;
    }

    public void setDetails(String notificactionDetails) {
        this.notificactionDetails = notificactionDetails;
    }

    public String getType() {
        return notification_type;
    }

    public void setHeading(String heading) {

        this.heading = heading;
    }

    public String getHeading() {
        return heading;
    }

    public void setDatas(String s) {
        this.data = s;
    }

    public String getData() {
        return data;
    }

    public String getNotificactionDetails() {
        return notificactionDetails;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String date) {
        time = date;
    }
}
