package project2;

//import the ArrayList class
import java.util.*;
import java.io.File;                      // Import the File class
import java.io.FileWriter;               // Import the FileWriter class
import java.io.IOException;             // Import the IOException class to handle errors
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;           // Import the class of Array List
import java.util.Scanner;            // Import the Scanner

public class PhoneBook {

	static ArrayList<People> peps = new ArrayList<>(); // Create an ArrayList object

	public static void main(String[] args) {
		PhoneBook phone = new PhoneBook();                     //Create new phone Type
		int operation;
		boolean askUser = true;
		Scanner input = new Scanner(System.in); // creating object for receiving input
		
		while (askUser) {
			
			System.out.println("1.Add contact\n2.Delete contact\n3.Print all contacts");
			System.out.println("4.Search contact(by name)\n5.Sorting the list by name\n6.Sorting the list by phone number");
			System.out.println("7.Delete duplicates\n8.Reverse the order of the list\n9.Save the contacts in a file");
			System.out.println("10.Load phonebook from file\n11.exit");
			
			System.out.println("Please choose what you would like to do by selecting the number of the operation:");
			
			operation = input.nextInt();    
			switch (operation) {
			case 1:
				phone.addNum(input);
				break;
			case 2:     
				phone.delNum(input);
				break;
			case 3:
				System.out.println(peps);
			    break;
			case 4:
				phone.searchCont(input);
				break;
			case 5:
				phone.sort(new People.NameComparator(), false);
				break;
			case 6:
				phone.sort(new People.NumComparator(), true);
				break;
			case 7:
				phone.removeDuplicates();
				break;
			case 8:
				phone.reversePhoneBook();
				break;
			case 9:
				phone.saveToFile(input);
				break;
			case 10:
				phone.saveFromFile(input);
				break;
			case 11:
				System.out.println("Closing the PhoneBook\n");
				askUser = false;
				return;
			default:
		        System.out.println("Invalid operator!");
		        break;
			}
		}
	}

	public void addNum (Scanner input) {
		
		System.out.println("Who do you want to add?");
		System.out.println("Please enter a name & a phone number");
		
		String s4 = input.nextLine();   // getting the enter
		String s1 = input.nextLine();   // getting name into the arraylist
		String s2 = input.nextLine();   // getting phone number into the arraylist
		People e = new People(s1,s2);
		for (int i = 0; i < peps.size(); i++) {
			if (peps.get(i).equals1(e)) {
				System.out.println("Cannot add a name that already exists in the phonebook");
				return;                                     
			}
		}
		peps.add(e);
		
	}

	public void delNum (Scanner input) {
		if (peps.isEmpty()==true) {
			System.out.println("The PhoneBook is empty");
			return;
		}
		System.out.println("Which contact do you wish to delete?");
		String s5 = input.nextLine();   // getting the enter
		String s3 = input.nextLine();   // getting a name to delete
		int numOfAcurr = 0;
		for (People j : peps) {
			if (j.name.equals(s3)) {
				
				numOfAcurr++;             //so we know we found him
				peps.remove(j);           //deleting the contact
				SMSApp.deleteConvoPB(SMSApp.msgs, j.name);   //deleting his correspondence if he had one
				Schedule.delContact(j); // DELETE THE EVENT IF THE CONTACT DELETED
				
				if (peps.isEmpty()==true){
				   System.out.println("The PhoneBook is empty");
				 
				}
				 break;
			}
			 
		}
		if (numOfAcurr == 0) {
			System.out.println("Contact not found.");
		}
		
	}
	
	public void searchCont (Scanner input){  
		if (peps.isEmpty()==true){
			System.out.println("The PhoneBook is empty");
			return;
		}
		System.out.println("Which contact do you wish to search?");
		
		String s1 = input.nextLine();   // getting the enter
		String s2 = input.nextLine();   // getting the name to print
		int numOfAcurr = 0;
		for (People j : peps) {
			
			if (j.name.equals(s2)) {
				System.out.println(j.toString());
				numOfAcurr ++;
				
			}
		}
		if (numOfAcurr == 0) {
			System.out.println("Contact not found.");//in case the contact is not in the phone book
		}
	}

	public void sort(Comparator<People> comp, boolean reverse) {
		if (peps.isEmpty()==true){
			System.out.println("The PhoneBook is empty");
			return;
		}
		// Collections.sort method is sorting the
        //elements of ArrayList in ascending order
		
		// reverse = false -- from little to big,
		// reverse = true  -- from big to little
		if (reverse) {
			Collections.sort(peps, Collections.reverseOrder(comp));  //reverseOrder = from big to little
		}
		else {
			Collections.sort(peps, comp);              //sort from little to big
		}
	}

	// Removes duplicates by sorting the array (both by name and number),
	//  and then removing adjacent instances that are equal
	private void removeDuplicates() {
		if (peps.isEmpty()==true){
			System.out.println("The PhoneBook is empty");
			return;
		}
		// Create a new array for temporarily sorting the phone contacts
		List<People> tmp = new ArrayList<>(peps);

		// Stable sort the copy of the contacts list, both by name and number
		sort(new People.NumComparator(), false);
		tmp = new ArrayList<>(peps);
		sort(new People.NameComparator(), false);

		// Compare for equality each adjacent contact
		for (int i = 1; i < peps.size(); i++) {
			if (peps.get(i).equals(peps.get(i - 1))) {
				peps.remove(i);
				i--;
			}
		}
	}
	
	public void reversePhoneBook() {
		if (peps.isEmpty()==true){
			System.out.println("The PhoneBook is empty");
			return;
		}
        for (int i = 0; i < peps.size() / 2; i++) {
            People temp = peps.get(i);
            peps.set(i, peps.get(peps.size() - i - 1));      //Swap the edges and get closer to the center 
            peps.set(peps.size() - i - 1, temp);             
        }
    }
	
	public void saveToFile(Scanner input) {
		if (peps.isEmpty()==true){
			System.out.println("The PhoneBook is empty");
			return;
		}
		System.out.println("to save phone book to file , enter your file name with .txt");
		String s6 = input.nextLine();   // getting the enter
		String nameF = input.nextLine();   // getting name into the arraylist
		try {
			File myObj = new File(nameF);
			if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		    }
		try {
		      FileWriter myWriter = new FileWriter(nameF);
		      for(People p : peps) {
		    	  myWriter.write(p.name+"\n"+p.num);
		    	  myWriter.write("\n");
		      }
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		    }
	}
	
	public void saveFromFile(Scanner input) {
		System.out.println("Insert the file name you want to get data from .txt format");
		String s7 = input.nextLine();   // getting the enter
		String nameF = input.nextLine();   // getting name into the arraylist
		try {
		      File myObj = new File(nameF);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String nName = myReader.nextLine();
		        String nNum = myReader.nextLine();
		        People nPep = new People(nName , nNum);
		        peps.add(nPep);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("File not found.");
	}
}

}
