package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Department {
	//¸÷ÊôĞÔ
	private ArrayList<String> event_schedules;
	private int member_limit;
	private String department_no;
	private ArrayList<String> tags;
	
	
	
	
	public ArrayList<String> getEvent_schedules() {
		return event_schedules;
	}
	public void setEvent_schedules(ArrayList<String> event_schedules) {
		this.event_schedules = event_schedules;
	}
	public int getMember_limit() {
		return member_limit;
	}
	public void setMember_limit(int member_limit) {
		this.member_limit = member_limit;
	}
	public String getDepartment_no() {
		return department_no;
	}
	public void setDepartment_no(String department_no) {
		this.department_no = department_no;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public Department(ArrayList<String> event_schedules, int member_limit, String department_no,
			ArrayList<String> tags) {
		super();
		this.event_schedules = event_schedules;
		this.member_limit = member_limit;
		this.department_no = department_no;
		this.tags = tags;
	}
	public Department() {
		super();
	}
	
	
	
}
