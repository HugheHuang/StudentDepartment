package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ext.Java7Support;

public class DSSystem {
	static public Set<Student> unluckyStudent = new HashSet<Student>();
	static public ArrayList<Student> finalUnluckyStudent = new ArrayList<Student>();
	static public ArrayList<Department> unluckyDepartment = new ArrayList<Department>();
	static public ArrayList<Department> luckyDepartment = new ArrayList<Department>();
	static public ArrayList<Student> studentList;
	static public ArrayList<Department> departmentList ;
	static public HashMap<Department, HashMap<Student,Integer>> departStuPriority = new HashMap<Department, HashMap<Student,Integer>>();
	
	//ִ�к���
	@SuppressWarnings({ "unchecked", "unchecked" })
	static public void display() {
		//System.out.println("begin!");
		Util.studentdepartment();
		studentList=Util.studentFactory();
		departmentList = Util.departmentFactory();
		//�Բ��ź�ѧ������һ�Σ��ַ�����ʱ��,���ֳ�ʼ��
		for(Department department : departmentList) {
			ArrayList<FreeTime> departmentTime=new ArrayList<FreeTime>();
			for(String departStringTime: department.getEvent_schedules()) {
				//System.out.println(departStringTime);
				departmentTime.add(Util.string2time(departStringTime));
				
			}
			department.setEventSchedulesByTimeFormat(departmentTime);
			department.setCandidates(new ArrayList<Student>());
		}
		for(Student student : studentList) {
			//student.setFreeTimeByTimeFormat();
			ArrayList<FreeTime> studentTime=new ArrayList<FreeTime>();
			for(String stuStringTime : student.getFree_time() ) {
				//System.out.println(stuStringTime);
				studentTime.add(Util.string2time(stuStringTime));
			}
			student.setFreeTimeByTimeFormat(studentTime);
		}
		
		
		//һ�α�����������Ը��ѧ�����벿�ŵĺ�ѡ�б�
		for(Department department : departmentList) {
			for(Student student : studentList) {
				for( String studentapplicationdepartmentno : student.getApplications_department() ) {
					if(department.getDepartment_no().equals(studentapplicationdepartmentno)) {
						ArrayList<Student> sList=department.getCandidates();
						sList.add(student);
						department.setCandidates(sList);
						break;
					}
				}
			}
		} 
		
		//���α�������ÿ�����ŵĺ�ѡ�б�ĳ�Ա����һ��ֵ��Ȼ�������ֵ���򣬼���
		for(Department department : departmentList) {
			departStuPriority.put(department, new HashMap<Student,Integer>());
			for(Student student : department.getCandidates())
			{
				int grade = count(department, student);
				//System.out.println("cadidateSize:"+department.getCandidates().size());
				//System.out.println(student.getStudent_no()+" "+grade);
				departStuPriority.get(department).put(student, grade);
			}
			StudentComparator studentComparator = new StudentComparator(departStuPriority.get(department));
			//TODO ����δ����
			Collections.sort(department.getCandidates(),studentComparator);
			ArrayList<Student> departmentCandidates=department.getCandidates();
			int departmentLimit=department.getMember_limit();
			int candidateSize = departmentCandidates.size();
			//System.out.println(candidateSize);
			//��ѡ�˶��еĳ��ȸ�����ѡ�˵����ֵ�Ƚ�
			
			HashMap<Student, Integer> studentGrade = departStuPriority.get(department);
			
			//�����Ļ�
			//if(candidateSize>departmentLimit){
			
				ArrayList<Student> departmentMembers = new ArrayList<Student>();
				for(int i=0;i<candidateSize;i++) {
					Student student = departmentCandidates.get(i);
					
					/*if(studentGrade.get(student).intValue()>0) {
						System.out.println("i: "+i+" Student_no:"+student.getStudent_no()+" Grade:"+  studentGrade.get(student).intValue());
					}*/
					
					if(i<departmentLimit && (studentGrade.get(student).intValue()>0)) {
						//֮ǰ�ļ����Ӧ���ŵĳ�Ա�б�
						//System.out.println("-------"+department.getDepartment_no()+"-------");
						//System.out.println(departmentCandidates.get(i).getStudent_no());
						//System.out.println("----------------------------");
						departmentMembers.add(student);	
					}
					else {
						//ʣ�µļ���unlunchy���ϣ�
						unluckyStudent.add(student);
					}
				}
				department.setMembers(departmentMembers);
				
			//}
			/*
			else {
				//���С�ȵĻ���ȫ������(Ҫ�޳�Ϊ0��)
				ArrayList<Student> departmentMembers = new ArrayList<Student>();
				department.setMembers(departmentCandidates);
			}*/
			
			//��Member���н����޸ı�־λ�����ٿ���ʱ�� 
			for(Student student : department.getMembers()) {
				student.setChoosed(student.getChoosed()+1);
				student.setFreeTimeByTimeFormat(Util.decStudentFreeTime(department.getEventSchedulesByTimeFormat(), student.getFreeTimeByTimeFormat()));
			}
				
		}	
		//System.out.println("unlunckysize:"+unluckyStudent.size());
		//unlunckyѧ���ı���
		for(Student student : unluckyStudent) {
			if(student.getChoosed()==0) {
				finalUnluckyStudent.add(student);
			}
		}
		//unlucky���ŵı���
		for(Department department : departmentList) {
			if(department.getMembers()==null||department.getMembers().size()==0) {
				unluckyDepartment.add(department);
			}
			else {
				luckyDepartment.add(department);
			}
		}
		//System.out.println("finish!");
		Util.output(finalUnluckyStudent, departmentList, unluckyDepartment);;
		
	}
	
	//ѧ��ֵ����
	static public int count(Department department,Student student) {
			//System.out.println("count=0");
			int count;
			int choosed=student.getChoosed();
			int hobbies=hobbies(department,student);
			int priority=priority(department,student);
			int conflict=Util.timeconflict(department, student);
			int timeSize=department.getEventSchedulesByTimeFormat().size();
			//�������1/2�ʱ��δ�ܵ������̭(����0)��
			if(conflict>(timeSize*1/2)) { return 0;}
			//TODO ����δ��
			//���ǵ����أ��Ѽ��벿�ŵĸ���+��Ȥƥ��+���ȼ�ƥ��+ʱ���ͻ����
			count=3*(5-choosed)+4*(hobbies)+3*(5-priority)+10*(timeSize-conflict+1);
			//System.out.println("count="+count);
			return count;
		
		
	}
	
	static public int hobbies(Department department,Student student) {
		int hobbies=0;
		
		for(String departmenttags : department.getTags()) {
			for(String studenttags : student.getTags()) {
				if(departmenttags.equals(studenttags)) {
					hobbies++;
				}
			}
		}
		return hobbies;
	}
	
	
	static public int priority(Department department,Student student) {
		int priority=0;
		priority=student.getApplications_department().indexOf(department.getDepartment_no());
		return priority;
	}
}
