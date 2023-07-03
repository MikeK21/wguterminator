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
    private String price;

    public Course(int courseId, String courseName, String price) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}