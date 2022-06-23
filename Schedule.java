package project2;
//Group number 3
//Margarita kaplan 321775579
//Jessica Llanos 327083184
//Yarden Fahima 318647211
//Shahar Ganani 208983247
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.util.*;

public class Schedule {

	public static final int YEAR = 2021;
    public static final int MONTH = 4;                 //we choose the month April 
    public static final int DATE_YEAR = YEAR - 1900;
    public static final int DATE_MONTH = MONTH - 1;
    public static final int DAYS = 30;

    static ArrayList<Event>[] events = new ArrayList[DAYS]; // Create an ArrayList object with 30 days
	static {
		for (int i = 0; i < DAYS; i++) {
			events[i] = new ArrayList<>();          
		}                                      //we create array of 30 days,and inside there are arraylists of Events 
	}

    public static void main(String[] args) {

        Schedule schedule = new Schedule();         
        int operation;

        ArrayList<People> contacts = PhoneBook.peps;//the contacts from that in the PhoneBook

        boolean askUser = true;
		System.out.println("Welcome to the Schedule,what would you like to do?");

        Scanner input = new Scanner(System.in); // creating object for receiving input
        while (askUser) {
			System.out.println("1.Add event\n2.Delete event");
			System.out.println("3.Print all the events in a specific day");
			System.out.println("4.Print all the events with a specific person");
			System.out.println("5.Cheack for overlapping events");
			System.out.println("6.Print all the events");
			System.out.println("7.Exit\n");

			int day;
			
            operation = input.nextInt();    //geting the input 
            String s4 = input.nextLine();   //saving the enter
            switch (operation) {
                case 1:                            //Add event
                    schedule.addEvent(input, contacts);
                    break;
                case 2:
					schedule.delEventHelperFunc(input, contacts);
                    break;
                case 3:
                	System.out.println("Please enter a number of day you want to print 1-30:");
                    try {
                    	
                    	day = Integer.parseInt(input.nextLine());
					} catch (Exception e) {
                    	System.out.println("The day is not an integer");
                    	break;
					}
                    if (day < 1 || day > DAYS) {
                    	System.out.println("The day is not legal");
                    	break;
					}
                    if (schedule.printDayEvents(day) == false) {
                    	System.out.println("No events were found on day " + day);
					}
                    break;
                case 4:
                    schedule.printContactEvents(input, contacts);
                    break;
                case 5:
                	System.out.println("Deleting any overlapping events\n");
                	schedule.deleteOverlappingEvents();
                    break;
                case 6:
                    printAllEvents();
                    break;
                case 7:
                    System.out.println("Closing the Schedule App");
                    askUser = false;
                    return;

                default:
                    System.out.println("Invalid operator!");
                    break;
            }
        }
    }

    public void addEvent(Scanner input, List<People> contacts) {

        System.out.println("Which Event do you want to add?");
        System.out.println("1.Event with meeting");
        System.out.println("2.Event without meeting");
        System.out.println("Please choose the type you want");

        String s1 = input.nextLine();   // getting name into the arraylist
        if (s1.equals("1")) {

            System.out.println("you choose Event with meeting");

            Event basicEvent;   //create new event 
            try {
            	basicEvent = addBasicEvent(input);
			} catch(Exception e) {     //if the input is incorrect 
            	return;
			}

            System.out.println("Enter a contact's name:");
            String contactName = input.nextLine();
			People contact = null;
			for (People i : contacts) {    //checking if there are such name in the lis
				if (i.name.equals(contactName)) {
					contact = i;
				}
			}
			if (contact == null) {
				System.out.println("There is no contact with that name: \"" + contactName + "\"");
				return;
			}

			Event event = new EventWithP(basicEvent, contact);
			int day = basicEvent.getDate().getDate();
			events[day - 1].add(event);
			events[day - 1].sort(new DateComparator());
        }

        else if (s1.equals("2")) {

            System.out.println("you choose Event without meeting");

			Event basicEvent;
			try {
				basicEvent = addBasicEvent(input);
			} catch(Exception e) {
				return;
			}

			System.out.println("Enter a description:");
			String description = input.nextLine();

			Event event = new EventWithoutP(basicEvent, description);
			int day = basicEvent.getDate().getDate();
			events[day - 1].add(event);
			events[day - 1].sort(new DateComparator());
        } else {
            System.out.println("error input");
            return;
        }

    }

	public void delEventHelperFunc(Scanner input, List<People> contacts) {

		System.out.println("Which Event do you want to delete?");
		System.out.println("1.Event with meeting");
		System.out.println("2.Event without meeting");
		System.out.println("Please choose the type you want to delete");

		String s1 = input.nextLine();   // getting name into the arraylist
		if (s1.equals("1")) {

			System.out.println("you choose to delete Event with meeting");

			Event basicEvent;
			try {
				basicEvent = addBasicEvent(input);
			} catch(Exception e) {
				return;
			}

			System.out.println("Enter a contact's name to delete his event:");
			String contactName = input.nextLine();
			People contact = null;
			for (People i : contacts) {    //checking if there are such name in the lis
				if (i.name.equals(contactName)) {
					contact = i;
				}
			}
			if (contact == null) {
				System.out.println("There is no contact with that name: \"" + contactName + "\"");
				return;
			}

			Event event = new EventWithP(basicEvent, contact);
			if (!delEvent(event)) {
				System.out.println("Couldn't find the event to delete");
				return;
			}
		}

		else if (s1.equals("2")) {

			System.out.println("you choose to delete Event without meeting");

			Event basicEvent;
			try {
				basicEvent = addBasicEvent(input);
			} catch(Exception e) {
				return;
			}

			System.out.println("Enter a description for the event you want to delete:");
			String description = input.nextLine();

			Event event = new EventWithoutP(basicEvent, description);
			if (!delEvent(event)) {
				System.out.println("Couldn't find the event to delete");
				return;
			}
		} else {
			System.out.println("error input");
			return;
		}

	}

