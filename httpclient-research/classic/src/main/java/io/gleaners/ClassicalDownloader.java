package io.gleaners;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClassicalDownloader
{
    private static final int NUMBER_OF_THREAD = 10;
    private static final int MB_100 = 100 * 1024 * 1024;
    private final ExecutorService executorService;

    public ClassicalDownloader()
    {
        this.executorService = Executors.newFixedThreadPool(NUMBER_OF_THREAD);
    }

    public void download(String requestUrl)
    {

        HttpURLConnection conn = conn(requestUrl);

        try
        {
            InputStream inputStream = conn.getInputStream();

            final byte[] buffer = new byte[MB_100];

            downloadTask(inputStream, buffer);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void downloadTask(InputStream inputStream, byte[] buffer) throws IOException
    {

        try (FileOutputStream out = new FileOutputStream("file", true))
        {
            int bytesRead = 0;

            while((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                final byte[] chunkedData = Arrays.copyOf(buffer, bytesRead);
                executorService.execute(new DownloadTask(chunkedData, out));
            }
        }

        executorService.shutdown();
    }

    private HttpURLConnection conn(String requestUrl)
    {
        try
        {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            return conn;

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
