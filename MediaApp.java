package project2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MediaApp {
	
	String name;
	int length;
	static ArrayList<MediaApp> medias = new ArrayList<>(); // Create an ArrayList object
	
	public MediaApp (String name, int length){
		this.name = name;                                   //the constructor
		this.length = length;
	}
	
	public static String play(Scanner input, Boolean doAll, ArrayList<MediaApp> medias) {
		
		String r = " ";
		if (medias.isEmpty()==true){                           //if there isn't any media
			System.out.println("The list of medias is empty");
			return r;
		}
		
		if (doAll == true) {                    //if we need to print all the medias
			Iterator<MediaApp> it = medias.iterator();
			while (it.hasNext()) {
				MediaApp curr = it.next();
				
				if(curr instanceof Song) {
 				    ((Song) curr).play();                //if the media we want to play is a song
 				}
 				else {
    				((Video) curr).play();                //if the media we want to play is a video
 				}
          }
       }
		
       else if (doAll == false) {              //if we need to print only one media
   		  System.out.println("What is the name of the media you want to play?");
   		  String name1 = input.nextLine();       //getting the enter
   		  String name2 = input.nextLine();       //getting the name of the media
   		  int numOfAcurr = 0;
   		  
   		  Iterator<MediaApp> it = medias.iterator();
   		  while (it.hasNext()) {                             //searching for the media
   			  MediaApp curr = it.next();
			
   			  if (curr.name.equals(name2)) {                 //if we found the media
   				numOfAcurr ++;
   				
   				if(curr instanceof Song) {
   				  ((Song) curr).play();                //if the media we want to play is a song
   				  break;                            //play just one song and don't continue
   				}
   				else {
      		      ((Video) curr).play();                //if the media we want to play is a video
      			  break;                             //play just one video and don't continue
   				}
   			  }
   		  }
   		  if (numOfAcurr == 0) {                 //we didn't find any media by that name
   			  System.out.println("There is no media by that name.");
   		  }

       }
		return r;
	}
	
	public void AddMedia(Scanner input, ArrayList<MediaApp> medias) {
		System.out.println("Which media do you want to add?\n");

		System.out.println("Press 1 to add a song or press 2 to add a video");

		int choise = input.nextInt();
		if (choise == 1) {                          //if we are adding a song
			
          System.out.println("What is the name of the song?");
          System.out.println("What is the duration of the song?(in minutes)");
          String s1 = input.nextLine();            //getting the enter
          String s2 = input.nextLine();            //getting the name of the song
          int s3 = input.nextInt();                //getting the duration of the song
          MediaApp song1 = new Song(s2,s3);        //creating a new song 
          medias.add(song1);                       //adding the song to the arraylist medias
		}
		
		else if (choise == 2) {                          //if we are adding a video
			
	          System.out.println("What is the name of the video?");
	          System.out.println("What is the duration of the video?(in minutes)");
	          String s1 = input.nextLine();            //getting the enter
	          String s2 = input.nextLine();            //getting the name of the video
	          int s3 = input.nextInt();                //getting the duration of the video
	          MediaApp video1 = new Video(s2,s3);        //creating a new video
	          medias.add(video1);                       //adding the video to the arraylist medias
			}
	}
	
	public static void main(String[] args) {
		MediaApp media = new MediaApp(null,0);       // creating a new object
	
		Boolean doing = true;
		int operation;                                   //for saving the chosen operation
		Scanner input = new Scanner(System.in); // creating object for receiving input
		
		while(doing) {
		System.out.println("What do you wish to do?");
		System.out.println("1.Add media\n2.Play media by name\n3.Play all media\n4.exit\n");
		System.out.println("Please choose an action by pressing the number of the operation");
		
		operation = input.nextInt();    
		switch (operation) {
		case 1:
			media.AddMedia(input,medias);
			break;
		
		case 2:
			play(input, false, medias);          //play just one media - so send false 
			break;
			
		case 3:
			play(input, true, medias);          //sending true so we print all medias
			break;
			
		case 4:
			System.out.println("Closing the Media app\n");    //exit the app
			doing = false;
			return;
			
		default:
	        System.out.println("Invalid operator!");
	        break;
		}
		
		}
	}
	
}

class Song extends MediaApp {                            // there is a specific phrase for playing a song
	public Song(String name, int length) {
		super(name, length);
	}
	
	public void play(){
	    //play the song...
		System.out.println("The song " + name + " is now playing for " + length + " minutes");
	}
}

class Video extends MediaApp {                           // there is a specific phrase for playing a video
	public Video(String name, int length) {
		super(name, length);
	}
	
	public void play(){
	    // play the video...
		System.out.println("The video " + name + " is now playing for " + length + " minutes");
	}
}
