package com.scm.servicesimpl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scm.services.imageService;

@Service
public class ImageServiceimple implements imageService{

    @Override
    public String uploadImage(MultipartFile contactImage) {

        try {
            byte[] data=new byte[contactImage.getInputStream().available()];

            contactImage.getInputStream().read(data);
            return contactImage.getOriginalFilename();
            
        } catch (IOException e) {
          
            e.printStackTrace();
            return null;
        }

       
    }

}
