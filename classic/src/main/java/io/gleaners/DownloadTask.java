package io.gleaners;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadTask implements Runnable
{
    private final static int MB_50 = 50 * 1024 * 1024;
    private final byte[] chunk;
    private final FileOutputStream writer;

    public DownloadTask(byte[] chunk, FileOutputStream writer)
    {
        this.chunk = chunk;
        this.writer = writer;
    }

    @Override
    public void run()
    {
        try (ByteArrayInputStream byteArrReader = new ByteArrayInputStream(chunk);
             BufferedInputStream reader = new BufferedInputStream(byteArrReader, chunk.length))
        {
            byte[] smallBuffer = new byte[MB_50];

            int smallBytesRead = 0;

            while ((smallBytesRead = reader.read(smallBuffer, 0, smallBuffer.length)) != -1)
            {
                writer.write(smallBuffer, 0, smallBytesRead);
            }

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
