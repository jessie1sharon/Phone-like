package project2;
//Group number 3
//Margarita kaplan 321775579
//Jessica Llanos 327083184
//Yarden Fahima 318647211
//Shahar Ganani 208983247
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


abstract class Conversation {                  //abstract class for the conversation format
	String convo;
	public Conversation (String convo) {	
		this.convo = convo;
	}
	
    public void continueConvo(String replay) {
		this.convo = this.convo + replay ;
	}
	
	public abstract String getConvo();
} 

public class SMSApp extends Conversation {
	
	static ArrayList<SMSApp> msgs = new ArrayList<>();
	People contact; 
	
	public SMSApp(People contact,String convo) {                 //creator for SMSApp type
		super(convo) ;
		this.contact = contact;
	}
	
	public String getName() {
		return this.contact.getName();
	}
	
	public String getConvo() {
		return this.convo;
	}

	public String toString() {
			return "Contact :" + contact + "\nThe Correspondence :\n" + convo;
		}
	 
	public static void main(String[] args) { 
		Scanner input = new Scanner(System.in);
		int operation;
		boolean inApp = true;
		ArrayList<People> contacts = PhoneBook.peps;       //the contacts from that in the PhoneBook 	
		
		do {                                                  //the menu of the SMS app 
			System.out.println("What do you wish to do?");
			System.out.println("1.Add correspondence with a contact "); 
			System.out.println("2.Delete correspondence with a spesific contact "); 
			System.out.println("3.Print correspondence with a spesific contact "); 
			System.out.println("4.Find a sentence in all the correspondences"); 
			System.out.println("5.Print all the correspondences"); 
			System.out.println("6.exit");
			System.out.println("Please choose an action by pressing the number of the operation");
			
			operation = input.nextInt();
			
			switch(operation) {
			case 1:
				addConvo(input , msgs , contacts);
				break;
			case 2:
				deleteConvo(input, msgs);
				break;
			case 3:
				printConvo(input, msgs);
				break;
			case 4:
				findSentence(input, msgs);
				break;
			case 5:
				printAllConvos(msgs);
				break;
			case 6:
				System.out.println("Closing the SMS app\n");
				inApp = false;
				return;
			default:
		        System.out.println("Invalid operator!");
		        break;
			}
				
		}while(inApp);
	
	}

	private static void findSentence(Scanner input, ArrayList<SMSApp> msgs) {
		if (msgs.isEmpty()==true){
			System.out.println("No correspondences exists");
			return;
		}
		boolean found = false;
		System.out.println("Enter sentence you want to find ");
		String s = input.nextLine();  
		String s2 = input.nextLine();                   // getting the sentence 
		Iterator<SMSApp> it = msgs.iterator();
		while (it.hasNext()) {
			SMSApp curr = it.next();
			if(curr.convo.contains(s2)) {   //check if the entered sentence is in the convo of any contact
				System.out.println("appears in correspondence with "+ curr.getName());
				found = true;
			}
		}
		if(!found) {                       //if the sentence wasn't found
			System.out.println("Sentence isn't in any correspondence");
		}
		
	}

	public static String printAllConvos(ArrayList<SMSApp> msgs) {
		
		String r=" ";
		
		if (msgs.isEmpty()==true){
			System.out.println("No correspondences exists");
			return r;
		}
		for (SMSApp i : msgs) {                   //print all correspondences
				System.out.println(i);
		}
		return r;
	}

	public static void printConvo(Scanner input, ArrayList<SMSApp> msgs) {
		if (msgs.isEmpty()==true){
			System.out.println("No correspondences exists");
			return;
		}
		System.out.println("Who you want to preview the correspondence with? \nEnter his/her name");
		String s = input.nextLine();  
		String s2 = input.nextLine();   // getting the name 
		Iterator<SMSApp> it = msgs.iterator();
		while (it.hasNext()) {
			SMSApp curr = it.next();
			if(curr.contact.name.equals(s2))//finding the name the user asked for and print the correspondence
			{
				System.out.println(curr);
				return;
			}
		}
		System.out.println("No such correspondence");//if the contact doesnt have a correspondence
		return;
	}

	public static void deleteConvo(Scanner input, ArrayList<SMSApp> msgs) {
		if (msgs.isEmpty()==true){
			System.out.println("No correspondences exists");
			return;
		}
		System.out.println("Who you want to delete the correspondence with? \nEnter his/her name");
		String s1 = input.nextLine();   // getting the enter
		String s2 = input.nextLine();   // getting the name 
		Iterator<SMSApp> it = msgs.iterator();
		while (it.hasNext()) {
			SMSApp curr = it.next();
			if(curr.contact.name.equals(s2))
			{
				msgs.remove(curr);//remove the correspondence with the named contact
				System.out.println("Correspondence with " + s2 +" deleted");
				return;
			}
		}
		System.out.println("No such correspondence");//if the contact doesnt have a correspondence
		return;
	}
	
	public static void deleteConvoPB(ArrayList<SMSApp> msgs , String name) {
		if (msgs.isEmpty()==true){
			return;
		}
		Iterator<SMSApp> it = msgs.iterator();
		while (it.hasNext()) {
			SMSApp curr = it.next();
			if(curr.contact.name.equals(name))
			{
				msgs.remove(curr);//remove the correspondence with the named contact
				return;
			}
		}
	}
	
    public static void addConvo(Scanner input, ArrayList<SMSApp> msgs, ArrayList<People> contacts) {
		System.out.println("Who you want to send a message? \nEnter his/her name please");
		String s1 = input.nextLine();                     // getting the enter
		String s2 = input.nextLine();                     // getting the name 
		boolean found = false,more = false, hadMsg = false;
		//found - contact in PhoneBook , more- add more than 1 msg 
		//hadMsg - if there is already a corressponding with the contact 
		
		People contact1 = null ;
		Iterator<People> it_c = contacts.iterator();
		while (it_c.hasNext()) {
			People curr_c = it_c.next();
			if (curr_c.name.equals(s2)) {
				contact1 = curr_c;
				found = true; 
			}
		}
		if(found) {
			Iterator<SMSApp> it = msgs.iterator();
			while (it.hasNext()) {
				SMSApp curr = it.next();
			if(curr.contact.name.equals(s2))
			{	
				hadMsg = true;
				do {
				System.out.println("what you want to write to " + s2 );
				String s3 = input.nextLine();   // getting the entery
				curr.continueConvo(s3 + "\n");
				System.out.println("do you want to add more?Yes/No");
				String s4 = input.nextLine();
				if (s4.equals("Yes")) {
					more = true;
				}
				if(s4.equals("No")) {
					more = false;
				}
				}while(more);
				System.out.println("done messaging with "+s2);
				return;
			}
		}
			if(!hadMsg) {                                //contact exists but no conversation with it yet
				SMSApp msg = new SMSApp(contact1,"");
				do {
				System.out.println("what you want to write to " + s2 );
				String s3 = input.nextLine();   // getting the enrty 
				msg.continueConvo(s3 + "\n");
				System.out.println("do you want to add more?Yes/No");
				String s4 = input.nextLine();
				if (s4.equals("Yes")) {
					more = true;
				}
				if(s4.equals("No")) {
					more = false;
				}
				}while(more);
				msgs.add(msg);
				System.out.println("done messaging with "+s2);
				return;
			}
			
			
	}else {
		System.out.println("No such contact in PhoneBook");
		return;}
	
}	

}
