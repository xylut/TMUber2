
import java.util.Arrays;
import java.util.Scanner;



// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap
{
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  public static enum Stats {AVAILABLE, DRIVING};
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  // An easier solution would use String method split()
  // Other solutions are possible - you may replace this code if you wish
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    // Fill in the code
    // Make use of the helper methods above if you wish
    // There are quite a few error conditions to check for 
    // e.g. number of parts != 3
    /*
     checks for valid address by using getParts method defined above, checks that address is 3 parts
     checks that first part is of length two and is all digits, checks if second part is a number
     followed by correct suffix, checks if third part is either the string "avenue" or "street"
     returns true if all of the above is true, otherwise returns false
     */
    String [] Parts = getParts(address);
    if(Parts.length == 3){   
      if(Parts[0].length()==2 && Parts[1].length() ==3 && (Parts[2].equalsIgnoreCase("Street") || Parts[2].equalsIgnoreCase("Avenue"))){
        if(allDigits(Parts[0])){
          if(allDigits(String.valueOf(Parts[1].charAt(0)))){
        int num = Integer.parseInt(Parts[1].substring(0,1));
        String suffix = Parts[1].substring(1);
        if((num == 1 && suffix.equals("st")) || (num == 2 && suffix.equals("nd")) || (num == 3 && suffix.equals("rd")) || (num >= 4 && suffix.equals("th"))){
          return true;
        }
      }
    }
  }
}
    return false;
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4] 
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address)
  /*
   takes in address string uses getParts method defined above to split into parts
   checks if third part is avenue and sets the city block
   if it's not avenue, assumes it is street and sets city block accordingly.
   returns int array of city block {avenue, street} 
   */
  {
    int[] block = {-1, -1};
    String [] parts = getParts(address);

    if(parts[2].equalsIgnoreCase("Avenue")){
      block[1] = Integer.parseInt(String.valueOf(address.charAt(0)));
      block[0] = Integer.parseInt(String.valueOf(address.charAt(3)));
    }
    else{
      block[0] = Integer.parseInt(String.valueOf(address.charAt(0)));
      block[1] = Integer.parseInt(String.valueOf(address.charAt(3)));
    }
    return block;
  }
  
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  // Hint: be careful not to generate negative distances
  
  // This skeleton version generates a random distance
  // If you do not want to attempt this method, you may use this default code
  public static int getDistance(String from, String to)
  {
    // Fill in the code or use this default code below. If you use
    // the default code then you are not eligible for any marks for this part
    /*
     takes in two strings of addresses, uses getCityBlock method defined above to turn into
     int arrays and returns the distance between the two address, uses Math.abs() to prevent
     negative values
     */
    int [] c1Block = getCityBlock(from);
    int [] c2Block = getCityBlock(to);
    return(Math.abs(c1Block[0] - c2Block[0]) + Math.abs(c1Block[1] - c2Block[1]));
  }

  public static int getCityZone(String address){
    /*
   * gets city zone from an address string
   *
   * @param address = address to get city zone from
   * @return an int of the city zone, -1 if address not valid
  */
    if(!validAddress(address)){
      return -1;
    }
    int [] block = getCityBlock(address); //uses getCityBlock method to get street and avenue #

    if(block[0] <= 5){
      if(block[1] >= 6){
        return 0;         // returns 0 if in first to fifth avenue and 6th to 9th street
      }
      return 3;           // returns 3 if in 1st to 5th avenue and 1st to 5th street
    }
    if(block[1] >= 6){
      return 1;           // returns 1 if in 6th to 9th avenue and 6th to 9th street
    }
    return 2;             //returns 2 if in 6th to 9th avenue and 1st to 5th street
  }
}
