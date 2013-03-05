package hello;

//import org.apache.commons.*;
import java.io.*;
import java.net.*;
import org.apache.commons.cli.*;

public class optiontest {
	
	public static void main(String[] args){
		
		Options options = new Options();
		options.addOption("t", false, "first option! will echo that it's on.");
		
		CommandLineParser parser = new BasicParser();
		
		try {
			CommandLine cmd = parser.parse( options, args);
			
			if(cmd.hasOption("t")) {
			    // print the date and time
				System.out.println("FLAGGED!");
			}
			else {
			    // print the date
				System.out.println("NOT FLAGGED!");
			}
		} catch (ParseException e) {
			System.out.println(e);
		}
	
	}

}
