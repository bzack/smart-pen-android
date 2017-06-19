package com.example.zhangdx14.nfc;

import java.util.Date;

/**
 * Status
 * Green, ok
 * Yellow, days since room temp
 * Red, > 14 day since room temp, or already injected, or pass exp date
 */

public class Hpen {
    private String id;
    private Date expDate;
    private Date injDate;
    private int daysSinceRoomTemp;
    private String status;      // Green, Yellow, Red
    private String note;

    public Hpen(String hpenId) {
        id = hpenId;
        expDate = null;
        injDate = null;
        daysSinceRoomTemp = 0;
        status = "Green";
        note = "ok";
    }

    public String getId() {
        return id;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Date getInjDate() {
        return injDate;
    }

    public void setInjDate(Date injDate) {
        this.injDate = injDate;
    }

    public int getDaysSinceRoomTemp() {
        return daysSinceRoomTemp;
    }

    public void setDaysSinceRoomTemp(int daysSinceRoomTemp) {
        this.daysSinceRoomTemp = daysSinceRoomTemp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
