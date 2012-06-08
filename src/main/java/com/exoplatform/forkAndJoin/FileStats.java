package com.exoplatform.forkAndJoin;

/**
 * Created with IntelliJ IDEA.
 * Класс содержит информацию по чтению данных
 * User: dozie
 * Date: 08.06.12
 * Time: 16:45
 */
class FileStats {
    static long dirCount = 0;
    static long filesCount = 0;
    static long summaryFileSize = 0;

    /**
     * Количество параллельных чтений
     */
    static int parallelsReading = 0;

    /**
     * Количество прямых чтених
     */
    static int singleReading = 0;
}