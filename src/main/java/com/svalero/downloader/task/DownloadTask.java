package com.svalero.downloader.task;



import com.svalero.downloader.controller.DownloadController;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class DownloadTask extends Task<Integer> {

    private URL url;
    private File file;
    private JSONArray jsonArray = new JSONArray();

    private static final Logger logger = LogManager.getLogger(DownloadController.class);

    public DownloadTask(String urlText, File file) throws MalformedURLException {
        this.url = new URL(urlText);
        this.file = file;
    }


    @Override
    protected Integer call() throws Exception {
        logger.trace("Descarga " + url.toString() + " iniciada");
        updateMessage("Conectando con el servidor . . .");

        URLConnection urlConnection = url.openConnection();

        double fileSize = urlConnection.getContentLength();

        BufferedInputStream in = new BufferedInputStream(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte dataBuffer[] = new byte[1024];
        int bytesRead;
        int totalRead = 0;
        double downloadProgress = 0;

        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {

            // Guardamos en el archivo los bytes leidos y actualizamos.
            fileOutputStream.write(dataBuffer, 0, bytesRead);
            totalRead += bytesRead;


            downloadProgress = Math.round(((double) totalRead / fileSize) * 100) / 100d;
            double totalMb = totalRead / 1048576;
            jsonArray.put(1,totalMb);
            jsonArray.put(2,downloadProgress * 100 + " %");
            updateProgress(downloadProgress, 1);
            updateMessage(String.valueOf(jsonArray));

            if (isCancelled()) {
                logger.trace("Descarga cancelada");
                updateProgress(0, 0);
                jsonArray.put(2, "Cancelada");
                return null;
            }
        }

        updateProgress(1, 1);
        updateMessage("100 %");

        logger.trace("Descarga " + url.toString() + " finalizada");

        return null;
    }
}
