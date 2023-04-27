package io.gleaners;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

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
            CompletableFuture<InputStream> response = getResponse(request);
            write(response.join(), "file");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private CompletableFuture<InputStream> getResponse(HttpRequest request)
    {
        return HttpClient
            .newHttpClient()
            .sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
            .thenApply(HttpResponse::body);
    }

    private void write(InputStream reader, String destinationFIle) throws IOException
    {

        byte[] buffer = new byte[MB_100];

        int bytesRead = 0;

        try(FileOutputStream writer = new FileOutputStream(destinationFIle))
        {
            while ((bytesRead = reader.read()) != -1)
            {
                writer.write(bytesRead);
            }
        }
    }
}
