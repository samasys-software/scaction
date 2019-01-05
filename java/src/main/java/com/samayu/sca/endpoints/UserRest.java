package com.samayu.sca.endpoints;

import com.samayu.sca.businessobjects.CastingCallApplication;
import com.samayu.sca.businessobjects.PortfolioPicture;
import com.samayu.sca.businessobjects.User;
import com.samayu.sca.businessobjects.UserNotification;
import com.samayu.sca.service.DataAccessService;
import com.samayu.sca.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(path="/user/")
public class UserRest {

    @Autowired
    DataAccessService dataAccessService;

    @Autowired
    StorageService storageService;

    @Value("${portfolio.basefolder}")
    String baseFolder;


    @RequestMapping(path="/register",method = RequestMethod.POST)
    public ResponseEntity<User> create(
            @RequestParam("fbUser") String fbUser,
            @RequestParam("screenName") String screenName,
            @RequestParam("fbName") String name,
            @RequestParam("fbEmail") String fbEmail,
            @RequestParam("countryCode") String countryCode,
            @RequestParam("cityId") String city,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("whatsappNumber") String whatsappNumber,
            @RequestParam("gender") String gender,
            @RequestParam("dateOfBirth") String dateOfBirth,
            @RequestParam("searchable") String searchable,
            @RequestParam("profilePic") String profilePic,
            @RequestParam("roles") int[] roles
                                       ){

         User user = dataAccessService.register( fbUser, screenName , name , fbEmail ,
                 countryCode , city , phoneNumber , whatsappNumber, Integer.parseInt(gender) ,
                 LocalDate.parse(dateOfBirth) , Boolean.parseBoolean(searchable) , profilePic,roles );
        return ResponseEntity.ok(user);
    }

    @GetMapping(path="/checkUser/{fbUser}" )
    public ResponseEntity<User> checkUser(@PathVariable("fbUser") String fbUser ){
            User user = dataAccessService.findUser(fbUser);
            if( user != null ) {
                return ResponseEntity.ok(user);
            }
            else{
                return ResponseEntity.notFound().build();
            }
    }


    @GetMapping(path="/notifications/{userId}")
    public ResponseEntity<Iterable<UserNotification>> getNotificationsForUser(@PathVariable("userId") long userId ){
        return ResponseEntity.ok( dataAccessService.findNotificationsByUser( userId ));
    }

    @PostMapping(path="/applyForCastingCall")
    public ResponseEntity<Boolean> applyForCastingCall(@RequestParam("castingCallId") long castingCallId , @RequestParam("userId") long userId , @RequestParam("roleId") int roleId )
    {
        CastingCallApplication application = new CastingCallApplication();
        application.setCastingCallId( castingCallId );
        application.setUser( dataAccessService.findUser( userId ));
        application.setRoleId( roleId );
        return ResponseEntity.ok(dataAccessService.createCastingCallApplication( application ));

    }

    @PostMapping(path="/uploadPicture")
    public ResponseEntity<List<PortfolioPicture>> uploadPicture(@RequestParam("userId") long userId , @RequestParam("pictureType") int pictureType , @RequestParam("file") MultipartFile file) throws IOException
    {

        String filename = UUID.randomUUID().toString();
        InputStream inputStream = file.getInputStream();

        dataAccessService.savePortfolioPicture( userId , "" , filename , pictureType );

        storageService.store( userId , inputStream , filename , baseFolder );

        return ResponseEntity.ok(dataAccessService.getPortfolioPicsForUser( userId ));


    }

    @PostMapping(path="/deletePicture")
    public ResponseEntity<List<PortfolioPicture>> deletePicture(@RequestParam("userId") long userId , @RequestParam("portfolioId") long portfolioId )
    {
        dataAccessService.deletePortfolioPicture( userId , portfolioId );
        return ResponseEntity.ok(dataAccessService.getPortfolioPicsForUser( userId ));
    }

    @GetMapping(path="/downloadFile/{userId}/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename , @PathVariable("userId") long userId, HttpServletRequest httpRequest) throws MalformedURLException,IOException
    {
        Resource resource = storageService.getFile(baseFolder , userId , filename );
        String contentType = httpRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
