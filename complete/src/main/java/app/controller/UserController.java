package app.controller;
import static java.util.concurrent.TimeUnit.*;

import app.*;
import app.api.TicketMasterAPICaller;
import app.model.*;
import app.util.BroadCastUtil;
import app.util.PushNotificationHelper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(path="/crud/")
public class UserController {

    public static BuzzMapAccessor accessor = BuzzMapAccessor.getInstance();
	@Autowired // This means to get the bean called userRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;

	@Autowired
	private EventGroupRepository eventGroupRepository;



	static final Logger logger = LogManager.getLogger(UserController.class.getName());

	@RequestMapping("/users/create")
	@ResponseBody
	public String createUser(@RequestBody User user){
		User newUser = null;

		Iterable<User> abc = userRepository.findAll();

		long existingId=-1;
		boolean exists=false;
		for(User currentUser: abc){
			if(currentUser.getEmail().equals(user.getEmail())){
				exists=true;
				existingId=currentUser.getId();
				System.out.println("Id of user"+currentUser.getEmail()+"  is"+currentUser.getId());
				System.out.println("wont be added");
				break;
			}
		}

		if(!exists){
			try{
//			newUser= new User(user.getName(),user.getEmail(),user.getContactNumber(),user.getLatitude(),user.getLongitude(),user.getExpectedTime(),user.getRadius(),user.);
				user.setLatestUpdated(new Date());
				userRepository.save(user);

				return String.valueOf(user.getId());
			}
			catch(Exception e){
				logger.error(e.getMessage());
				return e.getMessage();
			}
		}

		return String.valueOf(existingId);
	}

