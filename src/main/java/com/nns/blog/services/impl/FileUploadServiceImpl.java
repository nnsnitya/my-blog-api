package com.nns.blog.services.impl;

import com.nns.blog.services.FileUploadService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final MinioClient minioClient;
    @Value("${minio.bucket-name}")
    String bucketName;

    @Override
    public String uploadImage(MultipartFile file) throws
            InsufficientDataException, NoSuchAlgorithmException, InvalidKeyException,
            IOException, ServerException, ErrorResponseException, InvalidResponseException, XmlParserException, InternalException {
        validate(file);

        String uuid = UUID.randomUUID().toString();
        String extention = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String objectKey = "post"+ File.separator + uuid + extention;

        //uploading code
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectKey)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                .build()
        );
        System.out.println(objectWriteResponse);

        return objectKey;
    }

    private void validate(MultipartFile file) {
        //validate logic
    }
}
