package com.samayu.sca;

import com.samayu.sca.businessobjects.User;
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

import java.util.HashMap;
import java.util.Map;

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
        map.add("fb_user","samayu");
        map.add("fb_Email","info@samayusoftcorp.com");
        map.add("profile_pic","profile");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<User> userResponseEntity = template.postForEntity("/user/register",request,User.class);
        Assert.assertEquals(200,userResponseEntity.getStatusCodeValue());
    }




}