	// GetUserInfo
	@RequestMapping(value = "/user/read/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(@PathVariable Long id){
		User possibleUser=null;
		System.out.println(userRepository.count()+"\n\n\n");

		try{
			System.out.println("\n\n\n\n\n\n Found one");
			possibleUser = userRepository.findOne(id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
		}

		if(possibleUser==null){
			String errorMessage = "No user found by particular userID"+id;
			logger.error(errorMessage);
			return null;
		}

		return possibleUser;
	}


	// Update user location values
	@RequestMapping(value = "/users/update/location",method = RequestMethod.POST)
	@ResponseBody
	public String updateLocation(@RequestBody HeartBeat heartBeat){

		String id = heartBeat.getId();
		User currentUser=null;
		Double newLatitude = heartBeat.getLatitude();
		Double newLongitude = heartBeat.getLongitude();
		try{
			currentUser = userRepository.findOne(Long.valueOf(id));
			currentUser.setLatitude(newLatitude);
			currentUser.setLongitude(newLongitude);
			currentUser.setLatestUpdated(new Date());
			userRepository.save(currentUser);
			System.out.println(newLatitude+":"+newLongitude+"    Updated Location of :"+currentUser.getName());
		}
		catch(Exception e){
			logger.error(e.getMessage());
			return e.getMessage();
		}

		return "Updated Location of :"+currentUser.getName();
	}

	// BroadCast user message to event
	@RequestMapping(value = "/users/broadcast",method = RequestMethod.POST)
	@ResponseBody
	public BroadCastMessageResponse broadCastMessage(@RequestBody BroadCastEvent broadCastMessageFromSender) throws IOException {
		User currentUser=null;
		broadCastMessageFromSender.setCurrentTime(new Date());
		try {
			String userID = broadCastMessageFromSender.getUserID();
			PushNotificationHelper pushNotificationHelper = new PushNotificationHelper();
			Event ticketMasterEvent = broadCastMessageFromSender.getEvent();
			if(ticketMasterEvent==null){
				System.out.println("broadcasted event is null");
			}

			currentUser = userRepository.findOne(Long.valueOf(userID));
			System.out.println(userID+"  userID");
			if(currentUser==null){
				System.out.println("Broadcasting user is null");
			}
			Iterable<User> userList = userRepository.findAll();
			ArrayList<User> nearbyUsers = getNearbyUsers(currentUser,userList,broadCastMessageFromSender.getPersonalMessage());

			for(User demo: nearbyUsers){
				System.out.println(demo.getName());
				if(!demo.equals(currentUser)){
					pushNotificationHelper.sendPushNotification(demo.getToken(),currentUser,ticketMasterEvent,broadCastMessageFromSender);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return new BroadCastMessageResponse();
		}

			System.out.println("expectedtime:"+currentUser.getExpectedTime());
			return new BroadCastMessageResponse(currentUser.getExpectedTime(),broadCastMessageFromSender.getCurrentTime().getTime());

	}


    // BroadCast user message to event
    @RequestMapping(value = "/users/confirmGoing/{userid}/{eventID}",method = RequestMethod.POST)
    @ResponseBody
    public String confirmGoing(@RequestBody YesResponseEvent yesResponseEvent,@PathVariable("userid") String userid,@PathVariable("eventID") String eventID) throws IOException {

        String senderID= yesResponseEvent.getSenderID();
        String responderID  = yesResponseEvent.getResponderID();
        Event currentEvent = yesResponseEvent.getEvent();

		System.out.println(yesResponseEvent.getBroadCastTime()+" checking broadcast time");


        User sender = userRepository.findOne(Long.valueOf(senderID));
        User responder = userRepository.findOne(Long.valueOf(responderID));
		EventGroup eventGroup = new EventGroup(sender.getName(),responder.getName(),sender.getToken(),responder.getToken(),currentEvent.getId(),currentEvent.getName(),new Date(),sender.getExpectedTime(),yesResponseEvent.getBroadCastTime(),responder.getContactNumber(),responder.getEmail());
		eventGroupRepository.save(eventGroup);
		PushNotificationHelper pushNotificationHelper = new PushNotificationHelper();
		pushNotificationHelper.sendToBroadcaster(sender.getToken(),responder,currentEvent);
//		pushNotificationHelper.sendToReciever(responder.getToken(),sender,currentEvent);

    return "";
    }

	// BroadCast user message to event
	@RequestMapping(value = "/users/checkStatus/{userID}/{eventID}",method = RequestMethod.POST)
	@ResponseBody
	public UserList checkStatus(@RequestBody UserFetcher userFetcher) throws IOException {

		System.out.println(userRepository.findOne(Long.valueOf(userFetcher.getSenderID()))+" is polling");

		UserList userList = new UserList();
		Iterable<EventGroup> eventGroups= eventGroupRepository.findAll();

		String actualSenderToken = userFetcher.getSenderToken();
		Long actualEventBroadCastTime = userFetcher.getBroadCastTime();
		String actualEventID = userFetcher.getEventID();
		String actualSenderID = userFetcher.getSenderID();


//String name, String email, String contactNumber, Double latitude, Double longitude , Integer expectedTime, Integer radius, Date latestUpdated,String token
		ArrayList<User> validUsers = new ArrayList<>();
		for(EventGroup currentEventGroup:eventGroups){


			System.out.println(currentEventGroup.getSenderToken()+"  ::  "+actualSenderToken);
			System.out.println(currentEventGroup.getBroadCastTime()+"  ::  "+actualEventBroadCastTime);
			System.out.println(currentEventGroup.getEventID()+"  ::  "+actualEventID);
			System.out.println(currentEventGroup.getResponderRespondedAt().getTime()+"  ::  "+currentEventGroup.getSignalValidUntil());

			if(currentEventGroup.getSenderToken().equals(actualSenderToken) &&
					actualEventBroadCastTime.equals(currentEventGroup.getBroadCastTime()) &&
					actualEventID.equals(currentEventGroup.getEventID())
					&& currentEventGroup.getResponderRespondedAt().getTime() <= currentEventGroup.getSignalValidUntil()){
					User user  = new User();
				user.setName(currentEventGroup.getResponderName());
				user.setContactNumber(currentEventGroup.getResponderContactNumber());
				user.setEmail(currentEventGroup.getResponderContactEmail());
				validUsers.add(user);
			}
		}
	userList.setUserList(validUsers);
		return userList;
	}

	private ArrayList<User> getNearbyUsers(User currentUser, Iterable<User> userList,String personalMessage) {
		ArrayList<User> nearbyUsers = new ArrayList<>();

		if(personalMessage.equals("favouriteList")){
			Iterable<EventGroup> favouriteList = eventGroupRepository.findBysenderName(currentUser.getName());

			HashSet<User> hashSet = new HashSet<>();
			for(EventGroup currentGroup: favouriteList){

				System.out.println(currentGroup.getResponderName());

				User newUser = new User();
				newUser.setToken(currentGroup.responderToken);
				if(!hashSet.contains(newUser)){
					nearbyUsers.add(newUser);
					hashSet.add(newUser);
				}

			}

		}
		else{
			long expectedTime = currentUser.getExpectedTime();
			long MAX_DURATION = MILLISECONDS.convert(expectedTime,MINUTES);

			BroadCastUtil utility = new BroadCastUtil();
			Double myLatitude = currentUser.getLatitude();
			Double myLongitude = currentUser.getLongitude();

			//Check if user is broadCasting for the second time,
			// if yes, you need to double the radius and the waiting time
			int oldRadius = currentUser.getRadius();
			int oldTime = currentUser.getExpectedTime();
			if(personalMessage.equals("secondTime")){
				//currentUser = 	userRepository.findOne(Long.valueOf(currentUser.getId()));
				currentUser	.setRadius(oldRadius*2);
				currentUser.setExpectedTime(oldTime*2);
				userRepository.save(currentUser);
			}

			System.out.println(myLatitude+":"+myLongitude+"   my location");
			for(User user: userList){
				long duration = currentUser.getLatestUpdated().getTime() - user.getLatestUpdated().getTime();
				if(duration < MAX_DURATION){
					Double userLatittude = user.getLatitude();
					Double userLongitude = user.getLongitude();
					System.out.println(userLatittude+":"+userLongitude+"   my location");
					if(utility.distance(myLatitude,myLongitude,userLatittude,userLongitude,'N') < Double.valueOf(currentUser.getRadius()+"")){
						nearbyUsers.add(user);
					}
				}
			}
		}
		return nearbyUsers;
	}


	//Delete User
	@RequestMapping("/user/delete/{id}")
	@ResponseBody
	public String deleteUser(long id){

		try{
			userRepository.delete(id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			return e.getMessage();
			}
		return "User Deleted";
	}


	@RequestMapping(value="/user/add", method = RequestMethod.POST) // Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam String name
			, @RequestParam String email, @RequestParam String contactNumber,
											@RequestParam Double latitude,
											@RequestParam Double longitude
											,@RequestParam  Integer radius) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		User n = new User();
		n.setName(name);
		n.setEmail(email);
		n.setContactNumber(contactNumber);
		n.setLatitude(latitude);
		n.setLongitude(longitude);
		n.setRadius(radius);

		userRepository.save(n);
		return "Saved";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users

		System.out.println(" \n\n\n\n\n\n\n\n\n\n\n\n\n  Printing All ");

		return userRepository.findAll();
	}

	@RequestMapping("/events/fetch")
	@ResponseBody
	public EventList fetchEvents(@RequestBody EventFetcher eventFetcher){

		EventList list = new EventList();

		System.out.println(eventFetcher.toString());
		String defaultRadius= eventFetcher.radius == null ? "5" : eventFetcher.radius;
		Map<String, String> apiInputMap = new HashMap<>();

		String latitude= (eventFetcher.latitude==null) ? "33.425510": eventFetcher.latitude;
		String longitude=(eventFetcher.longitude==null) ? " -111.940005":eventFetcher.longitude;

		String latLong = "\""+latitude+","+longitude+"\"";
		apiInputMap.put("radius", defaultRadius);
		apiInputMap.put("latlong",latLong);

		List<Event> events = TicketMasterAPICaller.getEvents(apiInputMap);
		list.setEventList(events);

		for(Event currentEvent: events){
			System.out.println(currentEvent.getName()+":"+currentEvent.getImageURL());
		}
		return list;
	}
}
