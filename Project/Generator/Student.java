package model;

import java.util.ArrayList;
import java.util.Date;

public class Student {
	//¸÷ÊôÐÔ
	private ArrayList<String> free_time;
	private String student_no;
	private ArrayList<String> applications_department;
	private ArrayList<String> tags;
	
	
	
	public ArrayList<String> getFree_time() {
		return free_time;
	}
	public void setFree_time(ArrayList<String> free_time) {
		this.free_time = free_time;
	}
	public String getStudent_no() {
		return student_no;
	}
	public void setStudent_no(String student_no) {
		this.student_no = student_no;
	}
	public ArrayList<String> getApplications_department() {
		return applications_department;
	}
	public void setApplications_department(ArrayList<String> applications_department) {
		this.applications_department = applications_department;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public Student() {
		super();
	}
	public Student(ArrayList<String> free_time, String student_no, ArrayList<String> applications_department,
			ArrayList<String> tags) {
		super();
		this.free_time = free_time;
		this.student_no = student_no;
		this.applications_department = applications_department;
		this.tags = tags;
	}
	
	
}
