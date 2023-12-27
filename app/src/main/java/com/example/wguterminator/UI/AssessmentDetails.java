package com.example.wguterminator.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


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


    EditText editCourse;
    EditText editName;
    EditText editEndDate;
    EditText editAssessmentType;
    Assessment assessment;
    Repository repository;
    int assocAssessments;
    int assessId;
    int courseId;
    List<Assessment> assessmentList;
    List<Course> courseList;
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
        Log.d("Debug", "About to set content view");
        setContentView(R.layout.activity_assessment_details);
        Log.d("Debug", "Content view set successfully");
        // Need to fill this out to match in AssessmentAdapter
        assessId = getIntent().getIntExtra("assessId",-1);
        courseId = getIntent().getIntExtra("courseId", -1);
        name = getIntent().getStringExtra("name");
        stringEndDate = getIntent().getStringExtra("endDate");
        assessmentTypeString = getIntent().getStringExtra("assessType");

        editCourse = findViewById(R.id.assessCourseId);
        editName = findViewById(R.id.assessNameDetails);
        editEndDate = findViewById(R.id.assessEndDateDetails);
        editAssessmentType = findViewById(R.id.assessTypeDetails);
        repository = new Repository(getApplication());

        // Check if editCourse is not null before calling setText
        if (editCourse != null) {
            editCourse.setText(String.valueOf(courseId));
        } else {
            // Log an error or handle the situation where editName is null
            Log.e("AssessmentDetails", "editCourse is null");
        }

        // Check if editName is not null before calling setText
        if (editName != null) {
            editName.setText(name);
        } else {
            // Log an error or handle the situation where editName is null
            Log.e("AssessmentDetails", "editName is null");
        }

        // Check if editName is not null before calling setText
        if (editEndDate != null) {
            editEndDate.setText(stringEndDate);
        } else {
            // Log an error or handle the situation where editName is null
            Log.e("AssessmentDetails", "editEndDate is null");
        }

        // Check if editName is not null before calling setText
        if (editAssessmentType != null) {
            editAssessmentType.setText(assessmentTypeString);
        } else {
            // Log an error or handle the situation where editName is null
            Log.e("AssessmentDetails", "editAssessmentType is null");
        }

        Button newAssessButton = findViewById(R.id.newAssess);
        newAssessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCourse.setText("");
                editName.setText("");
                editEndDate.setText("");
                editAssessmentType.setText("");
                assessId = -1;
            }
        });

        Button assessListButton = findViewById(R.id.seeAssess);
        assessListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentDetails.this, AssessmentList.class);
                intent.putExtra("assessId", assessId);
                startActivity(intent);
            }
        });

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
                    // Add new assessment
                    if (assessId == -1) {
                        int editCourseId = Integer.parseInt(editCourse.getText().toString());
                        // need to add associated course id
                        assessment = new Assessment(0, editCourseId,editName.getText().toString(),editEndDate.getText().toString(),assessmentType);
                        //assocAssessments = repository.getmAllAssessForACourse(assessment.getCourseId());

                        if (getAssocAssessments(editCourseId) >= 5 ) {
                            showAlertDialog("Cannot add assessment as Course already has" +
                                    " or will have more than 5 assessments!", "Course Conflict Error");
                        }
                        else if (!checkIfCourseExists(editCourseId)) {
                            showAlertDialog("Cannot add assessment as Course doesn't exist!", "Course doesn't exist error");
                        }
                        else {
                            repository.insert(assessment);
                        }
                        /*
                        if (assocAssessments >= 5) {
                            showAlertDialog("Cannot add assessment as Course already has" +
                                    " or will have more than 5 assessments!", "Course Conflict Error");
                        }
                        else {
                            repository.insert(assessment);
                        }
                        */
                        // Update assessment
                    } else {
                        // need to add associated course id
                        int editCourseId = Integer.parseInt(editCourse.getText().toString());

                        assessment = new Assessment(assessId, editCourseId, editName.getText().toString(),editEndDate.getText().toString(),assessmentType);
                        //assocAssessments = repository.getmAllAssessForACourse(assessment.getCourseId());
                        if (getAssocAssessments(editCourseId) >= 5 ) {
                            showAlertDialog("Cannot add assessment as Course already has" +
                                    " or will have more than 5 assessments!", "Course Conflict Error");
                        }
                        else if (!checkIfCourseExists(editCourseId)) {
                            showAlertDialog("Cannot add assessment as Course doesn't exist!", "Course doesn't exist error");
                        }
                        else {
                            repository.update(assessment);
                        }
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.notifyStartOption:
                String dateFromScreen = editEndDate.getText().toString();
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate = null;
                try {
                    myDate = sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", dateFromScreen + " should trigger");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            //case R.id.notifyEndOption:
             //   return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog(String message, String title) {
        AlertDialog dialog =  new AlertDialog.Builder(AssessmentDetails.this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();

    }

    public int getAssocAssessments (int inFocusCourseId) {
        int assocCourseCounter = 0;
        assessmentList = repository.getmAllAssessments();
        for (Assessment assessment : assessmentList) {
            if (inFocusCourseId == assessment.getCourseId()) {
                assocCourseCounter++;
            }
        }
        return assocCourseCounter;
    }

    public boolean checkIfCourseExists (int inFocusCourseId) {
        courseList = repository.getmAllCourses();
        for (Course course : courseList) {
            if (inFocusCourseId == course.getCourseId()) {
                return true;
            }
        }
        return false;

    }
}