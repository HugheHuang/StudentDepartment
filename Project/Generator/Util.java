package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
	static private String[] Tags = { "study","film","English","music","reading","chess","football","dance","programming", "basketball"};
	static private int tagsSize = Tags.length; 
	static private String[] Days = { "Mon.","Tues.","Wed.","Thur.","Fri.","Sat.","Sun."};
	static private int daysSize = Days.length;
	static public ArrayList<Student> studentGenerator(){
		
		ArrayList<Student> students =new ArrayList<Student>();
		for(int i = 1;i <= 300;i++) {
			//随机生成时间
			ArrayList<String> freeTimes = studengTimeGenerator();
			//生成学生编号
			String studentNo = "031502"+String.format("%03d", i);
			//生成部门申请表
			ArrayList<String> applicationsDepartment = departmentApplicationGenerator();
			//生成兴趣标签
			ArrayList<String> tags = tagsGenerator();
			Student student = new Student(freeTimes, studentNo, applicationsDepartment, tags);
			students.add(student);
		}
		return students;
	}
	
	static public ArrayList<Department> departmentGenerator(){
		ArrayList<Department> departments = new ArrayList<Department>();
		for(int i = 1;i <= 20;i++) {
			//随机生成时间
			ArrayList<String> eventsSchedules =  departmentTimeGenerator();
			//随机生成限定人数
			int limit = randomRange(10, 16);
			//部门编号
			String departmentNo = "D0"+String.format("%02d", i);
			//兴趣标签
			ArrayList<String> tags = tagsGenerator();
			Department department = new Department(eventsSchedules, limit, departmentNo, tags);
			departments.add(department);
		}
		return departments;
	}
	
	static public ArrayList<String> tagsGenerator(){
		ArrayList<String> tag = new ArrayList<String>();
		for(int i = 0;i < tagsSize;i++) {
			if(randomRange(0,2)==1) {
				tag.add(Tags[i]);
			}
		}
		return tag;
	}
	
	static public ArrayList<String> departmentTimeGenerator(){
		ArrayList<String> time = new ArrayList<String>();
		int timeFinalCount = randomRange(1,6 );
		int timecount = 0;
		int function = randomRange(0, 3);
		//正序
		if(function==0) {
			for(int i = 0;i < daysSize;i++) {
				if(randomRange(0,2)==1) {
					int timeRandom = randomRange(0, 3);
					int timeFinalBegin = 8;
					int timeFinalEnd = 21;
					for(int j = 0;j < timeRandom;j++) {
						if(timeFinalBegin<timeFinalEnd) {
							int timeBegin = randomRange(timeFinalBegin,timeFinalEnd);
							int timeEnd = timeBegin + randomRange(1, 3);
							timeFinalBegin=timeEnd+1;
							String string = Days[i]+timeBegin+":00~"+timeEnd+":00";
							time.add(string);
							timecount++;
						}else {
							break;
						}					
					}
					if(timecount==timeFinalCount) {
						break;
					}
				}
			}
		}
		//逆序
		else if(function==1) {
			for(int i = daysSize-1;i >=0;i--) {
				if(randomRange(0,2)==1) {
					int timeRandom = randomRange(0, 3);
					int timeFinalBegin = 8;
					int timeFinalEnd = 21;
					for(int j = 0;j < timeRandom;j++) {
						if(timeFinalBegin<timeFinalEnd) {
							int timeBegin = randomRange(timeFinalBegin,timeFinalEnd);
							int timeEnd = timeBegin + randomRange(1, 3);
							timeFinalBegin=timeEnd+1;
							String string = Days[i]+timeBegin+":00~"+timeEnd+":00";
							time.add(string);
							timecount++;
						}else {
							break;
						}					
					}
					if(timecount==timeFinalCount) {
						break;
					}
				}
			}
		}
		//区间随机
		else if(function==2)
		{
			int begin = randomRange(0, daysSize-1);
			int end = randomRange(begin+1, daysSize);
			int innerFunction = randomRange(0, 2);
			//正序
			if(innerFunction==0) {
				for(int i = begin;i <= end;i++) {
					if(randomRange(0,2)==1) {
						int timeRandom = randomRange(0, 3);
						int timeFinalBegin = 8;
						int timeFinalEnd = 21;
						for(int j = 0;j < timeRandom;j++) {
							if(timeFinalBegin<timeFinalEnd) {
								int timeBegin = randomRange(timeFinalBegin,timeFinalEnd);
								int timeEnd = timeBegin + randomRange(1, 3);
								timeFinalBegin=timeEnd+1;
								String string = Days[i]+timeBegin+":00~"+timeEnd+":00";
								time.add(string);
								timecount++;
							}else {
								break;
							}					
						}
						if(timecount==timeFinalCount) {
							break;
						}
					}
				}
			}//逆序
			else {
				for(int i = end;i >= begin;i--) {
					if(randomRange(0,2)==1) {
						int timeRandom = randomRange(0, 3);
						int timeFinalBegin = 8;
						int timeFinalEnd = 21;
						for(int j = 0;j < timeRandom;j++) {
							if(timeFinalBegin<timeFinalEnd) {
								int timeBegin = randomRange(timeFinalBegin,timeFinalEnd);
								int timeEnd = timeBegin + randomRange(1, 3);
								timeFinalBegin=timeEnd+1;
								String string = Days[i]+timeBegin+":00~"+timeEnd+":00";
								time.add(string);
								timecount++;
							}else {
								break;
							}					
						}
						if(timecount==timeFinalCount) {
							break;
						}
					}
				}
			}
			
		}
		if(time.size()==0)
		{
			String string ="Sat.19:00~21:00";
			time.add(string);
		}
		return time;
	}
	static public ArrayList<String> studengTimeGenerator(){
		ArrayList<String> time = new ArrayList<String>();
		for(int i = 0;i < daysSize;i++) {
			if(randomRange(0,2)==1) {
				int timeRandom = randomRange(0, 3);
				int timeFinalBegin = 8;
				int timeFinalEnd = 21;
				for(int j = 0;j < timeRandom;j++) {
					if(timeFinalBegin<timeFinalEnd) {
						int timeBegin = randomRange(timeFinalBegin,timeFinalEnd);
						int timeEnd = timeBegin + randomRange(1, 24-timeBegin);
						timeFinalBegin=timeEnd+1;
						String string = Days[i]+timeBegin+":00~"+timeEnd+":00";
						time.add(string);	
					}else {
						break;
					}
					
				}
			}
		}
		if(time.size()==0)
		{
			String string ="Sat.19:00~21:00";
			time.add(string);
		}
		return time;
	}
	
	static public ArrayList<String> departmentApplicationGenerator(){
		ArrayList<String> departmentApplication = new ArrayList<String>();
		int applicationSize=randomRange(1, 6);
		int departmentCount=20;
		int count=0;
		//3种方式随机
		int function = randomRange(0, 3);
		//第一种 从头开始
		if(function==0) {
			for(int i = 1;i <= departmentCount;i++) {
				if(randomRange(0, 2)==1) {
					String departmentNo = "D0"+String.format("%02d", i);
					departmentApplication.add(departmentNo);
					count++;
				}
				if(count==applicationSize) {
					break;
				}
			}
		}
		//第二种 从尾开始
		else if(function==1) {
			for(int i = departmentCount;i > 0 ;i--) {
				if(randomRange(0, 2)==1) {
					String departmentNo = "D0"+String.format("%02d", i);
					departmentApplication.add(departmentNo);
					count++;
				}
				if(count==applicationSize) {
					break;
				}
			}
		}
		//第三种 从随机区间开始
		else if(function==2) {
			int begin =randomRange(1, departmentCount);
			int end = randomRange(begin+1, departmentCount+1);
			int innerFunction = randomRange(0, 2);
			if(innerFunction==0) {
				//正序
				for(int i = begin;i <= end;i++) {
					if(randomRange(0, 2)==1) {
						String departmentNo = "D0"+String.format("%02d", i);
						departmentApplication.add(departmentNo);
						count++;
					}
					if(count==applicationSize) {
						break;
					}
				}
			}
			else {
				//逆序
				for(int i = end;i >= begin;i--) {
					if(randomRange(0, 2)==1) {
						String departmentNo = "D0"+String.format("%02d", i);
						departmentApplication.add(departmentNo);
						count++;
					}
					if(count==applicationSize) {
						break;
					}
				}
			}
			
		}
		if(count==0) {
			String departmentNo = "D0"+String.format("%02d", randomRange(1, 21));
			departmentApplication.add(departmentNo);
		}
		else{
			
			//departmentApplication乱序;
			Collections.shuffle(departmentApplication);
		}
		return departmentApplication;
	}
	static public void output(StudentDepartment sd) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter("./input_data.txt")));
			String string = StudentDepartment2json(sd);
			out.println(string);
			System.out.print(string);
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally {
			out.close();
		}
	}
	
	static public String StudentDepartment2json(StudentDepartment sd) {
		StringWriter out = new StringWriter();
		JsonGenerator jsonGenerator = null;
		try {
			jsonGenerator = new JsonFactory().createGenerator(out);
			ObjectMapper mapper = new ObjectMapper();
			mapper.writerWithDefaultPrettyPrinter().writeValue(jsonGenerator, sd);
			//mapper.writeValue(jsonGenerator, sd);
		} catch (IOException e) {			
			e.printStackTrace();
		}finally {
			try {
					if(jsonGenerator!=null)
						jsonGenerator.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
		}
		
		return out.getBuffer().toString();
	}
	
	//[start,end)
	static public int randomRange(int start,int end) {
		return (int)(Math.random()*(end-start)+start);
	}
}
