package com.karonda;

public class Main {

    public static void main(String[] args) {
        String originUrlpath = "https://youku.cdn2-youku.com/20180710/12991_efbabf56/1000k/hls/index.m3u8";
        String preUrlPath = originUrlpath.substring(0, originUrlpath.lastIndexOf("/")+1);
        String rootPath = "E:\\videodir";
        String fileName = "";
        HlsDownloader downLoader = new HlsDownloader(originUrlpath, preUrlPath, rootPath);
        //downLoader.setThreadQuantity(10);
        try{
            fileName = downLoader.download(true);
        }
        catch (Exception e) {

        }

        if(fileName.isEmpty()){
            System.out.println("下载失败");
        }else{
            System.err.println("下载成功");
        }
    }
}
