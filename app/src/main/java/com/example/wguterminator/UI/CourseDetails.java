package com.example.wguterminator.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wguterminator.Database.Repository;
import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.Entities.CourseStatus;
import com.example.wguterminator.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CourseDetails extends AppCompatActivity {

    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    int id;
    int termId;
    Course course;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        editName = findViewById(R.id.courseName);
        editStartDate = findViewById(R.id.courseStartDate);
        editEndDate = findViewById(R.id.courseEndDate);
        id = getIntent().getIntExtra("id", -1);
        termId = getIntent().getIntExtra("termId", -1);
        repository = new Repository(getApplication());

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetails.this, CourseList.class);
                intent.putExtra("courseId", id);
                startActivity(intent);
            }
        });

        // Added this for end of part 3 videos
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getmAllCourses()) {
            if (c.getCourseId() == id) filteredCourses.add(c);
        }
        Button button = findViewById(R.id.saveCourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == -1) {
                    course = new Course(0,0, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), CourseStatus.in_progress,
                            "name", "phonenumber","email");
                    repository.insert(course);
                }
                else {
                    course = new Course(id,termId, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(),CourseStatus.in_progress,
                            "courseinstructorname", "instructorphone", "emailaddy");
                    repository.update(course);
                }
            }
        });
    }
}