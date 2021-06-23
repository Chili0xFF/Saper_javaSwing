package com.company;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class soundBoard {

    public void play(String command){
        String filepath = "";
        switch(command) {
            case "win":
                filepath = "E:\\Java\\Saper\\src\\com\\company\\sounds\\fanfars.wav";
                break;
            case "lose":
                filepath = "E:\\Java\\Saper\\src\\com\\company\\sounds\\explosion.wav";
                break;
            case "click":
                filepath = "E:\\Java\\Saper\\src\\com\\company\\sounds\\blop2.wav";
        }
        try {
            File musicFile = new File(filepath);
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(musicFile);
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.start();
            if(command.equals("click"))Thread.sleep(clip.getMicrosecondLength()/4000);
        } catch (Exception e){
            System.out.println("Explosion error");
        }
    }
}