package project2;

//import the ArrayList class
import java.util.Scanner;            // Import the Scanner

public class Phone {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in); // creating object for receiving input
		int choose;
		
		do {
		System.out.println("Hello ! This is your phone\n");   //starting the phone
		System.out.println("You have 3 options:");
		System.out.println("1.Go to the Application Manager");
		System.out.println("2.Print all the content of the applications");
		System.out.println("3.Turn off the phone");
		System.out.println("What do you want to do?");
		
		choose = input.nextInt(); 
		switch (choose) {
		case 1:
			Applications(input, args);     //go to the function that manages the applications
			break;
			
		case 2:                            //calling the print func of all the applications
			System.out.println(PhoneBook.peps);
			System.out.println(SMSApp.printAllConvos(SMSApp.msgs));
			System.out.println(Schedule.printAllEvents());//print schedule
			System.out.println(MediaApp.play(input ,true ,MediaApp.medias));
			break;
			
		case 3:
			System.out.println("Turning off the phone , goodbey!");
			input.close();
			return;
			
		default:
	        System.out.println("Invalid operator!");
	        break;
	
		}
		
		} while(true);
	}
	
	public static void Applications(Scanner input,String[] args) {
		
		String choise;              //which application will choose
		String s;                   // to get the enter
		Boolean askUser = true;     //so we can enter the loop
		Boolean e = true;          //so we will check if we need to save the enter
		
		while (askUser) {
			
			System.out.println("\nYou arrived to your Application Manager\n");
			System.out.println("You have 4 applications on your phone");
			System.out.println("PhoneBook\nSMS\nSchedule\nMedia");
			System.out.println("Which application do you want to enter?");
			
			askUser = false;                   //if the choise is valid then we wont enter again
			if(e) {s = input.nextLine(); } 
			choise = input.nextLine();         //Receiving the choise
			
			switch (choise) {
			case "PhoneBook":
				PhoneBook.main(args);          //going the the phone book
				break;
				
			case "SMS":                         //going the the SMS
				SMSApp.main(args);
				break;
				
			case "Schedule":                    //going the the Schedule
				Schedule.main(args);
				break;
				
			case "Media":                      //going the the Media app      
				MediaApp.main(args);
				break;
				
			default:
				askUser = true;
				e = false;
		        System.out.println("Invalid operator!");
		        break;
			}
		}
	}
}
