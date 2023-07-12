package com.example.wguterminator.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.wguterminator.R;

public class CourseDetails extends AppCompatActivity {

    EditText editName;
    EditText editDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        editName = findViewById(R.id.courseName);
        editDate = findViewById(R.id.courseDate);
    }
}