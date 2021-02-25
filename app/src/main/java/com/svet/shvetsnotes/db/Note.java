package com.svet.shvetsnotes.db;

import android.content.Context;
import android.text.format.DateUtils;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String subtitle;
    @ColumnInfo
    private Long calendarLong;
    @ColumnInfo
    private Long lastChangedLong;

    public Note(String title, String subtitle, Long calendarLong, Long lastChangedLong) {
        this.title = title;
        this.subtitle = subtitle;
        this.calendarLong = calendarLong;
        this.lastChangedLong = lastChangedLong;
    }

    public void setLastChangedLong(Long lastChangedLong) {
        this.lastChangedLong = lastChangedLong;
    }

    public Long getLastChangedLong() {
        return lastChangedLong;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Long getCalendarLong() {
        return calendarLong;
    }

    public void setCalendarLong(Long calendarLong) {
        this.calendarLong = calendarLong;
    }

    public Calendar getCalendarDate() {
        if (calendarLong == 0) return null;
        Calendar calendarDate = new GregorianCalendar();
        calendarDate.setTimeInMillis(calendarLong);
        return calendarDate;
    }

    public String calendarDateToString(Context context) {
        if (calendarLong == 0) return "";
        return DateUtils.formatDateTime(context,
                calendarLong,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME);
    }

    public boolean isNotVisibleText() {
        if (title == null || title.equals("")) {
            if (subtitle == null || subtitle.equals("")) return true;
        }
        return false;
    }
}

