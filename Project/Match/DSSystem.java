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
	
	//执行函数
	@SuppressWarnings({ "unchecked", "unchecked" })
	static public void display() {
		//System.out.println("begin!");
		Util.studentdepartment();
		studentList=Util.studentFactory();
		departmentList = Util.departmentFactory();
		//对部门和学生遍历一次，字符串改时间,各种初始化
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
		
		
		//一次遍历，将有意愿的学生加入部门的候选列表
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
		
		//二次遍历，对每个部门的候选列表的成员计算一个值，然后按照这个值排序，加人
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
			//TODO 排序未测试
			Collections.sort(department.getCandidates(),studentComparator);
			ArrayList<Student> departmentCandidates=department.getCandidates();
			int departmentLimit=department.getMember_limit();
			int candidateSize = departmentCandidates.size();
			//System.out.println(candidateSize);
			//候选人队列的长度跟部门选人的最大值比较
			
			HashMap<Student, Integer> studentGrade = departStuPriority.get(department);
			
			//如果大的话
			//if(candidateSize>departmentLimit){
			
				ArrayList<Student> departmentMembers = new ArrayList<Student>();
				for(int i=0;i<candidateSize;i++) {
					Student student = departmentCandidates.get(i);
					
					/*if(studentGrade.get(student).intValue()>0) {
						System.out.println("i: "+i+" Student_no:"+student.getStudent_no()+" Grade:"+  studentGrade.get(student).intValue());
					}*/
					
					if(i<departmentLimit && (studentGrade.get(student).intValue()>0)) {
						//之前的加入对应部门的成员列表
						//System.out.println("-------"+department.getDepartment_no()+"-------");
						//System.out.println(departmentCandidates.get(i).getStudent_no());
						//System.out.println("----------------------------");
						departmentMembers.add(student);	
					}
					else {
						//剩下的加入unlunchy集合；
						unluckyStudent.add(student);
					}
				}
				department.setMembers(departmentMembers);
				
			//}
			/*
			else {
				//如果小等的话，全部加入(要剔除为0的)
				ArrayList<Student> departmentMembers = new ArrayList<Student>();
				department.setMembers(departmentCandidates);
			}*/
			
			//对Member队列进行修改标志位，减少空闲时间 
			for(Student student : department.getMembers()) {
				student.setChoosed(student.getChoosed()+1);
				student.setFreeTimeByTimeFormat(Util.decStudentFreeTime(department.getEventSchedulesByTimeFormat(), student.getFreeTimeByTimeFormat()));
			}
				
		}	
		//System.out.println("unlunckysize:"+unluckyStudent.size());
		//unluncky学生的遍历
		for(Student student : unluckyStudent) {
			if(student.getChoosed()==0) {
				finalUnluckyStudent.add(student);
			}
		}
		//unlucky部门的遍历
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
	
	//学生值计算
	static public int count(Department department,Student student) {
			//System.out.println("count=0");
			int count;
			int choosed=student.getChoosed();
			int hobbies=hobbies(department,student);
			int priority=priority(department,student);
			int conflict=Util.timeconflict(department, student);
			int timeSize=department.getEventSchedulesByTimeFormat().size();
			//如果超过1/2活动时间未能到达，则淘汰(返回0)。
			if(conflict>(timeSize*1/2)) { return 0;}
			//TODO 参数未定
			//考虑的因素：已加入部门的个数+兴趣匹配+优先级匹配+时间冲突个数
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
