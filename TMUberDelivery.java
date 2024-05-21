
/*
 * 
 * This class simulates a food delivery service for a simple Uber app
 * 
 * A TMUberDelivery is-a TMUberService with some extra functionality
 */
public class TMUberDelivery extends TMUberService
{
  public static final String TYPENAME = "DELIVERY";
 
  private String restaurant; 
  private String foodOrderId;
   
   // Constructor to initialize all inherited and new instance variables 
  public TMUberDelivery(String from, String to, User user, int distance, double cost,
                        String restaurant, String order)
  {
  super(from, to, user, distance, cost, TYPENAME);
  this.restaurant = restaurant;
  this.foodOrderId = order;
  }
 
  
  public String getServiceType()
  {
    return TYPENAME;
  }
  public String getRestaurant()
  {
    return restaurant;
  }
  public void setRestaurant(String restaurant)
  {
    this.restaurant = restaurant;
  }
  public String getFoodOrderId()
  {
    return foodOrderId;
  }
  public void setFoodOrderId(String foodOrderId)
  {
    this.foodOrderId = foodOrderId;
  }
  /*
   * Two Delivery Requests are equal if they are equal in terms of TMUberServiceRequest
   * and the restaurant and food order id are the same  
   */
  public boolean equals(Object other)
  {
    // First check to see if other is a Delivery type
    // Cast other to a TMUService reference and check type
    // If not a delivery, return false
    /*@param other = object to compare to
     * overrides equals method, casts other to a TMUberDelivery if it is an instance of it
     * if not returns false, if yes checks parent equals method and if restaurant are same and food order id same
     * if all same returns true, otherwise returns false
     * 
     * @return true if equals, false otherwise
     */
    if(other instanceof TMUberDelivery){
      TMUberDelivery Other = (TMUberDelivery) other;
      return(super.equals(Other) && this.getRestaurant().equals(Other.getRestaurant()) && this.getFoodOrderId().equals(Other.getFoodOrderId()));
    }
    else{
      return false;
    }
    
    
    // If this and other are deliveries, check to see if they are equal
  }
  /*
   * Print Information about a Delivery Request
   */
  public void printInfo()
  {
    // i mean what is there to comment i did exactly what it says (im bad at commenting man i tried my best)
    // Fill in the code
    // Use inheritance to first print info about a basic service request
    super.printInfo();
    // Then print specific subclass info
    System.out.printf("\nRestaurant: %-9s Food Order #: %-3s", restaurant, foodOrderId); 
  }
}
