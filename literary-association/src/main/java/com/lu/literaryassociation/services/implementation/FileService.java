package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.entity.Genre;
import com.lu.literaryassociation.services.definition.IFileService;
import com.lu.literaryassociation.util.exceptions.GeneralException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService implements IFileService {

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;


    @Override
    public void upload(MultipartFile file) throws Exception {
        try {
            System.out.println("USLA u servis");
            System.out.println("uploadDir"+uploadDir);
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!", HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public String filesStoreAndMap(MultipartFile[] files) throws  Exception
    {
        List<String> listForProcesVariable = new ArrayList<>();
        Arrays.asList(files)
                .stream()
                .forEach(file -> {
                    try {
                        this.upload(file);
                        listForProcesVariable.add(file.getOriginalFilename());
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new GeneralException("Could not store file " + file.getOriginalFilename()
                                + ". Please try again!", HttpStatus.BAD_REQUEST);
                    }
                });
        return this.filesToString(listForProcesVariable);
    }


    public String filesToString(List<String>files){
        String returnString = "";
        for (String f: files) {
            //genres=romance;horror
            returnString = returnString.concat(f.concat(";"));
        }
        return returnString;
    }


    @Override
    public Resource loadFile(String fileName) throws Exception {
        try {
            Path filePath = Paths.get(uploadDir, fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new GeneralException("Could not load file", HttpStatus.BAD_REQUEST);
            }
        } catch (MalformedURLException ex) {
            throw new GeneralException("Could not load file", HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public ResponseEntity download(String fileN) throws MalformedURLException {
        System.out.println("USLA U DOWNLOAD" + fileN);
        String fileName = fileN+ ".pdf";

        Path filepath = Paths.get("src\\main\\resources\\files", fileName);
        File file = new File(filepath.toAbsolutePath().toString());

        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(filepath.toAbsolutePath().toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(file.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename="+file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
        return responseEntity;
    }

}
