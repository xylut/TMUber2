
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI
{
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 
    try{
    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      String action = scanner.nextLine();

      if (action == null || action.equals("")) 
      {
        System.out.print("\n>");
        continue;
      }
      // Quit the App
      else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
        return;
      // Print all the registered drivers
      else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
      {
        tmuber.listAllDrivers(); 
      }
      // Print all the registered users
      else if (action.equalsIgnoreCase("USERS"))  // List all users
      {
        tmuber.listAllUsers(); 
      }
      // Print all current ride requests or delivery requests
      else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
      {
        tmuber.listAllServiceRequests(); 
      }
      // Register a new driver
      else if (action.equalsIgnoreCase("REGDRIVER")) // if user types regdriver
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();      // gets name from user using scanner
        }
        String address  = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();      // gets address from user using scanner
        }
        String carModel = "";
        System.out.print("Car Model: ");
        if (scanner.hasNextLine())
        {
          carModel = scanner.nextLine();  // gets car model from user using scanner
        }
        String license = "";
        System.out.print("Car License: ");
        if (scanner.hasNextLine())
        {
          license = scanner.nextLine();   //gets license plate from user using scanner
        }
        tmuber.registerNewDriver(name, address, carModel, license); // registers driver, if registering creates error gets error message
        System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s", name, carModel, license); // prints driver info
      }
      
      // Register a new user
      else if (action.equalsIgnoreCase("REGUSER")) 
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        double wallet = 0.0;
        System.out.print("Wallet: ");
        if (scanner.hasNextDouble())
        {
          wallet = scanner.nextDouble();
          scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
        }
        tmuber.registerNewUser(name, address, wallet);
        System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
      }
      // Request a ride
      else if (action.equalsIgnoreCase("REQRIDE")) 
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestRide() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)
        String accID = "";
        System.out.print("User Account Id: ");
        if (scanner.hasNextLine())
        {
          accID = scanner.nextLine(); // gets account id from user input using scanner
        }
        String from = "";
        System.out.print("From Address: ");
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine(); // gets start address from user input using scanner
        }
        String to = "";
        System.out.print("To Address: ");
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();  // gets end address from user input using scanner
        }
        tmuber.requestRide(accID, from, to); 
        //prints request info
        System.out.printf("RIDE for: %-15s From Address: %-15s To Address: %-15s", tmuber.getUser(accID).getName(), from, to);
      }

      // Request a food delivery
      else if (action.equalsIgnoreCase("REQDLVY")) //checks if user input = reqdlvy
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestDelivery() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)
        // "Restaurant: "           (string)
        // "Food Order #: "         (string)
        String accID = "";
        System.out.print("User Account Id: ");
        if (scanner.hasNextLine())
        {
          accID = scanner.nextLine(); // gets accid from user input using scanner
        }
        String from = "";
        System.out.print("From Address: ");
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine(); // gets start address from user input using scanner
        }
        String to = "";
        System.out.print("To Address: ");
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine(); // gets end address from user input using scanner
        }
        String restaurant = "";
        System.out.print("Restaurant: ");
        if (scanner.hasNextLine())
        {
          restaurant = scanner.nextLine();  // gets restaurant name from user input using scanner
        }
        String foodNum = "";
        System.out.print("Food Order #: ");
        if (scanner.hasNextLine())
        {
          foodNum = scanner.nextLine(); // gets food order id from user input using scanner
        }
        tmuber.requestDelivery(accID, from, to, restaurant, foodNum); // creates deliv request, if fails gets error and prints
        //if created no problem prints delivery info
        System.out.printf("Delivery for: %-15s From Address: %-15s To Address: %-15s", tmuber.getUser(accID).getName(), from, to);
      
      }
      // Sort users by name
      else if (action.equalsIgnoreCase("SORTBYNAME")) 
      {
        tmuber.sortByUserName();
      }
      // Sort users by number of ride they have had
      else if (action.equalsIgnoreCase("SORTBYWALLET")) 
      {
        tmuber.sortByWallet();
      }
      // Sort current service requests (ride or delivery) by distance
      else if (action.equalsIgnoreCase("SORTBYDIST")) 
      {
        tmuber.sortByDistance();
      }
      // Cancel a current service (ride or delivery) request
      else if (action.equalsIgnoreCase("CANCELREQ")) 
      {
        int request = -1;
        int zone = -1;
        System.out.print("Zone #: ");
        if (scanner.hasNextInt())
        {
          zone = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }
        System.out.print("Request #: ");
        if (scanner.hasNextInt())
        {
          request = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }
        tmuber.cancelServiceRequest(zone, request);
        System.out.println("Zone # " + zone + " Service request #" + request + " cancelled");
      }
      // Drop-off the user or the food delivery to the destination address
      else if (action.equalsIgnoreCase("DROPOFF")) 
      {
        String driverID = "";
        System.out.print("Driver Id: ");
        if (scanner.hasNextLine())
        {
          driverID = scanner.nextLine();
        }
        tmuber.dropOff(driverID);
        System.out.println("Driver " + driverID + " Dropping Off");
      }
      // Get the Current Total Revenues
      else if (action.equalsIgnoreCase("REVENUES")) 
      {
        System.out.println("Total Revenue: " + tmuber.totalRevenue);
      }
      // Unit Test of Valid City Address 
      else if (action.equalsIgnoreCase("ADDR")) 
      {
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        System.out.print(address);
        if (CityMap.validAddress(address))
          System.out.println("\nValid Address"); 
        else
          System.out.println("\nBad Address"); 
      }
      // Unit Test of CityMap Distance Method
      else if (action.equalsIgnoreCase("DIST")) 
      {
        String from = "";
        System.out.print("From: ");
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        String to = "";
        System.out.print("To: ");
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        System.out.print("\nFrom: " + from + " To: " + to);
        System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
      }
      //Unit Test of PickUP method
      else if (action.equalsIgnoreCase("PICKUP")){
        String driverID = "";
        System.out.print("Driver Id: ");
        if(scanner.hasNextLine()){
          driverID = scanner.nextLine();
        }
        try{
        tmuber.pickUp(driverID);
        System.out.println("");
        System.out.println("Driver " + driverID + " Picking Up in Zone " + tmuber.getDriver(driverID).getZone());
        }
        catch(RuntimeException e){
          System.out.println(e.getMessage());
        }
      }
      //Unit Test if loadUsers method
      else if(action.equalsIgnoreCase("LOADUSERS")){
        String filename = "";
        System.out.print("User File: ");
        if(scanner.hasNextLine()){
          filename = scanner.nextLine();
        }
        try{
        ArrayList<User> user = TMUberRegistered.loadPreregisteredUsers(filename);
        tmuber.setUsers(user);
        System.out.println("Users Loaded");
        }
        catch(FileNotFoundException e){
          System.out.println("Users File: " + filename + " Not Found");
        }
      }
      //Unit Test of loaddrivers method
      else if(action.equalsIgnoreCase("LOADDRIVERS")){
        String filename = "";
        System.out.print("Drivers File: ");
        if(scanner.hasNextLine()){
          filename = scanner.nextLine();
        }
        try{
        ArrayList<Driver> driver = TMUberRegistered.loadPreregisteredDrivers(filename);
        tmuber.setDrivers(driver);
        System.out.println("Drivers Loaded");
        }
        catch(FileNotFoundException e){
          System.out.println("Drivers File: " + filename + " Not Found");
        }
      }
      //Unit Test of driveto method
      else if(action.equalsIgnoreCase("DRIVETO")){
        String driverId = "";
        System.out.print("Driver Id: ");
        if(scanner.hasNextLine()){
          driverId = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if(scanner.hasNextLine()){
          address = scanner.nextLine();
        }
        tmuber.driveTo(driverId, address);
        System.out.println("Driver " + driverId + " Now in Zone " + CityMap.getCityZone(address));
      }
      System.out.print("\n>");
    }
  }
catch(FileNotFoundException e){
  System.out.println(e.getMessage());
}
catch(RuntimeException e){    // catches custom errors created in TMUberSystemManager and prints their message
  System.out.println(e.getMessage());
}
  }
}




