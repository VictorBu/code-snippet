package com.karonda.miniodemo.model;

import org.springframework.web.multipart.MultipartFile;

public class UploadModel {
    private String bucket;
    private String fileName;
    private MultipartFile file;

    public String getBucket() {
        return bucket;
    }
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public MultipartFile getFile() {
        return file;
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
