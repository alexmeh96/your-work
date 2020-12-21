package com.coder.yourwork.service;

import com.coder.yourwork.model.Image;
import com.coder.yourwork.repo.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    public Image loadImage(MultipartFile file) throws IOException {
        Byte[] bytes = new Byte[file.getBytes().length];
        int i = 0;
        for (byte b: file.getBytes())
            bytes[i++] = b;
        return new Image(bytes);
    }
}
