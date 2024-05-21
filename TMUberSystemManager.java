
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  private TreeMap<String, User>   users;
  private ArrayList<Driver> drivers;

  private Queue<TMUberService>[] serviceRequests; 

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager() throws FileNotFoundException
  {
    serviceRequests = new Queue[4];
    for(int i = 0 ; i <= 3 ; i++){
      serviceRequests[i] = new LinkedList<TMUberService>();
    }

    users = new TreeMap<>(); 
    drivers = new ArrayList<>();
    
    totalRevenue = 0;
  }

  // Given user account id, gets user from map
  // Return null if not found
  public User getUser(String accountId)
  /*
   * gets user given accountId string
   *
   * @param accountId = user account id
   * @return user object associated with that account id, null if not found
  */
  {
    return users.get(accountId);
  }

  public Driver getDriver(String driverId)
  /*
   * gets driver from driver id string
   *
   * @param driverId = driver account id
   * @return driver object associated with that driver id, null if not found
  */
  {
    for(int i = 0 ; i < drivers.size() ; i++){
      if(drivers.get(i).getId().equals(driverId)){  //loops over drivers array list, compares id's to find a match
        return drivers.get(i);
      }
    }
    return null;
  }
  
  // Check for duplicate user
  private boolean userExists(User user)
  {
    //@param user is an instance of User Class
    //@return true if user in map of users, otherwise returns false
    return(users.containsValue(user));
  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 {
  //@param driver is a Driver class object
  //@return true if driver in arrayList<Driver> drivers initialized above
   return(drivers.contains(driver));
 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private boolean existingRequest(TMUberService req)
  {
  //@param req = service request, used to check for a duplicate of itself
  //@return true if duplicate request found, false otherwise
  int zone = CityMap.getCityZone(req.getFrom());  //gets zone from the request
  if(serviceRequests[zone].contains(req)){        //checks if queue contains req, uses zone to index
    return true;
  }
  return false;
}
  //given a user, check is user already has a ride request placed
  private boolean existingRideRequest(User user){
    /* @param user is instance of User class
     * @return true if user already has a ride request, false otherwise
     * 
     * user is only allowed one ride request at a time, this method checks that
     * creates an ArrayList of all TMUberRides in service requests
     * compares their user to given user, returns true if same
     */
    ArrayList<TMUberRide> allRides = new ArrayList<>(); //initialize list of rides
    for(int i = 0; i < serviceRequests.length ; i++){
      Object [] requestsArray = serviceRequests[i].toArray(); //turn queue into an array
      for(int j = 0 ; j < requestsArray.length ; j++){
        if(requestsArray[j] instanceof TMUberRide){           //if object in requests array is instance of a tmuber ride                 
          TMUberRide ride = (TMUberRide) requestsArray[j];    //casts it to a ride and adds to ride arrayList
          allRides.add(ride);
        }
      }
    }
    for(int i = 0 ; i < allRides.size() ; i++){
      if(allRides.get(i).getUser().equals(user)){     //loops over every ride and compares users
        return true;
      }
    }
    return false;
  }
  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  // @param distance = distance of a delivery
  // @return cost of a delivery via distance times deliveryrate
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  //@param int distance is distance of ride
  //@return cost of ride
  {
    return distance * RIDERATE;
  }
  
  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    /*
     * prints info about every user in map of users, uses index variable to list users starting from 1
     */
    System.out.println();
    int index = 1;      
    for (Map.Entry<String, User> set : users.entrySet())  // loops over map of users
    {
      System.out.printf("%-2s. ", index);
      set.getValue().printInfo();   //prints info about individual user
      System.out.println();
      index++;  //increments index variable
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    /* prints info about all drivers in drivers array list, first creates a one line gap
     * loops over all drivers, index is i+1 to display drivers in a non 0 indexed way
     * prints the index, then the driver info, then adds a line of blank space
     */
    System.out.println();
    for (int i = 0 ; i < drivers.size() ; i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {
    // prints information about all service requests in array of requests
  
    for(int i = 0; i < serviceRequests.length ; i++){
      int index =  1;   // used to list requests non zero indexed
      Object [] zonetemp = serviceRequests[i].toArray();  // convert queue to array, allows code to iterate over service requests
      if( i ==0){
        System.out.println(); 
        System.out.println("ZONE 0");
        System.out.println("======");     // prints info to seperate zones, requests are printed in the area for their respective zone
      }
      else if(i==1){
        System.out.println();
        System.out.println("ZONE 1");
        System.out.println("======");
      }
      else if(i==2){
        System.out.println();
        System.out.println("ZONE 2");
        System.out.println("======");
      }
      else if(i==3){
        System.out.println();
        System.out.println("ZONE 3");
        System.out.println("======");
      }
      for(int j = 0 ; j < zonetemp.length ; j++){
        System.out.println();
        System.out.printf("%-2s. ------------------------------------------------------", index);
        TMUberService service = (TMUberService) zonetemp[j];  //casts object in array to service 
        service.printInfo();                                  // then prints its info
        System.out.println(); 
        index++;                                              // increments index
      }
    }
      
    }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet) 
  {
    /*@param String name = name of user, String address = address of user, double wallet = initial money in wallet
    *
    * checks if given address is valid using validAddress method from CityMap, if not return false and set invalid address error
    * checks if given name is valid , if not return false and set invalid name error
    * checks for non negative wallet amount , returns false and sets error if neg wallet
    * creates user and adds it to map of users
    *
    * throws errors if parameters invalid
    */
    if (!CityMap.validAddress(address))
    {
      throw new InvalidUserAddressException("Invalid User Address");  //handled in TMUberUI
    }
    if(name == null || name.equals("")){
      throw new InvalidUserNameException("Invalid User Name");  //handled in TMUberUI
    }
    if(wallet < 0){
      throw new InvalidUserBalanceException("Invalid User Balance");  //handled in TMUberUI
    }
    // If all parameter checks pass then create and add new user to array list users
    // Make sure you check if this user doesn't already exist!
    User temp = new User(TMUberRegistered.generateUserAccountId(users.size()), name, address, wallet); 
    if(!userExists(temp)){
      users.put(temp.getAccountId(), temp);
    }
    else{
      throw new UserAlreadyExistsException("User Already Exists");    //handled in TMUberUI
    }
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String address, String carModel, String carLicencePlate) 
  {
    /*@param String name = name of driver,String address = address of driver, String carModel = driver's car model, String carLicencePlate = driver's cars licence plate
     * 
     * checks if String inputs are valid, if not throws custom errors describing the problem (handled in TMUberUI)
     * checks if driver already exists, if true throws error, if false adds to array list of drivers
     */
    if(name == null || name.equals("")){
      throw new InvalidDriverNameException("Invalid Driver Name");
    }
    if(carModel == null || carModel.equals("")){
      throw new InvalidCarModelException("Invalid Car Model");
    }
    if(carLicencePlate == null || carLicencePlate.equals("")){
      throw new InvalidLicensePlateException("Invalid License Plate");
    }
    if(!CityMap.validAddress(address)){
      throw new InvalidAddressException("Invalid address");
    }
    // If all parameter checks pass then create and add new user to array list users
    // Make sure you check if this user doesn't already exist!
    Driver temp = new Driver(TMUberRegistered.generateDriverId(drivers), name, carModel, carLicencePlate, address);
    if(!driverExists(temp)){
      drivers.add(temp);
    }
    else{
      throw new DriverAlreadyExistsException("Driver Already Exists");
    }
  }

  public void driveTo(String driverID, String address){
    /*
     * @param String driverID = driver id of a driver we want to move, String address is address to move driver to
     * checks for valid inputs, throws errors if not valid
     * if all valid, changes driver address to given address and set zone accordingly
     */
    if(!CityMap.validAddress(address)){
      throw new InvalidAddressException("Invalid Address");
    }
    
    Driver temp = getDriver(driverID);  //gets driver from given driverID String 
    if(temp==null){
      throw new NoDriverFoundException("Driver Not Found"); // checks if it exits
    }
    if(temp.getStatus() != Driver.Status.AVAILABLE){    //checks if driver available
      throw new DriverBusy("Driver not available");
    }
    else{
      temp.setAddress(address);     //sets driver address and zone
      temp.setZone(CityMap.getCityZone(address));
    }
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to) 
  {
    // Check for valid parameters
	// Use the account id to find the user object in the list of users
    // Get the distance for this ride
    // Note: distance must be > 1 city block!
    // Find an available driver
    // Create the TMUberRide object
    // Check if existing ride request for this user - only one ride request per user at a time!
    // Change driver status
    // Add the ride request to the list of requests
    // Increment the number of rides for this user
    /*@param String accountID = user id, str from = start address, str to = end address
     * 
     * checks for validity of inputs, throws errors if not valid
     * then creates ride request, checks if it already exists
     * if yes throws error, if no adds request to queue
     */
  User user = getUser(accountId); //get user and check user exists
  if(user == null)
  {
    throw new UserNotFoundException("User Not Found");   //throw error if does not exist
  }
  if(!CityMap.validAddress(from) || !CityMap.validAddress(to))  //checks addresses
  {
    throw new InvalidAddressException("Invalid Address");
  }

  int dist = CityMap.getDistance(from, to);
  if(dist < 1)
  {
    throw new InsufficientDistanceException("Insufficient Travel Distance");  //checks travel distance great enough
  }
  /*
   * loops over all service requests and checks if user already has a pending request, if yes deducts that amount 
   * from user's current wallet (only locally, does not affect user wallet) to ensure they have money for this request
   * after paying for previous requests
   * 
   * prevents negative balance after placing a ride request then delivery request
   */
    double nWallet = user.getWallet();  
    for(int i = 0 ; i <= 3 ; i++){
      Object [] queueArray = serviceRequests[i].toArray();
      for(int j = 0 ; j < queueArray.length ; j++){
        TMUberService request = (TMUberService) queueArray[j];  //loops over service requests, turn queues into arrays, casts objects to service and checks if user is same as given user
      if(request.getUser().equals(getUser(accountId))){
        nWallet -= request.getCost();   //takes away pending request costs from local wallet
      }
    }
    }
    
    double cost = getRideCost(dist);
    if(cost > nWallet){
      throw new InsufficientFundsException("Insufficient Funds"); //checks if enough money 
    }

    TMUberRide ride = new TMUberRide(from, to, user, dist, cost);
    int zone = CityMap.getCityZone(from);
          if(existingRequest(ride)){
            throw new DuplicateRequestException("Ride Request Already Exists");
          }
          if(existingRideRequest(user)){
            throw new UserAlreadyHasRideRequestException("User Has Existing Ride Request");
          }
          else{
            serviceRequests[zone].add(ride);  //adds to queue 
            user.addRide();
          }
        }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId) 
  {
    /*@param String accountID = user id, str from = start address, str to = end address, str restaurant = restaurant name
     * str foodOrderId = food order id
     * done in the exact steps described above in request ride but creates delivery instead of ride
     * and checks validity of every input, throws corresponding errors 
     * creates TMUberDelivery object and adds to queue for respective zone
     */
  User temp = getUser(accountId);
  if(temp == null)
  {
    throw new UserNotFoundException("User Not Found");
  }
  if(restaurant == null){
    throw new InvalidRestaurantNameException("Invalid Restaurant Name");
  }
  if(foodOrderId == null){
    throw new InvalidFoodOrderIDException("Invalid Food Order Id");
  }
  if(!CityMap.validAddress(from) || !CityMap.validAddress(to))
  {
    throw new InvalidAddressException("Invalid Address");
  }

  int dist = CityMap.getDistance(from, to);
  if(dist < 1)
  {
    throw new InsufficientDistanceException("Insufficient Travel Distance");
  }
    double nWallet = temp.getWallet();
    for(int i = 0 ; i <= 3 ; i++){
      Object [] queueArray = serviceRequests[i].toArray();
      for(int j = 0 ; j < queueArray.length ; j++){
        TMUberService request = (TMUberService) queueArray[j];
      if(request.getUser().equals(getUser(accountId))){
        nWallet -= request.getCost();
      }
    }
    }

    double cost = getDeliveryCost(dist);
    if(cost > nWallet){
      throw new InsufficientFundsException("Insufficient Funds");
    }
    int zone = CityMap.getCityZone(from);
    TMUberDelivery deliv = new TMUberDelivery(from, to, temp, dist, cost, restaurant, foodOrderId);
          if(!existingRequest(deliv)){
            serviceRequests[zone].add(deliv);
            temp.addDelivery();
          }
          else{
            throw new DuplicateRequestException("Delivery Request Already Exists");
          }
  }


  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list
  public void cancelServiceRequest(int zone, int request) 
  {
    request--;
    /*@param int zone = zone of request to cancel, int request = request to cancel
     * 
     * gets array of queue specified by zone, removes object at index request
     * recreates queue with new array
     */
    if(zone > 3){
      throw new InvalidZoneNumberException("Zone Does Not Exist");
    }
    Object [] requests = serviceRequests[zone].toArray();
    if(request >= requests.length){
      throw new InvalidRequestNumberException("Request Does Not Exist");
    }
    TMUberService removedRequest = (TMUberService) requests[request];
    User user = removedRequest.getUser();
    if(removedRequest instanceof TMUberRide){
      user.removeRide();
    }
    else {
      user.removeDelivery();
    }

    requests[request] = null;
    serviceRequests[zone].clear();
    for(int i = 0 ; i < requests.length ; i++){
      if(requests[i] != null){
        TMUberService service = (TMUberService) requests[i];
        serviceRequests[zone].add(service);
      }
    }
  } 
  
  public void pickUp(String driverID) {
    /*
     * @param string DriverID = driver id of a user
     * 
     * gets driver using id, checks for zone, status, existence, and no current service instance
     * throws error if any of the above in wrong state
     * 
     * removes request from queue of same zone as driver, if queue empty throws error
     * sets driver service instance to service removed from queue
     * sets driver status to driving
     * sets address to service from address
     * sets driver zone according to this address
     */
    Driver driver = getDriver(driverID);
    if(driver == null){
      throw new DriverNotFoundException("Driver Not Found");
    }
    if(driver.getStatus() == Driver.Status.DRIVING){
      throw new DriverBusy("Driver Busy");
    }
    int zone = driver.getZone();
    TMUberService service = serviceRequests[zone].poll();

    if(service == null){
      throw new NoAvailableRequestException("No Service Requests in Zone " + zone);
    }
    driver.setService(service);
    driver.setStatus(Driver.Status.DRIVING);
    driver.setAddress(service.getFrom());
    driver.setZone(CityMap.getCityZone(driver.getAddress()));
  }
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public void dropOff(String driverID) 
  {
    /*
     * @param String driverID, used to find driver object
     * 
     * checks if driver exists, is driving and has a service instance
     * if any of the above not true throws correpsonding error
     * 
     * gets user from service
     * charges user, pays driver, adds to totalRevenue
     * sets driver status to available
     * sets driver service instance to null
     * sets driver address to to address of service and sets zone accordingly
     */
    Driver driver = getDriver(driverID);
    if(driver == null){
      throw new DriverNotFoundException("Driver Not Found");
    }
    if(driver.getStatus() == Driver.Status.AVAILABLE){
      throw new DriverNotBusyException("Driver not on service request");
    }
    TMUberService service = driver.getService();
    if(service == null){
      throw new DriverNotBusyException("Driver not on service request");
    }
    User user = service.getUser();
    double cost = service.getCost();
    totalRevenue += cost - ( cost * PAYRATE);
    driver.pay(cost * PAYRATE);
    user.payForService(cost);
    driver.setStatus(Driver.Status.AVAILABLE);
    driver.setService(null);
    driver.setAddress(service.getTo());
    driver.setZone(CityMap.getCityZone(driver.getAddress()));
  }


  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {
    //converts map to array list
    ArrayList<User> temp = new ArrayList<>();
    for (String name: users.keySet()) {
           temp.add(users.get(name));
    }
    //uses collections.sort to sort arraylist of users by name in ascending order using nameComparator created below
    Collections.sort(temp, new NameComparator());
    //printing part
    System.out.println();
    for (int i = 0; i < temp.size(); i++) //prints array list of users
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      temp.get(i).printInfo();
      System.out.println(); 
    }

  }

  // Helper class for method sortByUserName
  private class NameComparator implements java.util.Comparator<User>
  {
    //compares names of user a to b, returns > 0 if a > b, 0 if equal,  < 0 if b > a
    public int compare(User a , User b){
      int pos = a.getName().compareTo(b.getName());
      return pos;
    }
  }

  // Sort users by number amount in wallet
  // Then ist all users
  public void sortByWallet()
  {
    //uses collections.sort to sort users arraylist in ascending order based on wallet value using comparator created below
    ArrayList<User> temp = new ArrayList<>();
    for (String name: users.keySet()) {
           temp.add(users.get(name));
    }
    //uses collections.sort to sort arraylist of users by name in ascending order using nameComparator created below
    Collections.sort(temp, new UserWalletComparator());
    //printing part
    System.out.println();
    for (int i = 0; i < temp.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      temp.get(i).printInfo();
      System.out.println(); 
    }
  }
  // Helper class for use by sortByWallet
  private class UserWalletComparator implements java.util.Comparator<User>
  {
    //compares value of user a wallet to user b wallet, returns 1 if a > b, 0 if equal,  -1 if b > a
    public int compare(User a , User b){
      if(a.getWallet() > b.getWallet()){
        return 1;
      }
      else if (a.getWallet() < b.getWallet()){
        return -1;
      }
      else{
        return 0;
      }
    }
  }

  // Sort trips (rides or deliveries) by distance
  // Then list all current service requests
  public void sortByDistance()
  {
    //sort servicerequests arraylist in ascending order based on distance using comparator created below
    ArrayList<TMUberService> serviceRequest = new ArrayList<>();
    for(int i = 0 ; i <= 3 ; i++){
      Object [] list = serviceRequests[i].toArray();
      for(int j = 0 ; j < list.length ; j++){
        TMUberService service = (TMUberService) list[j];
        serviceRequest.add(service);
      }
    }
    Collections.sort(serviceRequest, new serviceRequestsComparator());
    for (int i = 0; i < serviceRequest.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      serviceRequest.get(i).printInfo();
      System.out.println(); 
    }
  
  }
  private class serviceRequestsComparator implements java.util.Comparator<TMUberService>{
    public int compare(TMUberService a , TMUberService b){
      //compares value of request a distance to request b wallet, returns 1 if a > b, 0 if equal,  -1 if b > a
      if(a.getDistance() > b.getDistance()){
        return 1;
      }
      else if (a.getDistance() < b.getDistance()){
        return -1;
      }
      else{
        return 0;
      }
  }
}
  public void setUsers(ArrayList<User> userList){
    /*
     * @param ArrayList<User> userList = given array list of class User to add to map of users
     * loops over userList, adds to map of users, key = user id, value = User object
     */
  for(int i = 0 ; i < userList.size() ; i++){
    User temp = userList.get(i);
    users.put(temp.getAccountId(), temp);
  }
  }

  public void setDrivers(ArrayList<Driver> driverList){
    /*
     * @param ArrayList<Driver> driverList, list of Driver objects to add to drivers list
     */
    drivers.addAll(driverList);
  }
}
//below is all classes for custom errors created
class InvalidUserNameException extends RuntimeException{
  public InvalidUserNameException(){}
  public InvalidUserNameException(String message){
      super(message);
    }
}

