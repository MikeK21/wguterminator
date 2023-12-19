package com.example.wguterminator.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


import com.example.wguterminator.Database.Repository;
import com.example.wguterminator.Entities.Assessment;
import com.example.wguterminator.Entities.AssessmentType;
import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {


    EditText editName;
    EditText editEndDate;
    EditText editAssessmentType;
    Assessment assessment;
    Repository repository;
    int assessId;
    int courseId;
    String name;
    String stringEndDate;
    String assessmentTypeString;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance();
    String myFormat = "MM/dd/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        editName.findViewById(R.id.assessName);
        editEndDate.findViewById(R.id.assessEndDate);
        editAssessmentType.findViewById(R.id.assessType);
        // Need to fill this out to match in AssessmentAdapter
        assessId = getIntent().getIntExtra("assessId",-1);
        courseId = getIntent().getIntExtra("courseId", -1);
        name = getIntent().getStringExtra("name");
        stringEndDate = getIntent().getStringExtra("endDate");
        assessmentTypeString = getIntent().getStringExtra("type");

        Button assessListButton = findViewById(R.id.seeAssess);
        assessListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentDetails.this, AssessmentList.class);
                intent.putExtra("assessId", assessId);
                startActivity(intent);
            }
        });

        // Added this for end of part 3 videos
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getmAllCourses()) {
            if (c.getCourseId() == courseId) filteredCourses.add(c);
        }

        Button saveButton = findViewById(R.id.saveAssess);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssessmentType assessmentType = AssessmentType.objective;
                if (assessmentTypeString != null) {
                    switch (assessmentTypeString) {
                        case "performance":
                            assessmentType = AssessmentType.performance;
                            break;
                    }
                    if (assessId == -1) {
                        // need to add associated course id
                        assessment = new Assessment(0,0,editName.getText().toString(),editEndDate.getText().toString(),assessmentType);
                        repository.insert(assessment);
                    } else {
                        // need to add associated course id
                        assessment = new Assessment(assessId,courseId, editName.getText().toString(),editEndDate.getText().toString(),assessmentType);
                        repository.update(assessment);
                    }
                }
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editEndDate.getText().toString();
                //String endInfo = editEndDate.getText().toString();
                if (info.equals("")) info = "09/01/23";
                //if (endInfo.equals("")) endInfo = "03/01/24";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                    //myCalendarEnd.setTime(sdf.parse(endInfo));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
            }

            private void updateLabelEnd() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
            }

        };

    }
}