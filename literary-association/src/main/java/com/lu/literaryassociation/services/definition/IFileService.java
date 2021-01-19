package com.lu.literaryassociation.services.definition;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

public interface IFileService {

    public void upload(MultipartFile file) throws Exception;

    public List<String> filesStoreAndMap(MultipartFile[] files) throws Exception;

    public Resource loadFile(String fileName) throws  Exception;

    public ResponseEntity download(String fileName) throws MalformedURLException;

}
