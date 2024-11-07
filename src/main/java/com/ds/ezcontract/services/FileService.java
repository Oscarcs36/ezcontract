package com.ds.ezcontract.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadImage(String path, MultipartFile image)  throws IOException;
}
