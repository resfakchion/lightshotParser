package com.resfa;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static  CopyOnWriteArrayList<String> list;
    public static int counter = 0;
    public static String folder;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите первые три символа ссылки");
        String input = reader.readLine().toLowerCase(Locale.ROOT);
        System.out.println("Введите количество скриншотов");
        int count =  Integer.parseInt(reader.readLine());
        System.out.println("Введите директорию для скриншотов");
        folder = reader.readLine().replace("\\", "/");

        list = new CopyOnWriteArrayList<String>();
        String actualLink = "https://prnt.sc/" + input;
        list = (CopyOnWriteArrayList<String>) fillList(actualLink, list, count);
        long time = System.currentTimeMillis();

       /* for (int i = 0; i < list.size(); i++) {
            String folder = String.format("D:/parser/%d.png", i);
            try {
                downloadPicture(takeLink(list.get(i)), folder);
                System.out.println("Картинка " + i + ".png успешно скачана!");
            } catch (IOException e) {
                System.out.println("Ненашёл ссылку");
            }
        }
        System.out.println(System.currentTimeMillis() - time);

        */


       DownloaderThread downloaderThread1 = new DownloaderThread();
        DownloaderThread downloaderThread2 = new DownloaderThread();
        DownloaderThread downloaderThread3 = new DownloaderThread();
        downloaderThread1.start();
        downloaderThread2.start();
        downloaderThread3.start();


        while (true) {
           if (!downloaderThread1.isAlive() && !downloaderThread2.isAlive() && !downloaderThread3.isAlive()) {
               System.out.println(System.currentTimeMillis() - time);
               break;
           }
        }

    }
    public static Document getPage(String url) throws IOException {
        return Jsoup.parse(new URL(url),1000);
    }
    public static String takeLink(String url) throws IOException {
        Document page = getPage(url);
        Element image =  page.select("div[class=image-constrain js-image-wrap]").first().child(0).child(0);
        String[] strings = image.toString().split("\"");
        return strings[3];
    }
    public static void downloadPicture(String url, String fileName) throws IOException {
        try(InputStream in = URI.create(url).toURL().openStream()) {
            Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);

        }
    }

    public static List<String> fillList(String actualLink ,List<String> list, int count) {
        Random r = new Random();
        String abc = "0123456789abcdefghiijklmnopqrstuvwxyz";
        for (int j = 0; j < count; j++) {
            StringBuilder sb = new StringBuilder(actualLink);
            for (int i = 0; i < 3; i++) {
                char c = abc.charAt(r.nextInt(abc.length()));
                sb.append(c);
            }
            list.add(sb.toString());
        }
        return list;
    }


}
