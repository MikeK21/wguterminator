package com.example.wguterminator.UI;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


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
    AssessmentType selectedType;
    Integer selectedCourseId;
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

        //editCourse = findViewById(R.id.assessCourseId);
        editName = findViewById(R.id.assessNameDetails);
        editEndDate = findViewById(R.id.assessEndDateDetails);
        editEndDate.setText(sdf.format(new Date()));
        //editAssessmentType = findViewById(R.id.typeSpinner);
        repository = new Repository(getApplication());

        assessmentList = repository.getmAllAssessments();

        // Set up course id spinner
        ArrayList<Course> courseList = new ArrayList<>();
        ArrayList<Integer> courseIdList = new ArrayList<>();
        courseList.addAll(repository.getmAllCourses());
        for (Course course : courseList) {
            courseIdList.add(course.getCourseId());
        }
        // Set up assessment type typeSpinner
        ArrayList<AssessmentType> assessmentTypeList = new ArrayList<>();
        for (AssessmentType assessmentType: AssessmentType.values()) {
            assessmentTypeList.add(assessmentType);
        }

        ArrayAdapter<AssessmentType> assessmentTypeArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,AssessmentType.values());
        Spinner typeSpinner = findViewById(R.id.typeSpinner);
        typeSpinner.setAdapter(assessmentTypeArrayAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //editStatus.setText(courseStatusArrayAdapter.getItem(i).toString());
                selectedType = (AssessmentType) typeSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //editStatus.setText("Nothing selected");
                for (AssessmentType type : AssessmentType.values()) {
                    if (selectedType.equals(type)) {
                        selectedType = type;
                    }
                }
            }
        });

        ArrayAdapter<Integer> courseIdArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,courseIdList);
        Spinner courseIdSpinner = findViewById(R.id.courseIdSpinner);
        courseIdSpinner.setAdapter(courseIdArrayAdapter);
        courseIdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //editStatus.setText(courseStatusArrayAdapter.getItem(i).toString());
                selectedCourseId = (Integer) courseIdSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //editStatus.setText("Nothing selected");
                selectedCourseId = 0;
            }
        });

        // Check if editName is not null before calling setText
        if (editName != null) {
            editName.setText(name);
        } else {
            // Log an error or handle the situation where editName is null
            Log.e("AssessmentDetails", "editName is null");
        }

        // Check if editName is not null before calling setText
        if (editEndDate != null) {
            if (stringEndDate == null) {
                stringEndDate = sdf.format(new Date());
                editEndDate.setText(sdf.format(new Date()));
            } else {
                editEndDate.setText(stringEndDate);
            }
        }
        else {
            // Log an error or handle the situation where editName is null
            Log.e("AssessmentDetails", "editEndDate is null");
        }


        Button newAssessButton = findViewById(R.id.newAssess);
        newAssessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseIdSpinner.setSelection(0);
                editName.setText("");
                editEndDate.setText("");
                typeSpinner.setSelection(0);
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

        Button deleteButton = findViewById(R.id.deleteAssess);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (assessId != -1) {
                    Assessment deleteAssessment;
                    for (Assessment assessment : assessmentList) {
                        if (assessment.getAssessmentId() == assessId) {
                            deleteAssessment = assessment;
                            showAlertDialog("Deleting Assessment: " +assessment.getAssessmentName(), "Successful Delete");
                            repository.delete(deleteAssessment);
                            refreshScreen(-1);
                            break;
                        }
                    }
                }
            }
        });

        Button saveButton = findViewById(R.id.saveAssess);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssessmentType assessmentType = AssessmentType.objective;
                int editCourseId = -1;
                try {
                    editCourseId = selectedCourseId;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (assessmentTypeString != null) {
                    switch (assessmentTypeString) {
                        case "performance":
                            assessmentType = AssessmentType.performance;
                            break;
                    }
                }
                    if (editName.getText().toString().isEmpty()) {
                        showAlertDialog("Invalid Assessment Name", "Try Again");
                    } else if (editCourseId == -1) {
                        showAlertDialog("Invalid Associated Course", "Try Again");
                    } else if (assessmentType.toString().isEmpty()) {
                        showAlertDialog("Invalid Assessment Type", "Try Again");
                    } else if (editEndDate.getText().toString().isEmpty()) {
                        showAlertDialog("Invalid Dates", "Try Again");
                    } else if (!isValidDate(sdf, editEndDate.getText().toString())) {
                        showAlertDialog("Invalid Dates", "Try Again");
                    }
                    // Add new assessment
                    if (assessId == -1) {
                        editCourseId = selectedCourseId;
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
                            showAlertDialog("Successful Assessment Creation: "
                            + assessment.getAssessmentName(), "Successful Add");
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
                        editCourseId = selectedCourseId;

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
                            showAlertDialog("Successful Assessment Update: "
                                    + assessment.getAssessmentName(), "Successful Update");
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
                //if (info.equals("")) info = "09/01/23";
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

    public static boolean isValidDate(SimpleDateFormat sdf, String dateString) {

        try {
            Date date = sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void refreshScreen(int assessId) {
        editName.setText("");
        editEndDate.setText("");
        this.assessId = assessId;
    }
}