class InvalidUserAddressException extends RuntimeException{
  public InvalidUserAddressException(){}
  public InvalidUserAddressException(String message){
      super(message);
    }
}

class InvalidAddressException extends RuntimeException{
  public InvalidAddressException(){}
  public InvalidAddressException(String message){
      super(message);
    }
}

class UserNotFoundException extends RuntimeException{
  public UserNotFoundException(){}
  public UserNotFoundException(String message){
      super(message);
    }
  }
  
class DriverNotFoundException extends RuntimeException{
  public DriverNotFoundException(){}
  public DriverNotFoundException(String message){
      super(message);
    }
  }

class UserAlreadyExistsException extends RuntimeException{
  public UserAlreadyExistsException(){}
  public UserAlreadyExistsException(String message){
      super(message);
    }
  }

class DriverAlreadyExistsException extends RuntimeException{
  public DriverAlreadyExistsException(){}
  public DriverAlreadyExistsException(String message){
      super(message);
    }
  }

  class InvalidUserBalanceException extends RuntimeException{
    public InvalidUserBalanceException(){}
    public InvalidUserBalanceException(String message){
        super(message);
      }
  }

  class InvalidCarModelException extends RuntimeException{
    public InvalidCarModelException(){}
    public InvalidCarModelException(String message){
        super(message);
      }
  }

  class InvalidDriverNameException extends RuntimeException{
    public InvalidDriverNameException(){}
    public InvalidDriverNameException(String message){
        super(message);
      }
  }

  class InvalidLicensePlateException extends RuntimeException{
    public InvalidLicensePlateException(){}
    public InvalidLicensePlateException(String message){
        super(message);
      }
  }
  class NoAvailableDriverException extends RuntimeException{
    public NoAvailableDriverException(){}
    public NoAvailableDriverException(String message){
        super(message);
      }
  }

  class NoDriverFoundException extends RuntimeException{
    public NoDriverFoundException(){}
    public NoDriverFoundException(String message){
        super(message);
      }
  }

  class InsufficientDistanceException extends RuntimeException{
    public InsufficientDistanceException(){}
    public InsufficientDistanceException(String message){
        super(message);
      }
  }

  class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(){}
    public InsufficientFundsException(String message){
        super(message);
      }
  }

  class ExistingRequestException extends RuntimeException{
    public ExistingRequestException(){}
    public ExistingRequestException(String message){
        super(message);
      }
  }

  class InvalidRequestNumberException extends RuntimeException{
    public InvalidRequestNumberException(){}
    public InvalidRequestNumberException(String message){
        super(message);
      }
  }
  
  class DriverBusy extends RuntimeException{
    public DriverBusy(){
      super();
    }
    public DriverBusy(String message){
      super(message);
    }
    
  }

  class DriverNotBusyException extends RuntimeException{
    public DriverNotBusyException(){}
    public DriverNotBusyException(String message){
        super(message);
      }
  }

  class NoAvailableRequestException extends RuntimeException{
    public NoAvailableRequestException(){}
    public NoAvailableRequestException(String message){
        super(message);
      }
  }

  class InvalidRestaurantNameException extends RuntimeException{
    public InvalidRestaurantNameException(){}
    public InvalidRestaurantNameException(String message){
        super(message);
      }
  }

  class InvalidFoodOrderIDException extends RuntimeException{
    public InvalidFoodOrderIDException(){}
    public InvalidFoodOrderIDException(String message){
        super(message);
      }
  }

  class UserAlreadyHasRideRequestException extends RuntimeException{
    public UserAlreadyHasRideRequestException(){}
    public UserAlreadyHasRideRequestException(String message){
        super(message);
      }
  } 

  class DuplicateRequestException extends RuntimeException{
    public DuplicateRequestException(){}
    public DuplicateRequestException(String message){
        super(message);
      }
  }   
  class InvalidZoneNumberException extends RuntimeException{
    public InvalidZoneNumberException(){}
    public InvalidZoneNumberException(String message){
        super(message);
      }
  }   