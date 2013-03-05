package server;

import java.io.*;
import java.net.*;
import org.apache.commons.cli.*;


public class ServerClass {
	
	
	public static void main(String[] args){
		
		//Option portopt = OptionBuilder.withArgName("p").hasArg().withDescription("use flag to set the server port").create("p");
		Options options = new Options();
		options.addOption("p","port",true,"flag for adding port number");
		
		CommandLineParser parser = new BasicParser();
		
		int port=4444;
		
		try {
			CommandLine cmd = parser.parse( options, args);
			if(cmd.hasOption("p")){
				port=Integer.parseInt(cmd.getOptionValue("p"));
			}
		} catch (ParseException e) {
			System.out.println(e);
		}
		
		ServerSocket ss=null;
		Socket s=null;
		BufferedReader in=null;
		DataOutputStream out=null;
	
		try{
			ss = new ServerSocket(port);
			System.out.println("Server Socket made");
			System.out.println("Port number: "+port);
		}catch(IOException e){
			System.out.println("Could not make server.");
			System.out.println(e);
		}
		try{
			s = ss.accept();
			System.out.println("Plain old Socket made");
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
			String sig="waiting for message from socket...";
			while((sig = in.readLine())!=null){
				System.out.println("Recieved: "+sig);
				out.writeChars(sig.trim()+"\n");
				if(sig=="bye"){
					break;
				}
			}
		}catch(IOException e){
			System.out.println(e);
		}
		try{
		in.close();
		out.close();
		s.close();
		ss.close();
		System.out.println("Everything closed up... Time to go home.");
		}catch(IOException e){
			System.out.println(e);
		}
	}
}
