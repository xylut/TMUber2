//alex clouthier 501249820
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.HashMap;

public class TMUberRegistered
{
    
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(int currentSize)
    {   
        return "" + firstUserAccountID + currentSize;
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    public static ArrayList<User> loadPreregisteredUsers(String filename) throws FileNotFoundException, NoSuchElementException, NumberFormatException{
        ArrayList<User> users = new ArrayList<>();
        Scanner file = new Scanner(new File(filename));
        while(file.hasNextLine()){
            try{
            String id = generateUserAccountId(users.size());
            User user = new User(id, file.nextLine(), file.nextLine(), Double.parseDouble(file.nextLine()));
            users.add(user);  
            }
            finally{}
        }
        file.close();
        return users;

    }
    public static ArrayList<Driver> loadPreregisteredDrivers(String filename) throws FileNotFoundException, NoSuchElementException{
        ArrayList<Driver> drivers = new ArrayList<>();
        Scanner file = new Scanner(new File(filename));
        while(file.hasNextLine()){
            try{
            String id = generateDriverId(drivers);
            Driver driver = new Driver(id, file.nextLine(), file.nextLine(), file.nextLine(), file.nextLine());
            drivers.add(driver);  
            }
            finally{}
        }
        file.close();
        return drivers;

    }
}

