package com.malta.taskwidget;

public class Task {
	private long id;
	private String name;
	private String start_date;
	private String end_date;
	
	public long getID(){
		return id;
	}
	
	public void setID(long id){
		this.id=id;
	}
	
	public String getname(){
		return name;
	}
	
	public void setname(String name){
		this.name=name;
	}	
	
	
	public String getStart(){
		return start_date;
	}

	public void setStart(String start_date){
		this.start_date=start_date;
	}	
	
	public String getEnd(){
		return end_date;
	}
	
	public void setEnd(String end_date){
		this.end_date=end_date;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Long.toString(id)+".  "+name+"  |  "+start_date+"  |  "+end_date;
	}	
	
	
	
}
