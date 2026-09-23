package com.nns.blog.services;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface FileUploadService {

    String uploadImage(MultipartFile file) throws InsufficientDataException,
            NoSuchAlgorithmException, IOException, InvalidKeyException, ServerException,
            ErrorResponseException, InvalidResponseException, XmlParserException,
            InternalException;

}
