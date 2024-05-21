
/*
 * 
 * General class that simulates a ride or a delivery in a simple Uber app
 * 
 * This class is made abstract since we never create an object. We only create subclass objects. 
 * 
 * Implement the Comparable interface and compare two service requests based on the distance
 */

import java.util.ArrayList;
import java.util.Collections;

abstract public class TMUberService 
{  
  private String from;
  private String to;
  private User user;
  private String type;  // Currently Ride or Delivery but other services could be added      
  private int distance; // Units are City Blocks
  private double cost;  // Cost of the service
  
  public TMUberService(String from, String to, User user, int distance, double cost, String type)
  {
    this.from = from;
    this.to = to;
    this.user = user;
    this.distance = distance;
    this.cost = cost;
    this.type = type;
  }


  // Subclasses define their type (e.g. "RIDE" OR "DELIVERY") 
  abstract public String getServiceType();

  // Getters and Setters
  public String getFrom()
  {
    return from;
  }
  public void setFrom(String from)
  {
    this.from = from;
  }
  public String getTo()
  {
    return to;
  }
  public void setTo(String to)
  {
    this.to = to;
  }
  public User getUser()
  {
    return user;
  }
  public void setUser(User user)
  {
    this.user = user;
  }
  public int getDistance()
  {
    return distance;
  }
  public void setDistance(int distance)
  {
    this.distance = distance;
  }
  public double getCost()
  {
    return cost;
  }
  public void setCost(double cost)
  {
    this.cost = cost;
  }

  // Compare 2 service requests based on distance
  // Add the appropriate method
  public boolean distCompare(TMUberService req1, TMUberService req2){
    /*
     * checks if two service requests are equal in distance
     * @param req1 TMUberservice request input req2 TMUberservice request input
     * @return boolean, true if both have equal distance
     */
    return(req1.getDistance() == req2.getDistance());
  }
  // Check if 2 service requests are equal (this and other)
  // They are equal if its the same type and the same user
  // Make sure to check the type first
  public boolean equals(Object other)
  {
    /*@param other = object to compare to
     * casts other to a tmuberservice, checks if type and user are equal, if yes returns true otherwise returns false
     * @return boolean, true if equals, false if not
     */
    TMUberService Other = (TMUberService) other;
    return(this.getServiceType().equals(Other.getServiceType()) && this.getUser().equals(Other.getUser()));
  }
  
  // Print Information 
  public void printInfo()
  {
    System.out.printf("\nType: %-9s From: %-15s To: %-15s", type, from, to);
    System.out.print("\nUser: ");
    user.printInfo();
  }
}
