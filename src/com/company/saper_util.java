package com.company;

public class saper_util {
    private int bok;
    private int bomby;
    private int flags;

    public saper_util(int bok, int bomby) {
        this.bok = bok;
        this.bomby = bomby;
        this.flags = bomby;
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
