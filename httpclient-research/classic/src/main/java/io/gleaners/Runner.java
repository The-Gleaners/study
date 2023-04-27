package io.gleaners;

public class Runner
{
    public static void main(String[] args)
    {
        ClassicalDownloader classicalDownloader = new ClassicalDownloader();
        long startTime = System.currentTimeMillis();
        classicalDownloader.download("https://dgdeu18iqgapt.cloudfront.net/temp_800MB_file");
        long endTime = System.currentTimeMillis();

        System.out.println("블록킹 I/O 1GB 파일 다운로드 시간 : " + (endTime - startTime) + " milli seconds");
    }
}
