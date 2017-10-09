package model;

import java.util.ArrayList;

public class StudentDepartment {
	private ArrayList<Student> students;
	private ArrayList<Department> departments;
	public ArrayList<Student> getStudents() {
		return students;
	}
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}
	public ArrayList<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(ArrayList<Department> departments) {
		this.departments = departments;
	}
	public StudentDepartment(ArrayList<Student> students, ArrayList<Department> departments) {
		this.students = students;
		this.departments = departments;
	}
	
	
}
