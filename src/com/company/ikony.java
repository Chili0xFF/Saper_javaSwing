package com.company;

import javax.swing.*;

public enum ikony {
    questionMark(new ImageIcon("E:\\Java\\Saper\\src\\com\\company\\images\\questionMark.png")),
    bomb(new ImageIcon("E:\\Java\\Saper\\src\\com\\company\\images\\bomb.png")),
    flag(new ImageIcon("E:\\Java\\Saper\\src\\com\\company\\images\\flag.png")),
    jeden(new ImageIcon("E:\\Java\\Saper\\src\\com\\company\\images\\1.png")),
    dwa(new ImageIcon("E:\\Java\\Saper\\src\\com\\company\\images\\2.png")),
    trzy(new ImageIcon("E:\\Java\\Saper\\src\\com\\company\\images\\3.png")),
    cztery(new ImageIcon("E:\\Java\\Saper\\src\\com\\company\\images\\4.png")),
    piec(new ImageIcon("E:\\Java\\Saper\\src\\com\\company\\images\\5.png")),
    szesc(new ImageIcon("E:\\Java\\Saper\\src\\com\\company\\images\\6.png")),
    siedem(new ImageIcon("E:\\Java\\Saper\\src\\com\\company\\images\\7.png")),
    osiem(new ImageIcon("E:\\Java\\Saper\\src\\com\\company\\images\\8.png"));

    Icon a;
    ikony(ImageIcon b){
        this.a=b;
    }
    public Icon getIcon(){
        return a;
    }
}
