package com.wonyoung.moveit;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.logging.FileHandler;

/**
 * Created by wonyoung.jang on 2014-04-02.
 */
public class FileManager {
    private Context context;

    public FileManager(Context context) {

        this.context = context;
    }

    public void move(String sourceFolder, String targetFolder, String regex) {
        File source = new File(sourceFolder);
        File target = new File(targetFolder);
        Log.d("move", String.format("%s => %s by %s", sourceFolder, targetFolder, regex));

        if (!source.exists() || !source.isDirectory()) {
            return;
        }
        if (!target.isDirectory()) {
            return;
        }

        if (!target.exists()) {
            target.mkdir();
        }

        moveFile(source, target, regex);
    }

    private void moveFile(File source, File target, String regex) {
        for (File file : source.listFiles()) {
            String filename = file.getName();
            if (file.isDirectory()) {
                continue;
            }

            if (filename.matches(regex)) {
                String sourceFullname = file.getAbsolutePath();
                String targetFullname = target.getAbsolutePath() + "/" + filename;
                Log.d("move", "Move " + sourceFullname + " to " + target.getAbsolutePath());
                file.renameTo(new File(targetFullname));
                if (filename.matches(".*[(jpg)|(png)|(gif)|(bmp)|(webp)|(3gp)|(mkv)]")) {
                    Log.d("move", "media scan " + targetFullname);
                    mediaScan(sourceFullname);
                    mediaScan(targetFullname);
                }
            }
        }
    }

    private void mediaScan(String sourceFullname) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + sourceFullname)));
    }
}
