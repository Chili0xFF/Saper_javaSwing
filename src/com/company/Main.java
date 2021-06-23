package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
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
    private static void initRecords(int dif){
        //Tworzymy okno rekordów//
        JFrame records = new JFrame("Rekordy");
        records.setSize(300,300);
        JPanel panelki = new JPanel(new GridLayout(11,5));
        JLabel[][] rekordy = new JLabel[11][5];
        //Wczytujemy plik//
        BufferedReader csv = null;
        try {
            if (dif == 0) csv = new BufferedReader(new FileReader("E:\\Java\\Saper\\src\\com\\company\\csvs\\rekordEasy.csv"));
            if (dif == 1) csv = new BufferedReader(new FileReader("E:\\Java\\Saper\\src\\com\\company\\csvs\\rekordMedium.csv"));
            if (dif == 2) csv = new BufferedReader(new FileReader("E:\\Java\\Saper\\src\\com\\company\\csvs\\rekordHigh.csv"));
            String row;
            int i =-1;
            while((row = csv.readLine()) !=null){
                i++;
                String[] data = row.split(",");
                if(i==0){
                    rekordy[i][0] = new JLabel("Miejsce",SwingConstants.CENTER);
                }else
                {
                    //Wypisujemy wartości z pliku//
                    rekordy[i][0] = new JLabel(""+i,SwingConstants.CENTER);
                }
                rekordy[i][1] = new JLabel(data[0],SwingConstants.CENTER);
                rekordy[i][2] = new JLabel(data[1],SwingConstants.CENTER);
                rekordy[i][3] = new JLabel(data[2],SwingConstants.CENTER);
                rekordy[i][4] = new JLabel(data[3],SwingConstants.CENTER);
                panelki.add(rekordy[i][0]);
                panelki.add(rekordy[i][1]);
                panelki.add(rekordy[i][2]);
                panelki.add(rekordy[i][3]);
                panelki.add(rekordy[i][4]);
            }
            records.add(panelki);
            records.setResizable(false);
            records.setVisible(true);

        }catch (Exception e){
            System.out.println("problem z odczytem pliku: "+dif);
        }
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
                saper.resetTime();
                inicjalizuj();
            }
        });
        JMenuItem Rekordyes = new JMenuItem("Ranking easy");
        Rekordyes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initRecords(0);
            }
        });
        Gra.add(Rekordyes);
        JMenuItem Rekordymid = new JMenuItem("Ranking medium");
        Rekordymid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initRecords(1);
            }
        });
        Gra.add(Rekordymid);
        JMenuItem Rekordy = new JMenuItem("Ranking hard");
        Rekordy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initRecords(2);
            }
        });
        Gra.add(Rekordy);
        JMenuItem TrudnoscEasy=new JMenuItem("łatwy (8x8::6)");
        TrudnoscEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saper.setBok(8);
                saper.setBomby(6);
                saper.resetTime();
                inicjalizuj();
            }
        });
        JMenuItem TrudnoscMedium=new JMenuItem("średni (12x12::18)");
        TrudnoscMedium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saper.setBok(12);
                saper.setBomby(18);
                saper.resetTime();
                inicjalizuj();
            }
        });
        JMenuItem TrudnoscHard=new JMenuItem("trudny (15x15::40)");
        TrudnoscHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saper.setBok(15);
                saper.setBomby(40);
                saper.resetTime();
                inicjalizuj();
            }
        });
        Gra.add(GraNowaGra);
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
        //zmienić warunek wygranej na wszystkie pola
        //ranking z pliku.csv
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
                        if(j>0&&button[i-1][j-1].getIcon()==ikony.questionMark.getIcon())ustawIkone(pole,button,i-1,j-1);
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
                //warunek wygranej, każdpuste
                switch(kar){
                    case " ":
                        if (mnemo!=69){
                            wygrana=false;
                        }
                        break;
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                        if(mnemo!=76){
                            wygrana=false;
                        }
                        break;
                }
            }
        }
        if(wygrana){
            wygrana();
            odslonBomby(pole,buttons);
        }
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
        long[] czas = giveMeTimeElapsed();  //[0] -> hours [1] -> minutes [2] -> seconds;
        podmienRanking(czas);
        JOptionPane.showMessageDialog(null,"WYGRANA");
    }

    private static boolean podmienRanking(long[] czas) {
        BufferedReader csv = null;
        try {
            switch (saper.getBok()) {
                case 8:
                    csv = new BufferedReader(new FileReader("E:\\Java\\Saper\\src\\com\\company\\csvs\\rekordEasy.csv"));
                    break;
                case 12:
                    csv = new BufferedReader(new FileReader("E:\\Java\\Saper\\src\\com\\company\\csvs\\rekordMedium.csv"));
                    break;
                case 15:
                    csv = new BufferedReader(new FileReader("E:\\Java\\Saper\\src\\com\\company\\csvs\\rekordHard.csv"));
                    break;
            }
            String row = "";
            int i=-2;

            int[][] dataCzas = new int[10][3];
            String[] dataName = new String[10];

            while((row = csv.readLine()) !=null){
                i++;
                if(i==-1)continue;
                String[] data = row.split(",");
                dataName[i] = data[0];
                dataCzas[i][0] = Integer.parseInt(data[1]);
                dataCzas[i][1] = Integer.parseInt(data[2]);
                dataCzas[i][2] = Integer.parseInt(data[3]);
            }
            for (int j = 0; j < 10; j++) {
                if((dataCzas[j][0]>czas[0])||(dataCzas[j][0]==czas[0]&&dataCzas[j][1]>czas[1])||(dataCzas[j][0]==czas[0]&&dataCzas[j][1]==czas[1]&&dataCzas[j][2]>czas[2])){
                    podmienWypiszRanking(dataCzas,dataName,j,czas);
                    return true;
                }
            }
        }catch(Exception e){
            System.out.println("ERROR PRZY SPRAWDZANIU RANKINGU");
        }

        return true;
    }

    private static void podmienWypiszRanking(int[][] dataCzas, String[] dataName, int j, long[] czas) {
        System.out.println("J: "+j+"   PRZED ZMIANAMI");
        for (int[] dataCzasTemp:dataCzas) {
            System.out.println(""+dataCzasTemp[2]);
        }
        int[][] newDataCzas = new int[10][3];
        for (int i = 9; i >j; i--) {
            //System.out.println("J: "+j+"i:"+i+" ///"+dataCzas[i][2]+" <- "+dataCzas[i-1][2]);
            int[] temp = dataCzas[i-1];
            newDataCzas[i] = temp;
            dataName[i]=dataName[i-1];
        }
        for(int i=0;i<j;i++){
            int[] temp = dataCzas[i];
            newDataCzas[i] = temp;
            dataName[i]=dataName[i];
        }
        System.out.println("PO ZMIANACH");
        for (int[] dataCzasTemp:newDataCzas) {
            System.out.println(""+dataCzasTemp[2]);
        }
        //AKTUALNIE JEST OD 6-24 Włącznie!!!!!
        //Powinno wyrzucić 24, wrzucić PLACEHOLDER,0,0,15 pomiędzy 14 i 16
        newDataCzas[j][0]=Integer.parseInt(String.valueOf(czas[0]));
        newDataCzas[j][1]=Integer.parseInt(String.valueOf(czas[1]));
        newDataCzas[j][2]=Integer.parseInt(String.valueOf(czas[2]));
        dataName[j] = JOptionPane.showInputDialog(null,"NOWY REKORD! PODAJ SWÓJ NICK (3 znaki)");
        System.out.println("PO ZMIANACH I WPISANIU");
        for (int[] dataCzasTemp:newDataCzas) {
            System.out.println(""+dataCzasTemp[2]);
        }
        FileOutputStream csv = null;
        String path = null;
        try {
            switch (saper.getBok()) {
                case 8:
                    path = "E:\\Java\\Saper\\src\\com\\company\\csvs\\rekordEasy.csv";
                    break;
                case 12:
                    path = "E:\\Java\\Saper\\src\\com\\company\\csvs\\rekordMedium.csv";
                    break;
                case 15:
                    path = "E:\\Java\\Saper\\src\\com\\company\\csvs\\rekordHard.csv";
                    break;
            }
            csv = new FileOutputStream(path,true);

            PrintWriter writer = new PrintWriter(path);
            writer.print("");
            writer.close();
            csv.write("Imie".getBytes(StandardCharsets.UTF_8));
            csv.write(",".getBytes(StandardCharsets.UTF_8));
            csv.write("Godzina".getBytes(StandardCharsets.UTF_8));
            csv.write(",".getBytes(StandardCharsets.UTF_8));
            csv.write("Minuta".getBytes(StandardCharsets.UTF_8));
            csv.write(",".getBytes(StandardCharsets.UTF_8));
            csv.write("Sekunda".getBytes(StandardCharsets.UTF_8));
            csv.write("\n".getBytes(StandardCharsets.UTF_8));
            for (int i = 0; i < 10; i++) {
                csv.write(dataName[i].getBytes(StandardCharsets.UTF_8));
                csv.write(("," + newDataCzas[i][0]).getBytes(StandardCharsets.UTF_8));
                csv.write(("," + newDataCzas[i][1]).getBytes(StandardCharsets.UTF_8));
                csv.write(("," + newDataCzas[i][2]).getBytes(StandardCharsets.UTF_8));
                csv.write("\n".getBytes(StandardCharsets.UTF_8));
            }
        }catch(Exception e){
            System.out.println("Problem przy zapisie");
        }
    }

    private static long[] giveMeTimeElapsed() {
        long different = new Date(System.currentTimeMillis()).getTime()-saper.getTime().getTime();
        long elapsedHours = different / (1000 * 60 * 60);
        different = different % (1000 * 60 * 60);
        long elapsedMinutes = different / (1000 * 60);
        different = different % (1000 * 60);
        long elapsedSeconds = different / (1000);
        long[] elapsed = new long[3];
        System.out.println("h: "+elapsedHours+" minutes: "+elapsedMinutes+" seconds: "+elapsedSeconds);
        elapsed[0] = elapsedHours;
        elapsed[1] = elapsedMinutes;
        elapsed[2] = elapsedSeconds;
        return elapsed;
    }
}

//"Gdy nakładam prześcieradło na materac to znika mi rama od łóżka" type of situation

