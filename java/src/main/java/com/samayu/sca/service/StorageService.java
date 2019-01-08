package com.samayu.sca.service;

import org.apache.poi.util.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Paths;

@Component
public class StorageService {

    public void store(long userId, InputStream inputStream,String uuid, String baseFolder) throws IOException
    {
        File folder = new File(baseFolder+"/"+userId );
        if( !folder.exists() ){
            folder.mkdirs();
        }

        File file = new File( folder , uuid );

        FileOutputStream fos = new FileOutputStream( file );

        IOUtils.copy( inputStream , fos );


    }

    public Resource getFile(String baseFolder , long userId , String filename ) throws MalformedURLException{
        return new UrlResource( Paths.get( baseFolder+"/"+userId+"/"+filename ).toUri() );
    }
}
