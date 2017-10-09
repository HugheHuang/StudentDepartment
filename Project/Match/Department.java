package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Department {
	//¸÷ÊôÐÔ
	private ArrayList<String> event_schedules;
	private int member_limit;
	private String department_no;
	private ArrayList<String> tags;
	private ArrayList<Student> candidates;
	private ArrayList<Student> members;
	private ArrayList<FreeTime> eventSchedulesByTimeFormat;
	
	public ArrayList<FreeTime> getEventSchedulesByTimeFormat() {
		return eventSchedulesByTimeFormat;
	}
	public void setEventSchedulesByTimeFormat(ArrayList<FreeTime> eventSchedulesByTimeFormat) {
		this.eventSchedulesByTimeFormat = eventSchedulesByTimeFormat;
	}
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
	public ArrayList<Student> getCandidates() {
		return candidates;
	}
	public void setCandidates(ArrayList<Student> candidates) {
		this.candidates = candidates;
	}
	public ArrayList<Student> getMembers() {
		return members;
	}
	public void setMembers(ArrayList<Student> members) {
		this.members = members;
	}
	
	
}
