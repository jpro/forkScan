package com.exoplatform.forkAndJoin;

import jsr166y.RecursiveAction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Класс считывает файлы и директории по определенному пути
 * User: dozie
 * Date: 08.06.12
 * Time: 10:04
 */
public class ForkTask extends RecursiveAction {
    String searchPath;
    File file;
    File[] listFiles;

    /**
     * Дочерние директории являются будущими задачами для подальшего чтения
     */
    ArrayList<ForkTask> taskList = new ArrayList<ForkTask>();

    /**
     * Количество дочерних директорий
     */
    int directoryCount = 0;

    /**
     * На вход поступает путь(директория) в которой следует произвести чтение
     * @param searchPath
     */
    ForkTask(String searchPath) {
        this.searchPath = searchPath;
        file = new File(this.searchPath);
        listFiles = file.listFiles();
        init();
    }

    /**
     * Берет список текущей директории и считает сколько в ней файлов, дироекторий.
     */
    private void init() {
        if (listFiles != null) {
            for(File currentFile: listFiles) {
                if (currentFile.isDirectory() && !isLink(currentFile)) {
                    directoryCount++;
                    FileStats.dirCount++;
                } else if (currentFile.isFile() && !isLink(currentFile)) {
                    FileStats.filesCount++;
                    FileStats.summaryFileSize += currentFile.length();
                }
            }
        }
    }

    /**
     * Считывает файлы и папки с определенной директории, если вложенных директорий в текущей больше 10, то чтение
     * дочерних директорий происходит в отдельных задачах, иначе в одной.
     */
    protected void compute() {
        if (listFiles != null) {
            if (directoryCount <= 10) {
                FileStats.singleReading++;
                for(File currentFile: listFiles) {
                    ForkTask task = null;
                    if (!isLink(currentFile)) {
                        task = new ForkTask(currentFile.getAbsolutePath());
                        task.compute();
                    }
                }
            } else {
                FileStats.parallelsReading++;
                for(File currentFile: listFiles) {
                    if (!isLink(currentFile)) {
                        taskList.add(new ForkTask(currentFile.getAbsolutePath()));
                    }
                }

                /**
                 * Форкаем все задачи
                 */
                invokeAll(taskList);

                /**
                 * Дожидаемся виполнения задач
                 */
                for (ForkTask currentTask: taskList) {
                    currentTask.join();
                }
            }
        }
        FileStats.controlEndTime = System.currentTimeMillis();
    }

    /**
     * Проверяет, является ли файл ссылкой
     * @param file
     * @return
     */
    private boolean isLink(File file) {
        try {
            String canonicalPath = file.getCanonicalPath();
            String absolutePath = file.getAbsolutePath();
            return !absolutePath.equals(canonicalPath);
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(-1);
        }
        return false;
    }
}