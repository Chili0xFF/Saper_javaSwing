package com.company;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class soundBoard {

    public soundBoard() {
    }
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
/*

    private AudioInputStream explosion;
    private AudioInputStream fanfars;
    private AudioInputStream click;

    public soundBoard() {
        String filepath = "E:\\Java\\Saper\\src\\com\\company\\sounds\\explosion.wav";
        try {
            File musicFile = new File(filepath);
            this.explosion = AudioSystem.getAudioInputStream(musicFile);
        } catch (Exception e){
            System.out.println("Explosion error");
        }
        filepath = "E:\\Java\\Saper\\src\\com\\company\\sounds\\fanfars.wav";
        try {
            File musicFile = new File(filepath);
            this.fanfars = AudioSystem.getAudioInputStream(musicFile);
        } catch (Exception e){
            System.out.println("fanfars error");
        }
        filepath = "E:\\Java\\Saper\\src\\com\\company\\sounds\\blop2.wav";
        try {
            File musicFile = new File(filepath);
            this.click = AudioSystem.getAudioInputStream(musicFile);
        } catch (Exception e){
            System.out.println("click error");
        }
    }

    private AudioInputStream getExplosion() {
        return explosion;
    }

    private AudioInputStream getFanfars() {
        return fanfars;
    }

    private AudioInputStream getClick() {
        return click;
    }
    public void play(String command) throws LineUnavailableException, IOException, InterruptedException {
        Clip clip = AudioSystem.getClip();
        switch(command){
            case "win":
                clip.open(this.getFanfars());
                clip.start();
                JOptionPane.showMessageDialog(null,"WYGRANA");
                break;
            case "lose":
                clip.open(this.getExplosion());
                clip.start();
                JOptionPane.showMessageDialog(null,"PRZEGRANA");
                break;
            case "click":
                System.out.println("No klikam no");
                clip.open(this.getClick());
                clip.start();
                Thread.sleep(clip.getMicrosecondLength()/4000);
                System.out.println(""+clip.getMicrosecondLength());
                clip.close();
                System.out.println(""+clip.getMicrosecondLength());
                break;
            default:
                System.out.println("command = default = "+command);
                break;
        }
        clip.drain();
        clip.close();
    }*/