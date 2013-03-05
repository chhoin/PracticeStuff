package client;
import java.io.*;
import java.net.*;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ClientClass {

	public static void main(String[] args){

		Socket s=null;
		BufferedReader in=null;
		DataOutputStream out=null;
		
		Options options = new Options();
		options.addOption("h","host",true,"Host name. Default is localhost");
		options.addOption("p","port",true,"Host port. Default is 4444");
		
		CommandLineParser parser = new BasicParser();
		
		int port=4444;
		String host="localhost";
		
		try {
			CommandLine cmd = parser.parse( options, args);
			if(cmd.hasOption("p")){
				port=Integer.parseInt(cmd.getOptionValue("p"));
			}
		} catch (ParseException e) {
			System.out.println(e);
		}
		
		try{
			s = new Socket("localhost",port);
			System.out.println("Plain old Socket made...but connected now!");
			System.out.println("Host: "+host);
			System.out.println("Port: "+port);
		}catch(IOException e){
			System.out.println(e);
		}
			
		try{
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			System.out.println("Reading input from Plain old Socket");
			out = new DataOutputStream(s.getOutputStream());
			System.out.println("(Ready to be) Writing output to Plain old Socket");
		}catch(IOException e){
			System.out.println(e);
		}
		try{
			
			String[] sigs = {"Hello","Ping","Pong","This","That","Good","bye"};
			System.out.println("made string array to send");
			String returned="not back...";
			for(int i = 0;i<sigs.length;i++){
				out.writeChars(sigs[i]+"\n");
				System.out.println(sigs[i]+" sent");
				while((returned = in.readLine())!=null){
					
					//System.out.println(sigs[i]+"  "+returned);
					String clipped = "";
					for(int j = 0; j<returned.length();j++){
						char ch = returned.charAt(j);
						if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
							clipped=clipped+ch;
						}
					}
					System.out.println("Got message back: "+clipped);
					//System.out.println("original length: "+sigs[i].length()+" returned length: "+clipped.length());
					//System.out.println("Equal? "+(returned.trim()).substring(0, sigs[i].length()).equals(sigs[i].trim()));
					//System.out.println("Equal? "+clipped.equals(sigs[i]));
					if(clipped.equals(sigs[i])){
						System.out.println("  Moving on...");
						break;
					}
				}
			}
		}catch(IOException e){
			System.out.println(e);
		}
		try{
			in.close();
			out.close();
			s.close();
			System.out.println("Everything closed up... Time to go home.");
		}catch(IOException e){
			System.out.println(e);
		}
	}
}

