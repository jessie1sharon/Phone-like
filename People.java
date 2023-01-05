package project2;

import java.util.Comparator;

public class People {
	String num;
	String name;
	
	public People(String name , String num) {
		
		this.name = name;
		this.num = num;
	}

	@Override
    public String toString() {
		
		return "" + name + " " + num;
	}
	
	public String getName() {
		return this.name;
	}
	public String getNum() {
		return this.num;
	}
	
	// Compare equality of two people (lowercase and uppercase are the same)
	public boolean equals(People other) {
		return this.name.toLowerCase().equals(other.name.toLowerCase()) && this.num.equals(other.num);
	}
	
	// Compare equality of two names (lowercase and uppercase are the same)
	public boolean equals1(People other) {
		return this.name.toLowerCase().equals(other.name.toLowerCase());
	}


	public static class NameComparator implements Comparator<People> {
		@Override
		public int compare(People lhs, People rhs) {
			return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
		}
	}

	public static class NumComparator implements Comparator<People> {
		@Override
		public int compare(People lhs, People rhs) {
			//return lhs.num - rhs.num;
			long res = Long.parseLong(lhs.getNum()) - Long.parseLong(rhs.getNum());
			return res < 0 ? -1 : (res > 0 ? 1 : 0);
		}
	}
}
