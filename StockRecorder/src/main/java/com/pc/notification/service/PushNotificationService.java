package com.pc.notification.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pc.model.notification.PushNotificationRequest;

@Service
public class PushNotificationService {

    @Value("#{${app.notifications.defaults}}")
    private Map<String, String> defaults;

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    
    @Autowired
    private FCMService fcmService;

//   @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    public void sendSamplePushNotification() {
        try {
            fcmService.sendMessageToToken(getSamplePushNotificationRequest());
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotification(PushNotificationRequest request) {
        try {
            fcmService.sendMessage(getSamplePayloadData(), request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotificationWithoutData(PushNotificationRequest request) {
        try {
            fcmService.sendMessageWithoutData(request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }


    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }


    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("messageId", defaults.get("payloadMessageId"));
        pushData.put("text", defaults.get("payloadData") + " " + LocalDateTime.now());
        return pushData;
    }


    private PushNotificationRequest getSamplePushNotificationRequest() {
        PushNotificationRequest request = new PushNotificationRequest(defaults.get("title"),
                defaults.get("message"),
                defaults.get("topic"));
        request.setToken("cu-9boft6ik:APA91bFIx8f4evF3IzRDDghzhSwmBSa68AFcc_Lgrjxe_k6rUGSG8OHI7nkXpvpcfJDKbbj1RfSfCgIT0BxNPGdoOhCUG_jI2rZQG_hlGbGc71fCAuHFOFl7UCVXus4uwvD1HgVOgKte");
        return request;
    }

}
