package com.example.wguterminator.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
Course object
 */
@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private String courseName;
    private String date;

    public Course(int courseId, String courseName, String date) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.date = date;
    }

    public Course() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}