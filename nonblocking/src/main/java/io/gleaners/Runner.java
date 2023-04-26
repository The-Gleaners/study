package io.gleaners;

public class Runner
{
    public static void main(String[] args)
    {
        ModernDownloader downloader = new ModernDownloader();
        long startTime = System.currentTimeMillis();
        downloader.download("https://dgdeu18iqgapt.cloudfront.net/temp_800MB_file");
        long endTime = System.currentTimeMillis();

        System.out.println("논블록킹 I/O 1GB 파일 다운로드 시간 : " + (endTime - startTime) + " milli seconds");
    }
}
