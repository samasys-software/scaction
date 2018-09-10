package com.samayu.sca.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samayu.sca.Application;
import com.samayu.sca.businessobjects.CastingCall;
import com.samayu.sca.businessobjects.Notification;
import com.samayu.sca.businessobjects.User;
import com.samayu.sca.businessobjects.UserNotification;
import com.samayu.sca.dao.CastingCallRepository;
import com.samayu.sca.dao.UserNotificationRepository;
import com.samayu.sca.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
@Service
public class Listener {

    public static final String CASTING_CALL_NOTIFICATION_TOPIC = "castingcallnotification";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataAccessService dataAccessService;

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Qualifier("topicJmsTemplate")
    @Autowired
    JmsTemplate jmsTopicTemplate;

    @JmsListener(destination = Application.CASTING_CALL_MESSAGE_QUEUE)
    public void receiveMessageFromQueue(String jsonMessage) throws JMSException, JsonProcessingException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.S").create();
        CastingCall castingCall = gson.fromJson(jsonMessage, CastingCall.class);
        List<User> users = userRepository.findByCityId(castingCall.getCityId());
        ObjectMapper objectMapper = new ObjectMapper();
        for (User user : users){
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setReference(castingCall);
            notification.setNotificationType(1);
            System.out.println("Sending notification to topic for user: " + user.getFbUser());
            sendNotificationToTopic(notification, objectMapper);
        }
    }

    public void sendNotificationToTopic(Notification notification, ObjectMapper objectMapper) throws JsonProcessingException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        jmsTopicTemplate.convertAndSend(CASTING_CALL_NOTIFICATION_TOPIC, objectMapper.setDateFormat(dateFormat).writeValueAsString(notification));
    }

    @JmsListener(destination = CASTING_CALL_NOTIFICATION_TOPIC, containerFactory = "topicListenerFactory")
    public void receiveMessageFromTopic(String jsonMessage){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.S").create();
        System.out.println(jsonMessage);
        Notification notification = gson.fromJson(jsonMessage, Notification.class);
        UserNotification userNotification = new UserNotification();
        userNotification.setUserId(notification.getUser().getUserId());
        userNotification.setNotificationType(notification.getNotificationType());
        if(notification.getNotificationType() == 1){
            userNotification.setMessage("casting call message");
        }
        userNotification.setRedirectLink("");
        userNotification.setReferenceKey(5L);
        userNotification.setCreateDt(new Timestamp( System.currentTimeMillis() ));
        System.out.println("Saving to Database");
        userNotificationRepository.save(userNotification);
        System.out.println("Saved to Database");
    }
}
