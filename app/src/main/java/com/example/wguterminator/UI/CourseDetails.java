package com.example.wguterminator.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wguterminator.Database.Repository;
import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.R;

import java.util.ArrayList;
import java.util.List;

public class CourseDetails extends AppCompatActivity {

    EditText editName;
    EditText editDate;
    int id;
    Course course;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        editName = findViewById(R.id.courseName);
        editDate = findViewById(R.id.courseDate);
        id = getIntent().getIntExtra("id", -1);
        repository = new Repository(getApplication());

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
                    course = new Course(0, editName.getText().toString(), editDate.getText().toString());
                    repository.insert(course);
                }
                else {
                    course = new Course(id, editName.getText().toString(), editDate.getText().toString());
                    repository.update(course);
                }
            }
        });
    }
}