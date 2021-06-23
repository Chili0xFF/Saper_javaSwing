package com.company;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class saper_util {
    private int bok;
    private int bomby;
    private int flags;
    private Date time;
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    public saper_util(int bok, int bomby) {
        this.bok = bok;
        this.bomby = bomby;
        this.flags = bomby;
        this.time = new Date(System.currentTimeMillis());
    }


    public Date getTime() {
        return time;
    }

    public void resetTime() {
        this.time = new Date(System.currentTimeMillis());
    }

    public int getBok() {
        return bok;
    }

    public void setBok(int bok) {
        this.bok = bok;
    }

    public int getBomby() {
        return bomby;
    }

    public void setBomby(int bomby) {
        this.bomby = bomby;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }
}
