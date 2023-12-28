package com.example.wguterminator.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
/** Assessment Model
 *
 */
@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private int courseId;
    private String assessmentName;
    private String assessmentEndDate;
    private AssessmentType assessmentType;


    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }
    public Assessment(int assessmentId, int courseId, String assessmentName, String assessmentEndDate, AssessmentType assessmentType) {
        this.assessmentId = assessmentId;
        this.courseId = courseId;
        this.assessmentName = assessmentName;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentType = assessmentType;
    }

    public Assessment() {
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
