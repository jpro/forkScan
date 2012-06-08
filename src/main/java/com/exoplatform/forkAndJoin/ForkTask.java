package com.exoplatform.forkAndJoin;

import jsr166y.RecursiveAction;
import java.io.File;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dozie
 * Date: 08.06.12
 * Time: 10:04
 */
public class ForkTask extends RecursiveAction {
    String searchPath;
    final int level;

    ForkTask(String searchPath, int level) {
        this.searchPath = searchPath;
        this.level = level;
    }

    protected void compute() {
        File file = new File(searchPath);
        File[] listFiles = file.listFiles();
        ArrayList<ForkTask> taskList = new ArrayList<ForkTask>();

        if (level >= 0) {
            if (listFiles != null) {
                for(File currentFile: listFiles) {
                    if (currentFile.isDirectory()) {
                        System.out.println("(d): " + currentFile.getAbsoluteFile());
                    } else {
                        System.out.println("(f): " + currentFile.getAbsolutePath() + " Size: " + currentFile.length() + " bytes");
                    }
                    taskList.add(new ForkTask(currentFile.getAbsolutePath(), level - 1));
                }
            }
        }

        invokeAll(taskList);
    }
}
