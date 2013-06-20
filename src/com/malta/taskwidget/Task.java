package com.malta.taskwidget;

public class Task {
	private long id;
	private String name;
	private String category;
	private String end_date;
    private int isDone = 1;

    public int getDone() {
        return isDone;
    }

    public void setDone(int done) {
        isDone = done;
    }

    private Task(long _id, String _name, String _category, String _end_date) {
        this.id=_id;
        this.name=_name;
        this.category=_category;
        this.end_date=_end_date;
    }

    public static Task createTask(long _id, String _name, String _category, String _end_date) {
        return new Task(_id, _name, _category, _end_date);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

}
