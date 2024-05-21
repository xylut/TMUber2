//alex clouthier 501249820
/*
 * 
 * This class simulates a car driver in a simple uber app 
 * 
 * Everything has been done for you except the equals() method
 */
public class Driver
{
  private String id;
  private String name;
  private String carModel;
  private String licensePlate;
  private String address;
  private double wallet;
  private String type;
  private int zone;
  private TMUberService service;
  
  public static enum Status {AVAILABLE, DRIVING};
  private Status status;
    
  
  public Driver(String id, String name, String carModel, String licensePlate, String address)
  {
    this.id = id;
    this.name = name;
    this.carModel = carModel;
    this.licensePlate = licensePlate;
    this.address = address;
    this.status = Status.AVAILABLE;
    this.wallet = 0;
    this.type = "";
    this.zone = CityMap.getCityZone(address);
    this.service = null;
  }
  // Print Information about a driver
  public void printInfo()
  {
    System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f Status: %-15s Address: %-15s Zone %2s", 
                      id, name, carModel, licensePlate, wallet, status, address, String.valueOf(zone));
  }
  // Getters and Setters
  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public int getZone()
  {
    return zone;
  }
  public void setZone(int nZone)
  {
    this.zone = nZone;
  }
  public TMUberService getService()
  {
    return this.service;
  }
  public void setService(TMUberService service)
  {
    this.service = service;
  }
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getCarModel()
  {
    return carModel;
  }
  public void setCarModel(String carModel)
  {
    this.carModel = carModel;
  }
  public String getLicensePlate()
  {
    return licensePlate;
  }
  public void setLicensePlate(String licensePlate)
  {
    this.licensePlate = licensePlate;
  }
  public String getAddress()
  {
    return address;
  }
  public void setAddress(String address)
  {
    this.address = address;
  }
  public Status getStatus()
  {
    return status;
  }
  public void setStatus(Status status)
  {
    this.status = status;
  }
  public double getWallet()
  {
    return wallet;
  }
  public void setWallet(double wallet)
  {
    this.wallet = wallet;
  }
  /*
   * Two drivers are equal if they have the same name and license plates.
   * This method is overriding the inherited method in superclass Object
   * 
   * Fill in the code 
   */
  public boolean equals(Object other){
    /*@param other object to check if equal
     * 
     * casts other to Driver and checks if has same name & license plate
     * 
     * @return true if this and other are equal, false otherwise
     */
    Driver Other = (Driver) other;
    return(this.getName().equals(Other.getName()) && this.getLicensePlate().equals(Other.getLicensePlate()));
  }
  
  // A driver earns a fee for every ride or delivery
  public void pay(double fee)
  {
    wallet += fee;
  }

}
