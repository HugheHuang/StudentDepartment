package model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class StudentComparator implements Comparator {
	
	private HashMap<Student,Integer> priority;
	@Override
	public int compare(Object o1, Object o2) {
		Student s1 = (Student)o1;
		Student s2 = (Student)o2;
		int i1=priority.get(s1);
		int i2=priority.get(s2);
		if(i1>i2)
			return -1;
		if(i1<i2)
			return 1;
		else return 0;
	}
	public StudentComparator(HashMap<Student,Integer> priority) {
		super();
		this.priority = priority;
	}
	
}
