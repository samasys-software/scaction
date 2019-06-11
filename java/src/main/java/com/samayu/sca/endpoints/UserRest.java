package com.samayu.sca.endpoints;

import com.samayu.sca.businessobjects.*;
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
import sun.misc.Request;

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
            @RequestParam("roles") int[] roles,
            @RequestParam(name = "loginType" , defaultValue = "0") int loginType
                                       ){

         User user = dataAccessService.register( fbUser, screenName , name , fbEmail ,
                 countryCode , city , phoneNumber , whatsappNumber, Integer.parseInt(gender) ,
                 LocalDate.parse(dateOfBirth) , Boolean.parseBoolean(searchable) , profilePic,roles , loginType );
        return ResponseEntity.ok(user);
    }

    @GetMapping(path="/checkUser/{fbUser}/{loginType}" )
    public ResponseEntity<User> checkUser(@PathVariable("fbUser") String fbUser , @PathVariable("loginType" ) Integer loginType ){
        User user = dataAccessService.findUser(fbUser , loginType );
            if( user != null ) {
                return ResponseEntity.ok(user);
            }
            else{
                return ResponseEntity.notFound().build();
            }
    }

    @GetMapping(path="/findUser/{userId}" )
    public ResponseEntity<User> findUser(@PathVariable("userId") long userId ){
        User user = dataAccessService.findUser( userId );
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
        CastingCall call = new CastingCall();
        call.setId( castingCallId );
        application.setCastingCall( call );
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

    @GetMapping(path="/getAllPortfolio/{userId}" )
    public ResponseEntity<List<PortfolioPicture>> findAllPortfolio(@PathVariable("userId") long userId )
    {
        return ResponseEntity.ok(dataAccessService.getPortfolioPicsForUser( userId ));
    }

    @PostMapping(path="/updatePortfolio/{userId}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable("userId") long userId,  @RequestParam("shortDesc") String shortDesc, @RequestParam("areaOfExpertise") String areaOfExpertise, @RequestParam("projectWorked") String projectWorked, @RequestParam("message") String message ){

        Portfolio portfolio = new Portfolio();
        User user = new User();
        user.setUserId( userId );
        user.setPortfolio( portfolio );
        portfolio.setUserId( userId );
        portfolio.setMessage( message );
        portfolio.setAreaOfExpertise( areaOfExpertise );
        portfolio.setProjectsWorked( projectWorked );
        portfolio.setShortDescription( shortDesc );
        dataAccessService.updatePortfolio( portfolio );
        return ResponseEntity.ok( portfolio );
    }

    @GetMapping(path="/getPortfolio/{userId}")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable("userId") long userId ){
        return ResponseEntity.ok( dataAccessService.getPortfolio( userId ) );
    }

    @GetMapping(path="/getMyCastingCalls/{userId}")
    public ResponseEntity<List<CastingCall>> getMyCastingCalls(@PathVariable("userId") long userId ){

            return ResponseEntity.ok(dataAccessService.getMyCastingCalls( userId ));
    }





}
