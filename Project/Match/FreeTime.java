package model;

public class FreeTime {
	private String day;
	private int beginhour;
	private int beginmin;
	private int endhour;
	private int endmin;
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getBeginhour() {
		return beginhour;
	}
	public void setBeginhour(int beginhour) {
		this.beginhour = beginhour;
	}
	public int getBeginmin() {
		return beginmin;
	}
	public void setBeginmin(int beginmin) {
		this.beginmin = beginmin;
	}
	public int getEndhour() {
		return endhour;
	}
	public void setEndhour(int endhour) {
		this.endhour = endhour;
	}
	public int getEndmin() {
		return endmin;
	}
	public void setEndmin(int endmin) {
		this.endmin = endmin;
	}
	public FreeTime(String day, int beginhour, int beginmin, int endhour, int endmin) {
		super();
		this.day = day;
		this.beginhour = beginhour;
		this.beginmin = beginmin;
		this.endhour = endhour;
		this.endmin = endmin;
	}
	@Override
	public String toString() {
		return "星期："+this.day+" 开始小时"+this.beginhour+" 结束小时"+this.endhour;
	}
	
}
