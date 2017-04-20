package app.util;

import app.model.BroadCastEvent;
import app.model.Event;
import app.model.User;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jaydatta on 4/16/17.
 */
public class PushNotificationHelper {


//    public final static String AUTH_KEY_FCM = "AAAACauqcpo:APA91bECyglxihkr6ctR8BqNPhI1ZHLAeYc_1Q4_RB1sJy3mrEatU34Gccmvzhmpbj2nqfDF0U1Gm-48H2onO3B6Py8EXI2cVNAJXag_efLlZ3NmAZpf3y66Wtgh-lNhv_ac7kPxThhs";
public final static String AUTH_KEY_FCM = "AAAAj3wuWXY:APA91bHxxstkm7NQO8GhgaJOc0swxpnHITFuik74wEpof9fsd3DqT_pCjGBM66spHzh-iKdul-FFwYGPt1ibrpxE8-2tAJKUIbeuvtB3BcrEfL8QmSeOxy4gpAt7fBWE-zki7j5BNcyT";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public static String sendPushNotification(String deviceToken, User sender, Event event, BroadCastEvent broadCastMessageFromSender)
            throws IOException {
        String result = "";
        URL url = new URL(API_URL_FCM);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();

        json.put("to", deviceToken.trim());
        JSONObject info = new JSONObject();
        info.put("title",sender.getName()+" invites you!"+event.getName() ); // Notification title
        info.put("body", "Tap to respond"); // Notification
      //  info.put("data","check-payload");
        // body

        JSONObject eventInformation = getEventJSON(sender.getName()+" invites you!"+event.getName(),"Tap to respond",event,sender,broadCastMessageFromSender);
        JSONObject wrapperObject = new JSONObject();
        wrapperObject.put("EventObject",eventInformation);
        json.put("data",eventInformation);
       // json.put("notification", info);
        json.put("click_action","OPEN_RECIEVE_ACTIVITY");
        try {
            OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            result = "FAILURE";
        }
        System.out.println("GCM Notification is sent successfully");

        return result;
    }

    public static String sendToBroadcaster(String deviceToken,User responder,Event event)
            throws IOException {
        String result = "";
        URL url = new URL(API_URL_FCM);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();

        json.put("to", deviceToken.trim());
        JSONObject info = new JSONObject();
        info.put("title", responder.getName()+" is joining you"); // Notification title
        info.put("body", event.getName()); // Notification
        //  info.put("data","check-payload");
        // body

       // JSONObject eventInformation = getEventJSON(event,sender);
        //JSONObject wrapperObject = new JSONObject();
       // wrapperObject.put("EventObject",eventInformation);
   //     json.put("data","dummyData");
        json.put("notification", info);
        try {
            OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            result = "FAILURE";
        }
        System.out.println("GCM Notification is sent successfully");

        return result;
    }

    public static String sendToReciever(String deviceToken,User sender,Event event)
            throws IOException {
        String result = "";
        URL url = new URL(API_URL_FCM);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();

        json.put("to", deviceToken.trim());
        JSONObject info = new JSONObject();
        info.put("title", "It's Confirmed"); // Notification title
        info.put("body", "Joining "+sender.getName()); // Notification
        //  info.put("data","check-payload");
        // body

        JSONObject eventInformation = getEventJSON(event,sender);
        JSONObject wrapperObject = new JSONObject();
        wrapperObject.put("EventObject",eventInformation);
        json.put("data",eventInformation);
        json.put("notification", info);
        try {
            OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            result = "FAILURE";
        }
        System.out.println("GCM Notification is sent successfully");

        return result;
    }



    public static JSONObject getEventJSON(String notificationBody,String notificationTitle,Event event,User sender,BroadCastEvent broadcastedMessageFromSender){

        JSONObject obj = new JSONObject();


        obj.put("id",event.getId());
        obj.put("name",event.getName());
        obj.put("imageURL",event.getImageURL());
        obj.put("eventURL",event.getEventURL());
        obj.put("startDate",event.getStartDate());
        obj.put("startTime",event.getStartTime());
        obj.put("venue",event.getVenue());
        obj.put("sender",sender.getName());
        obj.put("senderPhone",sender.getContactNumber());
        obj.put("senderID",sender.getId());
        obj.put("senderMailID",sender.getEmail());
        obj.put("broadCastTime",broadcastedMessageFromSender.getCurrentTime().getTime()+"");
        obj.put("notificationBody",notificationBody);
        obj.put("notificationTitle",notificationTitle);
        obj.put("personalMessage",broadcastedMessageFromSender.getPersonalMessage());
        return obj;
    }

    public static JSONObject getEventJSON(Event event,User sender){

        JSONObject obj = new JSONObject();


        obj.put("id",event.getId());
        obj.put("name",event.getName());
        obj.put("imageURL",event.getImageURL());
        obj.put("eventURL",event.getEventURL());
        obj.put("startDate",event.getStartDate());
        obj.put("startTime",event.getStartTime());
        obj.put("venue",event.getVenue());
        obj.put("sender",sender.getName());
        obj.put("senderPhone",sender.getContactNumber());
        obj.put("senderID",sender.getId());
        obj.put("senderMailID",sender.getEmail());

        return obj;
    }

}
