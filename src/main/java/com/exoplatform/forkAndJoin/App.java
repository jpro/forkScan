package com.exoplatform.forkAndJoin;

import jsr166y.ForkJoinPool;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: dozie
 * Date: 08.06.12
 * Time: 10:02
 */
public class App {
    public static void main(String[] args) {

        /**
         * Распирсиваем входящий аргумент в коммандной строке, который отвечает за путь просмотра
         */
        if (args.length == 0) {
            System.out.println("Usage: java -jar fork.jar \"path\"\nFor example: java -jar fork.jar /usr");
            System.exit(-1);
        }

        String path = "";
        int level = 0;

        try {
            path = args[0];
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Wrong argument.");
            System.exit(-1);
        }

        /**
         * Подсчитаем сколько времени было затрачено на операцию
         * устанавливаем стартовое значение времени
         */
         FileStats.controlStartTime = System.currentTimeMillis();

        /**
         * Запускаем
         */
        ForkJoinPool fjPool = new ForkJoinPool();
        fjPool.invoke(new ForkTask(path));

        /**
         * Вывод статистики по чтению
         */
        System.out.println("Available proc cores: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Read path: " + path);
        System.out.println("Dir count: " + FileStats.dirCount + "\nFiles count: " + FileStats.filesCount);
        System.out.println("Summary file size: " + FileStats.summaryFileSize + " bytes (" + ((float) FileStats.summaryFileSize / 1024 / 1024 / 1024) + " Gb)");
        System.out.println("Single reading: " + FileStats.singleReading + "\nParallels reading: " + FileStats.parallelsReading);
        System.out.println("Used time : " + (FileStats.controlEndTime - FileStats.controlStartTime) + " milliseconds");
    }
}