package project2;
//Group number 3
//Margarita kaplan 321775579
//Jessica Llanos 327083184
//Yarden Fahima 318647211
//Shahar Ganani 208983247
import java.util.Date;

public class Event{
    
	Date d1 = new Date() ;
	int time;
	
	public Event(int time,Date d1) {
        
		this.d1=d1;           //object type Date
		this.time=time;       //how long is the event in minutes
		
	}

	public int getTime() {
		return time;
	}

	public Date getDate() {
		return d1;
	}

	@Override
	public String toString() {
		return "Event:\n\tDate:\t\t" + d1.toString() + "\n\tDuration:\t" + time + " minutes";
	}
	public String toString(int i) {
		return "Event:\n\tDate:\t\t" + d1.toString() + "\n\tDuration:\t" + time + " minutes";
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Event)) return false;
		Event event = (Event) o;
		return this.d1.equals(event.d1) && this.time == event.time;
	}
}


class EventWithP extends Event{  //event that include meeting with someone from the contacts
	
	People someone;

	public EventWithP(int time , People someone,Date d1) {

		super(time, d1);
		this.someone = someone;
	}

	public EventWithP(Event event , People someone) {

		super(event.time, event.d1);
		this.someone = someone;
	}

	@Override
	public String toString() {
		return super.toString() + "\n\tContact:\t" + someone.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof EventWithP)) return false;
		EventWithP event = (EventWithP) o;
		return super.equals(o) && this.someone.equals(event.someone);
	}
}

 class EventWithoutP extends Event{ //event without meeting with someone from the contacts
	
	String EventDescription;       //description of the event
	
	public EventWithoutP(int time ,String EventDescription,Date d1) {
          
		super(time,d1);
		this.EventDescription=EventDescription;
		
	}

	 public EventWithoutP(Event event, String EventDescription) {

		 super(event.time,event.d1);
		 this.EventDescription=EventDescription;

	 }

	 @Override
	 public String toString() {
		 return super.toString() + "\n\tDescription:\t" + EventDescription;
	 }

	 @Override
	 public boolean equals(Object o) {
		 if (o == null) return false;
		 if (!(o instanceof EventWithoutP)) return false;
		 EventWithoutP event = (EventWithoutP) o;
		 return super.equals(o) && this.EventDescription.equals(event.EventDescription);
	 }
}