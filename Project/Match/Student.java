package model;

import java.util.ArrayList;
import java.util.Date;

public class Student {
	//������
	private ArrayList<String> free_time;
	private String student_no;
	private ArrayList<String> applications_department;
	private ArrayList<String> tags;
	private int choosed;
	private ArrayList<FreeTime>freeTimeByTimeFormat;
	
	public ArrayList<FreeTime> getFreeTimeByTimeFormat() {
		return freeTimeByTimeFormat;
	}
	public void setFreeTimeByTimeFormat(ArrayList<FreeTime> freeTimeByTimeFormat) {
		this.freeTimeByTimeFormat = freeTimeByTimeFormat;
	}
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
	public int getChoosed() {
		return choosed;
	}
	public void setChoosed(int choosed) {
		this.choosed = choosed;
	}
	
	
}
