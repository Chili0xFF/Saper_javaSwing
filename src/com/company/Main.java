package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Main {
    public static saper_util saper = new saper_util(8,6);
    public static Pole[][] pole = new Pole[saper.getBok()][saper.getBok()];
    public static JPanel plansza = new JPanel();
    //Budowanie samego window//
    public static JFrame window = frame();
    public static JLabel statusWin = new JLabel();
    public static JLabel flagInt = new JLabel();
    public static soundBoard soundBoard = new soundBoard();
    //Koniec budowania window//


    public static void main(String[] args) {
        //Bombimy//
        bombimy(pole);
        //Zabombianie zakonczone//
        //Guzikujemy//
        JPanel plansza = plansza(saper.getBok(), pole);
        window.add(plansza);
        //Koniec guzikowania//
        //Tworzenie menu//
        JMenuBar menu = menu(pole,plansza);
        menu.setSize(700,20);
        window.setJMenuBar(menu);
        //Menu stworzone//
        window.setVisible(true);
    }
    private static void inicjalizuj(){
        saper.setFlags(saper.getBomby());
        pole = new Pole[saper.getBok()][saper.getBok()];
        bombimy(pole);
        plansza = new JPanel();
        plansza = plansza(saper.getBok(), pole);

        window.dispose();
        window = new JFrame();
        window = frame();
        window.setVisible(true);

        window.add(plansza);
        JMenuBar menu = menu(pole,plansza);
        window.setJMenuBar(menu);
    }
    private static JPanel plansza(int a, Pole[][] pole){
        JPanel mapa = new JPanel(new GridLayout(a+1,a+1));
        JButton[][] tablicaButton = new JButton[a][a];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                tablicaButton[i][j]=new JButton(ikony.questionMark.getIcon());
                tablicaButton[i][j].setMnemonic('q');
                int finalI = i;
                int finalJ = j;
                tablicaButton[i][j].addMouseListener(new MouseListener(){
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(statusWin.getText().equals("Trwa")){
                            soundBoard.play("click");
                            if(!SwingUtilities.isRightMouseButton(e)){
                                ustawIkone(pole,tablicaButton,finalI,finalJ);
                            }
                            else{
                                if(tablicaButton[finalI][finalJ].getIcon()==ikony.flag.getIcon()){
                                    tablicaButton[finalI][finalJ].setIcon(ikony.questionMark.getIcon());
                                    tablicaButton[finalI][finalJ].setMnemonic('q');
                                    int temp = saper.getFlags();
                                    saper.setFlags(temp+1);
                                }
                                else if(tablicaButton[finalI][finalJ].getMnemonic()==76){

                                }
                                else if(saper.getFlags()>0){
                                    tablicaButton[finalI][finalJ].setIcon(ikony.flag.getIcon());
                                    tablicaButton[finalI][finalJ].setMnemonic('f');
                                    int temp = saper.getFlags();
                                    saper.setFlags(temp-1);
                                }
                                flagInt.setText(""+saper.getFlags());
                            }
                            sprawdzenie(pole,tablicaButton);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });
                mapa.add(tablicaButton[i][j]);
            }
        }
        JLabel flagi = new JLabel("Flagi: ");
        flagInt.setText(""+saper.getFlags());
        JLabel status = new JLabel("Status gry: ");
        statusWin.setText("Trwa");
        mapa.add(flagi);mapa.add(flagInt);mapa.add(status);mapa.add(statusWin);
        return mapa;
    }
    private static JFrame frame(){
        JFrame window = new JFrame("S23049");
        window.setSize(700,700);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return window;
    }
    private static JMenuBar menu(Pole[][] pole,JPanel plansza){
        JMenuBar mb=new JMenuBar();
        JMenu Gra=new JMenu("Gra");
        JMenu Trudnosc=new JMenu("Trudność");
        JMenuItem GraNowaGra=new JMenuItem("Nowa Gra");
        GraNowaGra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inicjalizuj();
            }
        });
        JMenuItem GraRanking=new JMenuItem("Ranking");
        JMenuItem TrudnoscEasy=new JMenuItem("łatwy (8x8::6)");
        TrudnoscEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saper.setBok(8);
                saper.setBomby(6);
                inicjalizuj();
            }
        });
        JMenuItem TrudnoscMedium=new JMenuItem("średni (12x12::18)");
        TrudnoscMedium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saper.setBok(12);
                saper.setBomby(18);
                inicjalizuj();
            }
        });
        JMenuItem TrudnoscHard=new JMenuItem("trudny (15x15::40)");
        TrudnoscHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saper.setBok(15);
                saper.setBomby(40);
                inicjalizuj();
            }
        });
        Gra.add(GraNowaGra); //Gra.add(GraRanking);
        Trudnosc.add(TrudnoscEasy);Trudnosc.add(TrudnoscMedium);Trudnosc.add(TrudnoscHard);
        mb.add(Gra);mb.add(Trudnosc);
        return mb;
    }
    private static void bombimy(Pole[][] pole) {
        Random random = new Random();
        for (int i = 0; i < saper.getBok(); i++) {
            for (int j = 0; j < saper.getBok(); j++) {
                pole[i][j]=new Pole();
            }
        }
        for (int i = saper.getBomby(); i>0; i--) {
            int rand1 = random.nextInt(saper.getBok());
            int rand2 = random.nextInt(saper.getBok());
            int temp;
            if(pole[rand1][rand2].getWartosc().equals(" ")){
                pole[rand1][rand2].setWartosc("B");
                //pierwsza wartosc to Y druga to X
                if(rand1>0){
                    if(rand2>0){
                        if(!pole[rand1-1][rand2-1].getWartosc().equals("B")){
                            if(pole[rand1-1][rand2-1].getWartosc().equals(" "))temp = 0;
                            else temp = Integer.parseInt(pole[rand1-1][rand2-1].getWartosc());
                            temp++;
                            pole[rand1-1][rand2-1].setWartosc(""+temp);
                        }
                    }
                    if(!pole[rand1-1][rand2].getWartosc().equals("B")){
                        if(pole[rand1-1][rand2].getWartosc().equals(" "))temp = 0;
                        else temp = Integer.parseInt(pole[rand1-1][rand2].getWartosc());
                        temp++;
                        pole[rand1-1][rand2].setWartosc(""+temp);
                    }
                    if(rand2< saper.getBok()-1){
                        if(!pole[rand1-1][rand2+1].getWartosc().equals("B")){
                            if(pole[rand1-1][rand2+1].getWartosc().equals(" "))temp = 0;
                            else temp = Integer.parseInt(pole[rand1-1][rand2+1].getWartosc());
                            temp++;
                            pole[rand1-1][rand2+1].setWartosc(""+temp);
                        }
                    }
                }
                if(rand2>0){
                    if(!pole[rand1][rand2-1].getWartosc().equals("B")){
                        if(pole[rand1][rand2-1].getWartosc().equals(" "))temp = 0;
                        else temp = Integer.parseInt(pole[rand1][rand2-1].getWartosc());
                        temp++;
                        pole[rand1][rand2-1].setWartosc(""+temp);
                    }
                }
                if(!pole[rand1][rand2].getWartosc().equals("B")){
                    if(pole[rand1][rand2].getWartosc().equals(" "))temp = 0;
                    else temp = Integer.parseInt(pole[rand1][rand2].getWartosc());
                    temp++;
                    pole[rand1][rand2].setWartosc(""+temp);
                }
                if(rand2< saper.getBok()-1){
                    if(!pole[rand1][rand2+1].getWartosc().equals("B")){
                        if(pole[rand1][rand2+1].getWartosc().equals(" "))temp = 0;
                        else temp = Integer.parseInt(pole[rand1][rand2+1].getWartosc());
                        temp++;
                        pole[rand1][rand2+1].setWartosc(""+temp);
                    }
                }
                if(rand1<saper.getBok()-1){
                    if(rand2>0){
                        if(!pole[rand1+1][rand2-1].getWartosc().equals("B")){
                            if(pole[rand1+1][rand2-1].getWartosc().equals(" "))temp = 0;
                            else temp = Integer.parseInt(pole[rand1+1][rand2-1].getWartosc());
                            temp++;
                            pole[rand1+1][rand2-1].setWartosc(""+temp);
                        }
                    }
                    if(!pole[rand1+1][rand2].getWartosc().equals("B")){
                        if(pole[rand1+1][rand2].getWartosc().equals(" "))temp = 0;
                        else temp = Integer.parseInt(pole[rand1+1][rand2].getWartosc());
                        temp++;
                        pole[rand1+1][rand2].setWartosc(""+temp);
                    }
                    if(rand2<saper.getBok()-1){
                        if(!pole[rand1+1][rand2+1].getWartosc().equals("B")){
                            if(pole[rand1+1][rand2+1].getWartosc().equals(" "))temp = 0;
                            else temp = Integer.parseInt(pole[rand1+1][rand2+1].getWartosc());
                            temp++;
                            pole[rand1+1][rand2+1].setWartosc(""+temp);
                        }
                    }
                }


            }else i++;
        }
        /*for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                System.out.print(pole[i][j].getWartosc()+" ");
            }
            System.out.println("");
        }*/
    }
    private static void ustawIkone(Pole[][] pole, JButton[][] button,int i, int j){
        if(button[i][j].getIcon()!=ikony.flag.getIcon()){
            String komand = pole[i][j].getWartosc();
            switch (komand){
                case "B":
                    button[i][j].setIcon(ikony.bomb.getIcon());
                    button[i][j].setMnemonic('b');
                    Color red = new Color(255,0,0);
                    button[i][j].setBackground(red);
                    if(statusWin.getText().equals("Trwa"))przegrana(pole,button);
                    break;
                case " ":
                    button[i][j].setIcon(ikony.flag.getIcon());
                    button[i][j].setMnemonic('e');
                    button[i][j].setVisible(false);
                    if(i>0){
                        if(j>0)if(button[i-1][j-1].getIcon()==ikony.questionMark.getIcon())ustawIkone(pole,button,i-1,j-1);
                        if(button[i-1][j].getIcon()==ikony.questionMark.getIcon())ustawIkone(pole,button,i-1,j);
                        if(j<pole[0].length-1)if(button[i-1][j+1].getIcon()==ikony.questionMark.getIcon())ustawIkone(pole,button,i-1,j+1);
                    }
                    if(j>0)if(button[i][j-1].getIcon()==ikony.questionMark.getIcon())ustawIkone(pole,button,i,j-1);
                    if(j<pole[0].length-1)if(button[i][j+1].getIcon()==ikony.questionMark.getIcon())ustawIkone(pole,button,i,j+1);
                    if(i<pole.length-1) {
                        if(j>0)if(button[i+1][j-1].getIcon()==ikony.questionMark.getIcon())ustawIkone(pole,button,i+1,j-1);
                        if(button[i+1][j].getIcon()==ikony.questionMark.getIcon())ustawIkone(pole, button, i + 1, j);
                        if(j<pole[0].length-1)if(button[i+1][j+1].getIcon()==ikony.questionMark.getIcon())ustawIkone(pole, button, i + 1, j + 1);
                    }
                    break;
                case "1":
                    button[i][j].setIcon(ikony.jeden.getIcon());
                    button[i][j].setMnemonic('l');
                    break;
                case "2":
                    button[i][j].setIcon(ikony.dwa.getIcon());
                    button[i][j].setMnemonic('l');
                    break;
                case "3":
                    button[i][j].setIcon(ikony.trzy.getIcon());
                    button[i][j].setMnemonic('l');
                    break;
                case "4":
                    button[i][j].setIcon(ikony.cztery.getIcon());
                    button[i][j].setMnemonic('l');
                    break;
                case "5":
                    button[i][j].setIcon(ikony.piec.getIcon());
                    button[i][j].setMnemonic('l');
                    break;
                case "6":
                    button[i][j].setIcon(ikony.szesc.getIcon());
                    button[i][j].setMnemonic('l');
                    break;
                case "7":
                    button[i][j].setIcon(ikony.siedem.getIcon());
                    button[i][j].setMnemonic('l');
                    break;
                case "8":
                    button[i][j].setIcon(ikony.osiem.getIcon());
                    button[i][j].setMnemonic('l');
                    break;
            }
            //Nie mogłem znaleźć opcji aby wyłączyć guzik i jednocześnie go nie wyszarzać, więc
            //zamiast tego usuwam jedyny mouselistener jaki jest :>
            button[i][j].removeMouseListener(button[i][j].getMouseListeners()[0]);
        }
    }
    private static void sprawdzenie(Pole[][] pole,JButton[][] buttons){
        boolean wygrana=true;
        //Set mnemonic przerabia char na inty, najpewniej według ASCII
        //66 = bomb ; 69 = empty ; 70 = flaga ; 76 = liczba ; 81 = questionMark ;

        for (int i = 0; i<pole.length&& wygrana; i++) {
            for (int j = 0; j < pole[0].length; j++) {
                int mnemo = buttons[i][j].getMnemonic();
                String kar = pole[i][j].getWartosc();
                //warunek wygranej, każda bomba w pole[][] musi mieć przypisany mnemonic flagi na buttonie;
                if(kar.equals("B")){
                    if (mnemo!=70){
                        wygrana=false;
                        break;
                    }
                }
            }
        }
        if(wygrana)wygrana();
    }
    private static void odslonBomby(Pole[][] pola, JButton[][] buttons){
        for (int i = 0; i < saper.getBok(); i++) {
            for (int j = 0; j < saper.getBok(); j++) {
                if(pola[i][j].getWartosc().equals("B")){
                    ustawIkone(pola,buttons,i,j);
                }
            }
        }
    }
    private static void przegrana(Pole[][] pola, JButton[][] buttons){
        statusWin.setText("PRZEGRANA");
        soundBoard.play("lose");
        JOptionPane.showMessageDialog(null,"PRZEGRANA");
        odslonBomby(pola,buttons);

    }
    private static void wygrana(){
        statusWin.setText("WYGRANA");
        soundBoard.play("win");
        JOptionPane.showMessageDialog(null,"WYGRANA");
        System.out.println("Win!");
    }
}

//"Gdy nakładam prześcieradło na materac to znika mi rama od łóżka" type of situation

//Bajery
//Dźwięki przy przegranej, wygranej oraz klikaniu (Wykorzystać funkcję mouseClicked która narazie jest pusta)
//Ranking z pliku, decydowany na podstawie czasu. Czas rozpoczęcia gry do zmiennej cyk, potem od czasu zakończenia