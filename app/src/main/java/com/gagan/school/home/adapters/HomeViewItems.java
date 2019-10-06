package com.gagan.school.home.adapters;

import com.gagan.school.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gagan S Patil on 26/9/19.
 */
public class HomeViewItems {
    private int icon;
    private int iconColor;
    private String text;
    private int bgColor;

    public int getIcon() {
        return icon;
    }

    public int getIconColor() {
        return iconColor;
    }

    public String getText() {
        return text;
    }

    public HomeViewItems(int icon_color, int icon, String text,int bgColor) {
        this.icon = icon;
        this.bgColor=bgColor;
        iconColor=icon_color;
        this.text = text;
    }

    public static List<HomeViewItems> getItems() {
        List<HomeViewItems> items=new ArrayList<>();
        items.add(new HomeViewItems(R.drawable.ic_gallery,R.drawable.ic_gallery,
                "Gallery",R.color.download_assignment));
        items.add(new HomeViewItems(R.drawable.ic_result,R.drawable.ic_result,
                "Results",R.color.results));
        items.add(new HomeViewItems(R.drawable.ic_notification,R.drawable.ic_notification,
                "Notifications",R.color.video_tut));
        items.add(new HomeViewItems(R.drawable.ic_contest,R.drawable.ic_contest,
                "Contest",R.color.contest));
        items.add(new HomeViewItems(R.drawable.ic_calendar,R.drawable.ic_calendar,
                "Calendar",R.color.caledar));
        items.add(new HomeViewItems(R.drawable.ic_timetable,R.drawable.ic_timetable,
                "Timetable",R.color.timetable));
      return items;
    }

    public int getBg() {
        return bgColor;
    }
}
