package com.resfa;


import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

public class DownloaderThread extends Thread {
    @Override
    public void run() {
        while (!Main.list.isEmpty()) {
            String temp = Main.list.get(0);
            Main.list.remove(0);
            String folder = String.format(Main.folder + "/%d.png", Main.list.size() + 1);
            try {
                Main.downloadPicture(Main.takeLink(temp),folder);
                System.out.printf("|%1d| Картинка успешно скачана!%n",Main.counter);
                Main.counter++;
            } catch (Exception e) {
                System.out.println("|*| Ненашёл ссылку");
            }
        }
    }
}