    private Event addBasicEvent(Scanner input) throws Exception { //help function for the addevent
		System.out.println("Enter a day from 1-30:");
		int day;
		try {
			day = Integer.parseInt(input.nextLine());
		} catch(Exception e) {
			System.out.println("Invalid day");
			throw e;
		}
		if (day < 1 || day > DAYS) {
			System.out.println("Invalid day");
			throw new Exception();
		}
		System.out.println("Enter the start time for the event in this format XX:XX");
		String time;
		time = input.nextLine();
		String[] timeArray = time.split(":"); //we want to know the time without the ":"
		if (timeArray.length != 2) {
			System.out.println("The time is not in the correct format");
			throw new Exception();
		}
		int hour;
		try {
			hour = Integer.parseInt(timeArray[0]);
		} catch(Exception e) {
			System.out.println("The hour is not an integer");
			throw e;
		}
		if (hour < 0 || hour > 23) {
			System.out.println("The hour is not legal");
			throw new Exception();
		}
		int minute;
		try {
			minute = Integer.parseInt(timeArray[1]);
		} catch(Exception e) {
			System.out.println("The minutes are not an integer");
			throw e;
		}
		if (hour < 0 || hour > 59) {
			System.out.println("The minutes are not legal");
			throw new Exception();
		}
		Date date = new Date(DATE_YEAR, DATE_MONTH, day, hour, minute); //initialize the date

		System.out.println("Enter the event length in minutes 1-60:");
		int lengthInMinutes;
		try {
			lengthInMinutes = Integer.parseInt(input.nextLine());
		} catch (Exception e) {
			System.out.println("The length in minutes is not an integer");
			throw e;
		}
		if (lengthInMinutes < 1 || lengthInMinutes > 60) {
			System.out.println("The length in minutes is not legal");
			throw new Exception();
		}

		return new Event(lengthInMinutes, date);
    }

    public boolean delEvent(Event event) {
		int day = event.getDate().getDate(); // Returns the day of the month from the event
		return events[day - 1].remove(event);
    }

    public static void delContact(People contact) { //running on all the events and deleting the contact we were given
		for (int day = 1; day <= DAYS; day++) {
			List<Event> dayEvents = events[day - 1];
			for (int i = 0; i < dayEvents.size(); i++) {
				Event event = dayEvents.get(i);
				if (event instanceof EventWithP) {
					EventWithP eventP = (EventWithP) event;
					if (eventP.someone.equals(contact)) {
						dayEvents.remove(i);
						i--;
					}
				}
			}
		}
	}

	public static class DateComparator implements Comparator<Event> {//compare 2 events
		@Override
		public int compare(Event lhsEvent, Event rhsEvent) {
			Date lhs = lhsEvent.getDate();
			Date rhs = rhsEvent.getDate();
			if (lhs.before(rhs)) {
				return -1;  //if the first events is before the second 
			}
			if (lhs.after(rhs)) {
				return 1;  //if the first events is after the second 
			}
			return 0;     //if they are in the same time
		}
	}

	public static boolean printDayEvents(int day) {
    	if (day < 1 || day > DAYS) {
    		return false;
		}
    	boolean eventsFound = false;
    	for (Event event : events[day - 1]) {
    		eventsFound = true;
    		System.out.println(event);
		}
    	return eventsFound;
	}
	
    public void printContactEvents(Scanner input, List<People> contacts) { //print all the events of a contact
		System.out.println("Please enter a contact's name to print his events:");
		String name = input.nextLine();

		People contact = null;
		for (People i : contacts) {
			if (i.getName().equals(name)) {
				contact = i;
				break;
			}
		}
		if (contact == null) {
			System.out.println("This contact does not exist in the phone book");
			return;
		}

		for (int day = 1; day <= DAYS; day++) {
			for (Event event : events[day - 1]) {
				if (event instanceof EventWithP) {
					EventWithP eventP = (EventWithP) event;
					if (eventP.someone.equals(contact)) {
						System.out.println(event);
					}
				}
			}
		}
	}

    public static String printAllEvents() {
		boolean eventsFound = false;
		for (int day = 1; day <= DAYS; day++) {
			if (printDayEvents(day)) {
				eventsFound = true;
			}
		}
		if (eventsFound == false) {
			System.out.println("No events were found");
		}
		return "";
	}

	public void deleteOverlappingEvents() {
		for (int day = 1; day <= DAYS; day++) {
			List<Event> dayEvents = events[day - 1];
			for (int i = 0; i < dayEvents.size() - 1; i++) {
				Event previous = dayEvents.get(i);
				Event next = dayEvents.get(i + 1);
				if (isOverlappingOrdered(previous, next)) {
					dayEvents.remove(i + 1);
					i--; // Stay on this event, until no overlaps are found
				}
			}
		}
	}

	public boolean isOverlapping(Event event1, Event event2) {
		return isOverlappingOrdered(event1, event2) || isOverlappingOrdered(event2, event1);
	}

	private boolean isOverlappingOrdered(Event previous, Event next) {
		Date previousStart = previous.getDate();
		Calendar previousCal = Calendar.getInstance();
		previousCal.setTime(previousStart);
		previousCal.add(Calendar.MINUTE, previous.getTime());
		Date previousEnd = previousCal.getTime();
		Date nextStart = next.getDate();
		return !previousStart.after(nextStart) && nextStart.before(previousEnd);
	}

}
	