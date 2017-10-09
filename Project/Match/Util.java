package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

	static StudentDepartment studentDepartment;

	// 字符串转时间
	public static FreeTime string2time(String string) {
		Pattern p = Pattern.compile("(\\w+)\\.(\\d{1,2}):(\\d{1,2})~(\\d{1,2}):(\\d{1,2})");
		Matcher m = p.matcher(string);
		FreeTime freeTime = null;
		if (m.find()) {
			String day = m.group(1);
			int beginhour = Integer.parseInt(m.group(2));
			int beginmin = Integer.parseInt(m.group(3));
			int endhour = Integer.parseInt(m.group(4));
			int endmin = Integer.parseInt(m.group(5));
			freeTime = new FreeTime(day, beginhour, beginmin, endhour, endmin);
		}
		return freeTime;
	}

	// 时间冲突检测
	public static int timeconflict(Department department, Student student) {
		ArrayList<FreeTime> dTimeList=department.getEventSchedulesByTimeFormat();
		ArrayList<FreeTime> sTimeList=student.getFreeTimeByTimeFormat();
		//1.找到星期几 没找到则冲突
		//遍历部门活动时间
		//冲突次数
		int timeconflictCount=0;
		//总的冲突
		//boolean finalconflict=true;
		for(FreeTime dTime : dTimeList) {
			//if(dTime==null) {continue;}
			//局部冲突
			boolean conflict=true;
			for(FreeTime sTime : sTimeList) {
				
				//System.out.println("dTime:"+dTime.getDay()+" sTime"+sTime.getDay());
				//if(sTime==null) {continue;}
				//星期相等
				if(dTime.getDay().equals(sTime.getDay())) {
					//2.比较时间段大小  
					//函数调用，如冲突返回真
					if(!timecompare(dTime,sTime)) {
						//加了非号，表示没有冲突，就不需要继续监测
						conflict=false;
						break;
					}
				}
				else {
					//否则的话继续找没有冲突的地方
					continue;
				}
			}
			//局部冲突则整个冲突
			if(conflict==true) {
				timeconflictCount++;
				
			}
			
		
		}
		 		
		return timeconflictCount;
	}

	public static boolean timecompare(FreeTime dTime, FreeTime sTime) {
		int dTimebeginhour = dTime.getBeginhour();
		int dTimebeginmin = dTime.getBeginmin();
		int dTimeendhour = dTime.getEndhour();
		int dTimeendmin = dTime.getEndmin();
		int sTimebeginhour = sTime.getBeginhour();
		int sTimebeginmin = sTime.getBeginmin();
		int sTimeendhour = sTime.getEndhour();
		int sTimeendmin = sTime.getEndmin();
		if (((dTimebeginhour > sTimebeginhour)|| (dTimebeginhour == sTimebeginhour) && (dTimebeginmin >= sTimebeginmin))
			&& ((dTimeendhour < sTimeendhour) || (dTimeendhour == sTimeendhour) && (dTimeendmin <= sTimeendmin)))
			return false;
		else 
			return true;
	}

	//减少学生空闲时间
	public static ArrayList<FreeTime> decStudentFreeTime(ArrayList<FreeTime> departmentTime,ArrayList<FreeTime> studentTime){
		//TODO 未测试
		ArrayList<FreeTime> sTimes = studentTime;
		for( FreeTime departmentFreeTime : departmentTime) {
			if(departmentFreeTime==null) {continue;}
			int size = sTimes.size();
			for(int i = 0; i < size;i++ ) {
				FreeTime time =sTimes.get(i);
				if(time==null) {continue;}
				if(time.getDay().equals(departmentFreeTime.getDay())) {
				//先找到占用时间段
					if(!timecompare(departmentFreeTime,time)) {
						int dTimebeginhour = departmentFreeTime.getBeginhour();
						int dTimebeginmin = departmentFreeTime.getBeginmin();
						int dTimeendhour = departmentFreeTime.getEndhour();
						int dTimeendmin = departmentFreeTime.getEndmin();
						int sTimebeginhour = time.getBeginhour();
						int sTimebeginmin = time.getBeginmin();
						int sTimeendhour = time.getEndhour();
						int sTimeendmin = time.getEndmin();
						//开头末尾都相同
						if(dTimebeginhour==sTimebeginhour && dTimebeginmin==sTimebeginmin
								&& dTimeendhour==sTimeendhour&&dTimeendmin==sTimeendmin) {
							sTimes.set(i, new FreeTime("", 0,0, 0, 0));
						}
						//开头相同
						else if(dTimebeginhour==sTimebeginhour
								&& dTimebeginmin==sTimebeginmin) {
							time.setBeginhour(dTimeendhour);
							time.setBeginmin(dTimeendmin);
							sTimes.set(i, time);
						}
						//末尾相同
						else if( dTimeendhour==sTimeendhour
								&&dTimeendmin==sTimeendmin) {
							time.setEndhour(dTimebeginhour);
							time.setEndmin(dTimebeginmin);
							sTimes.set(i, time);
						}
						else {
							time.setEndhour(dTimebeginhour);
							time.setEndmin(dTimebeginmin);
							sTimes.set(i, time);
							FreeTime ft = new FreeTime(departmentFreeTime.getDay(), dTimeendhour, dTimeendmin, sTimeendhour, sTimeendmin);
							sTimes.add(ft);
						}
					}
				
				}
			}
		}
		//返回列表
		return sTimes;
	}
	
	// JSON 转为 java对象
	public static void studentdepartment() {
		BufferedReader br = null;
		// 读入文件字符串
		try {
			br = new BufferedReader(new FileReader("./input_data.txt"));

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		String str;
		try {
			while ((str = br.readLine()) != null) {
				stringBuilder.append(str);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		str = stringBuilder.toString();
		ObjectMapper mapper;
		try {
			mapper = new ObjectMapper();
			StudentDepartment sd = (StudentDepartment) mapper.readValue(str, StudentDepartment.class);
			studentDepartment = sd;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 返回学生列表 相当于输入
	public static ArrayList<Student> studentFactory() {
		
		return studentDepartment.students;
	}

	// 返回部门列表 相当于输入
	public static ArrayList<Department> departmentFactory() {
		return studentDepartment.departments;
	}

	
	
	public static void output(ArrayList<Student> unlucky_student,ArrayList<Department> admitted,ArrayList<Department> unlucky_department ) {
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter("./output_data.txt")));
			out.println("{");
			//输出unlucky_student
			int unlucky_student_size;
			if(unlucky_student!=null && (unlucky_student_size=unlucky_student.size())>0) {
				out.println("    \"unlucky_student\":[");
				for(int i = 0 ; i < unlucky_student_size;i++) {
					out.print("        \""+unlucky_student.get(i).getStudent_no()+"\"");
					if(i!=(unlucky_student_size-1)) {
						out.println(",");
					}
					else {
						out.println();
					}
				}
				out.println("    ],");
			}else {
				out.println(" \"unlucky_student\": [ ],");
			}		
			
			
			int admittedSize;
			if(admitted!=null && (admittedSize=admitted.size())>0) {
				out.println("    \"admitted\":[");
				for(int i = 0;i < admittedSize;i++) {
					out.println("        {");
					Department department = admitted.get(i);
					//输出members
					ArrayList<Student> members = department.getMembers();
					int membersize= members.size();
					out.println("            \"member\": [");
					for(int j = 0;j<membersize;j++) {
						out.print("                \""+members.get(j).getStudent_no()+"\"");
						if(j!=(membersize-1)) {
							out.println(",");
						}
						else {
							out.println();
						}		
					}
					out.println("            ],");
					//输出department_no
					out.println("            \"department_no\": "+"\""+department.getDepartment_no()+"\"");
					
					out.print("        }");
			        if(i!=(admittedSize-1)) {
			        	out.println(",");
			        }
			        else {
			        	out.println();
			        }
				}
				out.println("    ],");
				
			}
			else {
				out.println(" \"admitted\": [ ],");
			}
				
			
			//输出unlucky_department
			int unlucky_department_size;
			if(unlucky_department!=null && (unlucky_department_size=unlucky_department.size())>0) {
			    out.println("    \"unlucky_department\": [");
			    for(int j = 0;j<unlucky_department_size;j++) {
			    	out.print("        \""+unlucky_department.get(j).getDepartment_no()+"\"");
			    	if(j!=(unlucky_department_size-1)) {
			    		out.println(",");
			    	}
			    	else {
			    		out.println();
			    	}
			    }
			    out.println("    ]");
			}
			else {
				out.println("    \"unlucky_department\": [ ]");
			}
			
			out.println("}");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	

	public static void outputArrayList(ArrayList<Student> finalUnluckyStudent) {
		// TODO Auto-generated method stub
		for(Student element : finalUnluckyStudent) {			
			System.out.println(element.getStudent_no());
		}
	}

	
}
