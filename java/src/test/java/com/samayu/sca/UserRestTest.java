package com.samayu.sca;

import com.samayu.sca.businessobjects.User;
import com.samayu.sca.businessobjects.UserRole;
import com.samayu.sca.dao.UserRepository;
import org.hibernate.sql.Template;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.Mock;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestTest {

    @Autowired
    TestRestTemplate template;


    @Test
    public void testCreate(){
        Map<String,String> variables = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();

        /*
            @RequestMapping(path="/register",method = RequestMethod.POST)
    public ResponseEntity<User> create(
            @RequestParam("fbUser") String fbUser,
            @RequestParam("screenName") String screenName,
            @RequestParam("fbName") String name,
            @RequestParam("fbEmail") String fbEmail,
            @RequestParam("countryCode") String countryCode,
            @RequestParam("city") String city,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("whatsappNumber") String whatsappNumber,
            @RequestParam("gender") String gender,
            @RequestParam("dateOfBirth") String dateOfBirth,
            @RequestParam("searchable") String searchable,
            @RequestParam("profilePic") String profilePic,
            @RequestParam("roles") int[] roles

         */
        map.add("fbUser","samayu");
        map.add("screenName","Samayu Screenname");
        map.add("fbName","Samayu FBName");
        map.add("fbEmail","info@samayusoftcorp.com");
        map.add("countryCode","1");
        map.add("cityId","1");
        map.add("phoneNumber","9790472014");
        map.add("whatsappNumber","+16464316250");
        map.add("gender","0");
        map.add("dateOfBirth","1978-02-14");
        map.add("searchable","true");
        map.add("profilePic","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRqMM-Fsol2Q0CN-VAq9GwrIU8q6g7lulqJXYr4CBJBoEwK3n5GYA");
        int[] arr = { 0 ,1 };

        map.add("roles", "0");
        map.add("roles", "1");


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<User> userResponseEntity = template.postForEntity("/user/register",request,User.class);
        Assert.assertEquals(200,userResponseEntity.getStatusCodeValue());
        assert( userResponseEntity.getBody()!=null );
    }

    @Test
    public void testIfNotFound(){

        ResponseEntity<User> userResponse = template.getForEntity("/user/checkUser/"+ UUID.randomUUID().toString() , User.class );
        assert( userResponse.getStatusCodeValue() == 200 );
        assert( userResponse.getBody() == null );
    }

    @Test
    public void testIfFound(){

        ResponseEntity<User> userResponse = template.getForEntity("/user/checkUser/samayu", User.class );
        assert( userResponse.getStatusCodeValue() == 200 );
        assert( userResponse.getBody() != null );
        assert( userResponse.getBody().getFbUser().equals("samayu"));
    }


    @Test
    public void testRoleMapping(){

        ResponseEntity<User> userResponse = template.getForEntity("/user/checkUser/samayu4", User.class );

        List<UserRole> roles = userResponse.getBody().getUserRoles();

        assert( roles != null );



    }


}
