package com.karonda.miniodemo;

import com.karonda.miniodemo.model.DownloadModel;
import com.karonda.miniodemo.model.UploadModel;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
public class MinioController {

    @Autowired
    private MinioProperties minioProperties;

    @PostMapping("/upload")
    public String upload(UploadModel uploadModel) {

        if(StringUtils.isEmpty(uploadModel.getFileName())) {
            uploadModel.setFileName(uploadModel.getFile().getOriginalFilename());
        }

        try {
            // 使用 MinIO 服务的 IP:PORT，Access key 和 Secret key 创建一个 MinioClient 对象
            MinioClient minioClient = new MinioClient(
                    minioProperties.getUrl(), minioProperties.getAccessKey(), minioProperties.getSecretKey());

            // 检查存储桶是否已经存在
            boolean isBucketExist = minioClient.bucketExists(uploadModel.getBucket());
            if(!isBucketExist) {
                minioClient.makeBucket(uploadModel.getBucket());
            }

            PutObjectOptions putObjectOptions = new PutObjectOptions(uploadModel.getFile().getSize()
                    , uploadModel.getFile().getSize() >= 5242880L ? uploadModel.getFile().getSize() : 0L);

            minioClient.putObject(uploadModel.getBucket(), uploadModel.getFileName(),
                    uploadModel.getFile().getInputStream(), putObjectOptions);

        } catch (Exception e) {
            return e.getMessage();
        }
        return "success";
    }

    @RequestMapping("/download")
    public ResponseEntity<Resource> download(DownloadModel downloadModel) {
        try {
            MinioClient minioClient = new MinioClient(
                    minioProperties.getUrl(), minioProperties.getAccessKey(), minioProperties.getSecretKey());

            InputStream inputStream = minioClient.getObject(downloadModel.getBucket(), downloadModel.getFileName());
            Resource resource = new InputStreamResource(inputStream);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment;filename=\"" +
                                    downloadModel.getFileName().replace('/', '-') + "\"")
                    .body(resource);
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping("/share")
    public String share(DownloadModel downloadModel) {
        try {
            MinioClient minioClient = new MinioClient(
                    minioProperties.getUrl(), minioProperties.getAccessKey(), minioProperties.getSecretKey());

            String url = minioClient.presignedGetObject(downloadModel.getBucket(), downloadModel.getFileName(), 120);
            return url;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
