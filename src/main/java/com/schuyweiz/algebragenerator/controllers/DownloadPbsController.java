package com.schuyweiz.algebragenerator.controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class DownloadPbsController {

    @GetMapping(value = "/download",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> demo() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "/usr/bin/pdflatex",
                "-output-directory=src/main/resources/files/",
                "-interaction=nonstopmode",
                "src/main/resources/files/demo.tex"
        );

        pb.redirectErrorStream(true);
        Process p  = pb.start();
        InputStream is = p.getInputStream();
        int in=-1;

        while((in=is.read())!=-1){
            System.out.print((char)in);
        }

        int exitWith = p.waitFor();
        System.out.println("\nExited with " + exitWith);


        var content = Files.readAllBytes(Path.of("src/main/resources/files/demo.pdf"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE); // (3) Content-Type: application/octet-stream
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename("mytext.pdf").build().toString()); // (4) Content-Disposition: attachment; filename="demo-file.txt"
        return ResponseEntity.ok().headers(httpHeaders).body(content);
    }

}
