package com.exoplatform.forkAndJoin;

<<<<<<< HEAD
import jsr166y.RecursiveTask;
=======
import jsr166y.RecursiveAction;

>>>>>>> b0e68177a2251711809c2d805cd8a6f275f1e977
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class, that implement algorithm for reading files and dirs from given path
 */
public class ForkTask extends RecursiveTask<Long> {

    /**
     * List of files and directories in current path
     */
    private File[] listFiles;

    /**
     * Nested directories is a future task
     */
    private ArrayList<ForkTask> taskList = new ArrayList<ForkTask>();

    /**
     * Count nested directories
     */
    private int localDirectoryCount = 0;

    /**
     * Attempt to get list of files in search path
     * @param searchPath - path for get list of nested files
     */
    ForkTask(String searchPath) {
<<<<<<< HEAD
        File file = new File(searchPath);
=======
        this.searchPath = searchPath;
        file = new File(this.searchPath);
>>>>>>> b0e68177a2251711809c2d805cd8a6f275f1e977
        listFiles = file.listFiles();
    }

    /**
     * Give current path and count nested files and directories
     */
    private void init() {
        if (listFiles != null) {
            for(File currentFile: listFiles) {
                if (currentFile.isDirectory() && !isLink(currentFile)) {
                    localDirectoryCount++;
                    Statistic.getInstance().setDirectoriesCount(Statistic.getInstance().getDirectoriesCount() + 1);
                } else if(currentFile.isFile() && !isLink(currentFile)) {
                    Statistic.getInstance().setFilesCount(Statistic.getInstance().getFilesCount() + 1);
                }
            }
        }
    }

    /**
     * Read nested files and directories. If nested directories are over 10, then reading is performed by multitasking otherwise in one task.
     */
    protected Long compute() {
        long res = 0;
        init();
        if (listFiles != null) {
<<<<<<< HEAD
            if (localDirectoryCount <= 10) {
                Statistic.getInstance().setSingleReading(Statistic.getInstance().getSingleReading() + 1);
=======
            if (directoryCount <= 10) {
                FileStats.singleReading++;
>>>>>>> b0e68177a2251711809c2d805cd8a6f275f1e977
                for(File currentFile: listFiles) {
                    ForkTask task = null;
                    if(!isLink(currentFile)) {
                        res += currentFile.length();
                    }
                    if (!isLink(currentFile) && currentFile.isDirectory()) {
                        task = new ForkTask(currentFile.getAbsolutePath());
<<<<<<< HEAD
                        res += task.compute();
                    }
                }
            } else {
                Statistic.getInstance().setParallelsReading(Statistic.getInstance().getParallelsReading() + 1);
=======
                        task.compute();
                    }
                }
            } else {
                FileStats.parallelsReading++;
>>>>>>> b0e68177a2251711809c2d805cd8a6f275f1e977
                for(File currentFile: listFiles) {
                    if (!isLink(currentFile) && currentFile.isDirectory()) {
                        taskList.add(new ForkTask(currentFile.getAbsolutePath()));
<<<<<<< HEAD
                    }
                    if(!isLink(currentFile)) {
                        res += currentFile.length();
                    }
                }

=======
                    }
                }

                /**
                 * Форкаем все задачи
                 */
>>>>>>> b0e68177a2251711809c2d805cd8a6f275f1e977
                invokeAll(taskList);

                for (ForkTask currentTask: taskList) {
                    res += currentTask.join();
                }
            }
        }
        Statistic.getInstance().setSummaryFilesSize(res);
        Statistic.getInstance().setControlEndTime(System.currentTimeMillis());

        return res;
    }

    /**
     * Check if given files is link
     * @param file - current file for check
     * @return - true if file is link and false - if file isn't link
     */
    private boolean isLink(File file) {
        String canonicalPath = "";
        String absolutePath = "";
        try {
            canonicalPath = file.getCanonicalPath();
            absolutePath = file.getAbsolutePath();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(-1);
        }

        return !absolutePath.equals(canonicalPath);
    }


}