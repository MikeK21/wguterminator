package com.example.wguterminator.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.wguterminator.Database.Repository;
import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.Entities.CourseStatus;
import com.example.wguterminator.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {

    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    EditText editNotes;
    EditText editAssignedInstructor;
    EditText editStatus;
    EditText editInstructorName;
    EditText editInstructorEmail;
    EditText editInstructorPhone;
    int courseId;
    int termId;
    Course course;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    String note;
    String status;
    String stringStartDate;
    String stringEndDate;
    String instructorName;
    String instructorEmail;
    String instructorPhone;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        editName = findViewById(R.id.courseName);
        editNotes = findViewById(R.id.courseNotez);
        editAssignedInstructor = findViewById(R.id.assignedInstructor);
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate = findViewById(R.id.courseStartDate);
        editStartDate.setText(sdf.format(new Date()));
        editEndDate = findViewById(R.id.courseEndDate);
        editEndDate.setText(sdf.format(new Date()));
        editAssignedInstructor.findViewById(R.id.assignedInstructor);
        //editInstructorName.setText(getIntent().getStringExtra("instructorName"));
        /*
        editInstructorEmail.findViewById(R.id.courseInstructorEmail);
        editInstructorPhone.findViewById(R.id.courseInstructorPhone);
        */
        courseId = getIntent().getIntExtra("courseId", -1);
        termId = getIntent().getIntExtra("termId", -1);
        name = getIntent().getStringExtra("name");
        note = getIntent().getStringExtra("note");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        stringStartDate = getIntent().getStringExtra("startDate");
        stringEndDate = getIntent().getStringExtra("endDate");
        status = getIntent().getStringExtra("status");
        // comment out for now
        //startDate = getIntent().getStringExtra("startDate");
        //endDate = getIntent().getStringExtra("endDate");
        editName.setText(name);
        editNotes.setText(note);
        editStartDate.setText(stringStartDate);
        editEndDate.setText(stringEndDate);
        editAssignedInstructor.setText(instructorName);
        /*
        editInstructorEmail.setText(instructorEmail);
        editInstructorPhone.setText(instructorPhone);
         */
        repository = new Repository(getApplication());

        ArrayList<CourseStatus> courseStatusList = new ArrayList<>();

        ArrayAdapter<CourseStatus> courseStatusArrayAdapter = new ArrayAdapter<CourseStatus>(this,
                android.R.layout.simple_spinner_item,courseStatusList);
        Spinner spinner = findViewById(R.id.statusSpinner);
        spinner.setAdapter(courseStatusArrayAdapter);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetails.this, CourseList.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });

        // Added this for end of part 3 videos
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getmAllCourses()) {
            if (c.getCourseId() == courseId) filteredCourses.add(c);
        }
        Button button = findViewById(R.id.saveCourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courseId == -1) {
                    course = new Course(0,0, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), CourseStatus.in_progress,
                            editAssignedInstructor.getText().toString(), editInstructorPhone.getText().toString(),editInstructorEmail.getText().toString(), editNotes.getText().toString());
                    repository.insert(course);
                }
                else {
                    course = new Course(courseId,termId, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(),CourseStatus.in_progress,
                            editAssignedInstructor.getText().toString(), editInstructorPhone.getText().toString(),editInstructorEmail.getText().toString(), editNotes.getText().toString());
                    repository.update(course);
                }
            }
        });

        editStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editStartDate.getText().toString();
                //String endInfo = editEndDate.getText().toString();
                if (info.equals("")) info = "09/01/23";
                //if (endInfo.equals("")) endInfo = "03/01/24";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                    //myCalendarEnd.setTime(sdf.parse(endInfo));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String endInfo = editEndDate.getText().toString();
                if (endInfo.equals("")) endInfo = "03/01/24";
                try {
                    myCalendarEnd.setTime(sdf.parse(endInfo));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                new DatePickerDialog(CourseDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelStart();
            }

        };
        endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
            }

        };



    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }
}