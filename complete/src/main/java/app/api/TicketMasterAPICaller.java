package app.api;

import app.helper.Event;
import app.util.UserPersonalization;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by jaydatta on 4/15/17.
 * <p>
 * Jaydatta Nagarkar is responsible for implementation of the code.
 * Original Idea of fetching events using ticketmaster API inspired from this link: https://github.com/PawanMahalle/TicketmasterAPIDemo
 * <p>
 * <p>
 * /**
 * Created by jaydatta on 4/15/17.
 */
public class TicketMasterAPICaller {
    static final Logger logger = LogManager.getLogger(TicketMasterAPICaller.class.getName());
    private static final String API_KEY = "SX2DFwa5BMC2nQX1RwGq9idXI4HCBrgZ";
    private static final String EVENT_API_CALL_PREFIX = new StringBuilder(
            "https://app.ticketmaster.com/discovery/v1/events.json?apikey=").append(API_KEY).toString();
    private static final String EVENT_DETAILS_API_CALL_PREFIX = "https://app.ticketmaster.com/discovery/v1/events/";
    private static final String EVENT_DETAILS_API_CALL_SUFFIX = new StringBuilder(".json?apikey=").append(API_KEY)
            .toString();

    /**
     * returns events based on input key-value pairs by calling event search
     * API.
     *
     * @param apiInputMap
     * @return
     */
    public static List<Event> getEvents(Map<String, String> apiInputMap, ArrayList<UserPersonalization> personalizationArrayList) {

        List<Event> events = new ArrayList<>();
        // create API call by appending key-values in API request format

        HashSet<Event> eventSet = new HashSet<>();

        if (!personalizationArrayList.isEmpty()) {
            for (UserPersonalization currentItem : personalizationArrayList) {
                if (currentItem.getCategory() != null) {
                    StringBuilder APICall = new StringBuilder(EVENT_API_CALL_PREFIX);
                    logger.info("Sending category:" + currentItem.getCategory());
                    List<Event> currentEventList = new ArrayList<>();

                    for (String key : apiInputMap.keySet()) {
                        if (!"".equals(apiInputMap.get(key))) {
                            APICall.append("&").append(key).append("=").append(apiInputMap.get(key).replace(" ", "%20"));
                        }
                    }
                    APICall.append("&keyword=").append(currentItem.getCategory());
                    logger.info("API Call:" + APICall.toString());
                    try {
                        currentEventList = extractEvents(getRespose(APICall.toString()));
                    } catch (Exception e) {
                        // generate empty response
                        //           events = new ArrayList<>();
                        // TODO: log events
                        //   e.printStackTrace();
                    } finally {
                        for (Event currentEvent : currentEventList) {
                            if (currentEvent != null && !eventSet.contains(currentEvent)) {
                                events.add(currentEvent);
                                eventSet.add(currentEvent);
                            }
                        }
                    }
                }
            }
        }


        StringBuilder APICall = new StringBuilder(EVENT_API_CALL_PREFIX);
        List<Event> currentEventList = new ArrayList<>();
        for (String key : apiInputMap.keySet()) {
            if (!"".equals(apiInputMap.get(key))) {
                APICall.append("&").append(key).append("=").append(apiInputMap.get(key).replace(" ", "%20"));
            }
        }
        logger.info("API Call:" + APICall.toString());
        try {
            currentEventList = extractEvents(getRespose(APICall.toString()));
        } catch (Exception e) {
            // TODO: log events
            //   e.printStackTrace();
        } finally {
            for (int i = 0; i < Math.min(currentEventList.size() - 1, 5); i++) {
                Event currentEvent = currentEventList.get(i);
                if (currentEvent != null && !eventSet.contains(currentEvent)) {
                    events.add(currentEvent);
                    eventSet.add(currentEvent);
                }
            }
        }

        return events;
    }

    private static JSONObject getRespose(String APICall) throws IOException, JSONException {

        JSONObject jsonObject = null;
        URL url = new URL(APICall);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        } else {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            jsonObject = new JSONObject(handleDuplicateKey(responseStrBuilder.toString()));

        }

        return jsonObject;
    }

    private static List<Event> extractEvents(JSONObject jsonObject) throws JSONException {

        List<Event> events = new ArrayList<>();

        try {
            JSONObject embeddedObj = jsonObject.getJSONObject("_embedded");
            JSONArray eventsArray = embeddedObj.getJSONArray("events");

            for (int i = 0; i < eventsArray.length(); i++) {

                Event event = null;
                try {
                    event = TicketMasterAPICaller.getEventDetails(eventsArray.getJSONObject(i).getString("id"));
                    if (null != event) {
                        events.add(event);
                    }
                } catch (IOException e) {
                    // TODO Log event
                    //     e.printStackTrace();
                }
            }

        } catch (Exception e) {
            //    e.printStackTrace();
        }

        return events;
    }

    public static Event getEventDetails(final String EVENT_ID) throws IOException, JSONException {

        Event event = null;

        StringBuilder APICall = new StringBuilder(
                EVENT_DETAILS_API_CALL_PREFIX + EVENT_ID + EVENT_DETAILS_API_CALL_SUFFIX);

        event = extractEventDetails(getRespose(APICall.toString()));
        return event;
    }

    /**
     * reforms JSON when duplicate key is detected.
     *
     * @param string
     * @return
     */
    private static String handleDuplicateKey(String string) {
        // TODO: Merge duplicate key values into JSONArray if duplicate keys are
        // present.

        //TODO: This is WORKAROUND :(
        return string.replaceFirst("_links", "_links_duplicate");
    }

    private static Event extractEventDetails(JSONObject eventDetailsJSONObj) throws JSONException {
        Event event = new Event(eventDetailsJSONObj.getString("id"));

        try {
            event.setName(eventDetailsJSONObj.getString("name"));
            try {
                event.setEventURL(eventDetailsJSONObj.getString("eventUrl"));
            } catch (JSONException ex) {
                // TODO: Log NOo event URL present
            }

            try {
                JSONArray venues = eventDetailsJSONObj.getJSONObject("_embedded").getJSONArray("venue");
                StringBuilder address = new StringBuilder();

                for (int i = 0; i < venues.length(); i++) {
                    if (i != 0) {
                        address.append("<BR>AND<BR>");
                    }
                    address.append(venues.getJSONObject(i).get("name")).append(", ");
                    address.append(venues.getJSONObject(i).get("marketId")).append(",");
                    address.append(venues.getJSONObject(i).getJSONObject("city").get("name")).append(",");
                    address.append(venues.getJSONObject(i).getJSONObject("country").get("countryCode"));

                }
                event.setVenue(address.toString());

            } catch (JSONException ex) {
                // TODO: Log NOo event URL present
            }


            try {
                JSONArray categories = eventDetailsJSONObj.getJSONObject("_embedded").getJSONArray("categories");
                for (int i = 0; i < categories.length(); i++) {
                    event.setCategory((String) categories.getJSONObject(i).get("name"));
                    logger.info((String) categories.getJSONObject(i).get("name") + " is the event category");
                }


            } catch (Exception e) {

            }
            try {
                JSONObject startTiming = eventDetailsJSONObj.getJSONObject("dates").getJSONObject("start");

                event.setStartDate(startTiming.get("localDate").toString());
                event.setStartTime(startTiming.get("localTime").toString());
            } catch (JSONException ex) {
                // TODO: Log NO date info
            }
        } catch (Exception e) {
            // TODO: Add event logging
        }
        return event;
    }

}
