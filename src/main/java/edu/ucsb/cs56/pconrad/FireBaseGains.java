package edu.ucsb.cs56.pconrad;

import static spark.Spark.port;
import com.google.firebase.*;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
/**
 * Hello world!
 *
 */

public class FireBaseGains {
    public static void main(String[] args) {
	
	getHerokuAssignedPort();

	String strtemp = getKeyString();
	strtemp = massageWhitespace(strtemp);
	
	initializeFireBase(strtemp);

	System.out.println("");
	System.out.println("(Don't worry about the warnings below about SLF4J... we'll deal with those later)");
	System.out.println("");						  
	System.out.println("In browser, visit: http://localhost:" + getHerokuAssignedPort() + "/hello");
	System.out.println("");
        
	spark.Spark.get("/", (req, res) -> "Hello World");
		spark.Spark.get("/hello", (req, res) -> "Hello World");
		spark.Spark.get("/bye", (req, res) -> "Goodbye World");
		spark.Spark.get("/yo", (req, res) -> "S'up World");
		spark.Spark.get("/tension", (req, res) -> "Midterm next week.  No problem dude");		
	
	}
	
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    static void initializeFireBase(String serviceAccount) {
		try {	
			//FileInputStream serviceAccount = new FileInputStream("src/resources/firebase-credentials.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
    				.setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(serviceAccount.getBytes())))
    				.setDatabaseUrl("https://gauchogains-f67f0.firebaseio.com")
    				.build();
			FirebaseApp.initializeApp(options);
		}	
		catch (FileNotFoundException e) {
			spark.Spark.get("/",(req,res) -> "FileNotFound");
		}
		catch (IOException e) {
			spark.Spark.get("/",(req,res) -> "serviceAccount invalid");
		}	
	}
    private static String massageWhitespace(String s) {
	    String newString = "";
	    for (Character c : s.toCharArray()) {
		    if ("00a0".equals(Integer.toHexString(c | 0x10000).substring(1))) {
			    newString += " ";
		    } else {
			    newString += c;
		    }
	    }
	    
	    return newString;
    }   

   static String getKeyString() {
	   try {
		   FileInputStream serviceAccount = new FileInputStream("src/resources/firebase-credentials.json");
		   String inputStreamString = new Scanner(serviceAccount, "UTF-8").useDelimiter("\\A").next();
		   return inputStreamString;
	   }
	   catch (FileNotFoundException e) {
	   	return "fail";
	   }
   }
}
