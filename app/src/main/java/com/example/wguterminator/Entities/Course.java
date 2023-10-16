package com.example.wguterminator.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
Course object
 */

;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private int termId;
    private String courseName;

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public String getCourseInstructorName() {
        return courseInstructorName;
    }

    public void setCourseInstructorName(String courseInstructorName) {
        this.courseInstructorName = courseInstructorName;
    }

    public String getCourseInstructorNumber() {
        return courseInstructorNumber;
    }

    public void setCourseInstructorNumber(String courseInstructorNumber) {
        this.courseInstructorNumber = courseInstructorNumber;
    }

    public String getCourseInstructorEmail() {
        return courseInstructorEmail;
    }

    public void setCourseInstructorEmail(String courseInstructorEmail) {
        this.courseInstructorEmail = courseInstructorEmail;
    }

    private String startDate;
    private String endDate;
    private CourseStatus status;
    private String courseInstructorName;
    private String courseInstructorNumber;
    private String courseInstructorEmail;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Course(int courseId, int termId, String courseName, String startDate, String endDate, CourseStatus status,
                  String courseInstructorName, String courseInstructorNumber, String courseInstructorEmail) {
        this.courseId = courseId;
        this.termId = termId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.courseInstructorName = courseInstructorName;
        this.courseInstructorNumber = courseInstructorNumber;
        this.courseInstructorEmail = courseInstructorEmail;
    }

    public Course() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}