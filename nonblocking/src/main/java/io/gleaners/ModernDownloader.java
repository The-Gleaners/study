package io.gleaners;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ModernDownloader
{
    private final static int MB_100 = 100 * 1024 * 1024;

    public void download(String requestUrl)
    {

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(requestUrl))
            .GET()
            .build();

        try
        {
            HttpResponse<InputStream> response = getResponse(request);
            write(response.body(), "file");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse<InputStream> getResponse(HttpRequest request)
    {
        try
        {
            return HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofInputStream());
        }
        catch (IOException | InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void write(InputStream reader, String destinationFIle) throws IOException
    {

        byte[] buffer = new byte[MB_100];

        int bytesRead = 0;

        try(FileOutputStream writer = new FileOutputStream(destinationFIle))
        {
            while ((bytesRead = reader.read(buffer, 0, buffer.length)) != -1)
            {
                writer.write(buffer, 0, bytesRead);
            }
        }
    }
}
