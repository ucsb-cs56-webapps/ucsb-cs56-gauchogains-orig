package edu.ucsb.cs56.pconrad;

import static spark.Spark.port;
import com.google.firebase.*;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
/**
 * Hello world!
 *
 */

public class FireBaseGains {
    public static void main(String[] args) {
	port(getHerokuAssignedPort());
	System.out.println("beginning");
	initializeFireBase();
	System.out.println("end");


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
	System.out.println("endapp");
	}
	
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    static void initializeFireBase() {
		try {	
			System.out.println("1");
			FileInputStream serviceAccount = new FileInputStream("target/classes/firebase-credentials.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
    				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
    				.setDatabaseUrl("https://gauchogains-f67f0.firebaseio.com")
    				.build();
			System.out.println("2");
			FirebaseApp.initializeApp(options);
			System.out.println("3");
		}	
		catch (FileNotFoundException e) {
			System.out.println("4");
			spark.Spark.get("/",(req,res) -> "FileNotFound");
		}
		catch (IOException e) {
			System.out.println("5");
			spark.Spark.get("/",(req,res) -> "serviceAccount invalid");
		}	
	}
}
