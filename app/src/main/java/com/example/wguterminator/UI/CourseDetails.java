package com.example.wguterminator.UI;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.Entities.CourseStatus;
import com.example.wguterminator.Entities.Term;
import com.example.wguterminator.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {

    EditText editName;
    EditText editTerm;
    EditText editStartDate;
    EditText editEndDate;
    EditText editNotes;
    EditText editAssignedInstructor;
    EditText editStatus;
    EditText editInstructorName;
    EditText editInstructorEmail;
    EditText editInstructorPhone;
    CourseStatus selectedStatus;
    int courseId;
    int termId;
    String selectedTermName;
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
        editNotes = findViewById(R.id.courseNotes);
        editAssignedInstructor = findViewById(R.id.assignedInstructor);
        editInstructorEmail = findViewById(R.id.courseInstructorEmail);
        editInstructorPhone = findViewById(R.id.courseInstructorPhone);
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate = findViewById(R.id.courseStartDate);
        editStartDate.setText(sdf.format(new Date()));
        editEndDate = findViewById(R.id.courseEndDate);
        editEndDate.setText(sdf.format(new Date()));
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
        editName.setText(name);
        editNotes.setText(note);
        editStartDate.setText(stringStartDate);
        editEndDate.setText(stringEndDate);
        editAssignedInstructor.setText(instructorName);
        editInstructorEmail.setText(instructorEmail);
        editInstructorPhone.setText(instructorPhone);
        repository = new Repository(getApplication());
        List<Term> assocTerms = repository.getmTermByTermId(termId);
        int assocTermNameResultCount =assocTerms.size();
        if (assocTermNameResultCount > 0) {
            Term selectedTerm = assocTerms.get(0);
            selectedTermName = selectedTerm.getTermName();
        } else {
            selectedTermName = "";
        }

        RecyclerView recyclerView = findViewById(R.id.assessrecyclerview);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Term ID Spinner
        ArrayList<Term> termList = new ArrayList<>();
        ArrayList<String> termNameList = new ArrayList<>();
        termList.addAll(repository.getmAllTerms());
        for (Term term : termList) {
            termNameList.add(term.getTermName());
        }

        ArrayAdapter<String> termIdArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,termNameList);
        Spinner termNameSpinner = findViewById(R.id.termNameSpinner);
        termNameSpinner.setAdapter(termIdArrayAdapter);
        termNameSpinner.setSelection(0);
        termNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTermName = (String) termNameSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedTermName = (String) termNameSpinner.getSelectedItem();
            }
        });
        // Set up Course Status Spinner
        ArrayList<Course> courseList = new ArrayList<>();
        ArrayList<CourseStatus> courseStatusList = new ArrayList<>();
        courseList.addAll(repository.getmAllCourses());
        for (CourseStatus courseStatus: CourseStatus.values()) {
            courseStatusList.add(courseStatus);
        }

        ArrayAdapter<CourseStatus> courseStatusArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,CourseStatus.values());
        Spinner spinner = findViewById(R.id.statusSpinner);
        spinner.setAdapter(courseStatusArrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedStatus = (CourseStatus) spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                for (CourseStatus courseStatus : CourseStatus.values()) {
                    if (courseStatus.equals(status)) {
                        selectedStatus = courseStatus;
                    }
                }
            }
        });

        Button deleteButton = findViewById(R.id.deleteCourse);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courseId != -1) {
                    Course deleteCourse;
                    for (Course course : courseList) {
                        if (course.getCourseId() == courseId) {
                            deleteCourse = course;
                            showAlertDialog("Deleting Term: " +course.getCourseName(), "Successful Delete");
                            repository.delete(deleteCourse);
                            refreshScreen(-1);
                            break;
                            }
                        }
                    }
                }
        });

        Button newFab = findViewById(R.id.newCourse);
        newFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termNameSpinner.setSelection(0);
                spinner.setSelection(0);
                refreshScreen(-1);
            }
        });

        Button seeAssessFab = findViewById(R.id.seeAssessments);
        seeAssessFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetails.this, AssessmentList.class);
                startActivity(intent);
            }
        });

        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getmAllCourses()) {
            if (c.getCourseId() == courseId) filteredCourses.add(c);
        }

        Button button = findViewById(R.id.saveCourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    termId = repository.getmTermIdByTermName(selectedTermName).get(0).getTermId();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (editName.getText().toString().isEmpty()) {
                    showAlertDialog("Invalid Course Name", "Try Again");
                } else if (editAssignedInstructor.getText().toString().isEmpty()) {
                    showAlertDialog("Invalid Instructor Name", "Try Again");
                } else if (editInstructorEmail.getText().toString().isEmpty()) {
                    showAlertDialog("Invalid Instructor Email", "Try Again");
                } else if (editInstructorPhone.getText().toString().isEmpty()) {
                    showAlertDialog("Invalid Instructor Phone Number", "Try Again");
                } else if (selectedStatus.toString().isEmpty()) {
                    showAlertDialog("Invalid Course Status", "Try Again");
                } else if (editStartDate.getText().toString().isEmpty() || editEndDate.getText().toString().isEmpty()) {
                    showAlertDialog("Invalid Dates", "Try Again");
                } else if (!isValidDate(sdf,editStartDate.getText().toString()) || !isValidDate(sdf, editEndDate.getText().toString())) {
                    showAlertDialog("Invalid Dates", "Try Again");
                }
                if (courseId == -1) {
                    course = new Course(0,termId, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), selectedStatus,
                            editAssignedInstructor.getText().toString(), editInstructorPhone.getText().toString(),editInstructorEmail.getText().toString(), editNotes.getText().toString());
                    repository.insert(course);
                    showAlertDialog("Adding Course: " +course.getCourseName(), "Successful Add");

                }
                else {
                    course = new Course(courseId,termId, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(),selectedStatus,
                            editAssignedInstructor.getText().toString(), editInstructorPhone.getText().toString(),editInstructorEmail.getText().toString(), editNotes.getText().toString());
                    repository.update(course);
                    showAlertDialog("Updating Course: " +course.getCourseName(), "Successful Update");
                }
            }
        });

        /**
         * Handle the Start Date
         */
        editStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                String info = editStartDate.getText().toString();
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /**
         * Handle End Date
         */
        editEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                String endInfo = editEndDate.getText().toString();
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

    /**
     * Update start date
     */
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    /**
     * Update end date
     */
    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.assessrecyclerview);
        final AssessmentAdapter assessmentAdapter2 = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter2);
        recyclerView.setClickable(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssess2 = new ArrayList<>();
        for (Assessment a : repository.getmAllAssessments()) {
            if (a.getCourseId() != 0 && a.getCourseId() == courseId) {
                filteredAssess2.add(a);
            }
            else if (a.getAssessmentId() == courseId) {
                filteredAssess2.add(a);
            }
        }
        assessmentAdapter2.setAssessments(filteredAssess2);
    }

    /**
     * Set up the Course Details Menu
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;
    }

    /**
     * Setup the notifications for start and end date
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date myDate = null;


        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.shareOption:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editNotes.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notifyStartOption:
                String dateFromScreen = editStartDate.getText().toString();
                try {
                    myDate = sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long startTrigger = myDate.getTime();
                Intent startIntent = new Intent(CourseDetails.this, MyReceiver.class);
                startIntent.putExtra("key", dateFromScreen  + " " + editName.getText() + " Start Date");
                PendingIntent startSender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, startIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);
                return true;
            case R.id.notifyEndOption:
                String endDateFromScreen = editEndDate.getText().toString();
                try {
                    myDate = sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long endTrigger = myDate.getTime();
                Intent endIntent = new Intent(CourseDetails.this, MyReceiver.class);
                endIntent.putExtra("key", endDateFromScreen  + " " +editName.getText() + " End Date");
                PendingIntent endSender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
                return true;        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up Alert Dialog for entire class
     * @param message
     * @param title
     */
    private void showAlertDialog(String message, String title) {
        AlertDialog dialog =  new AlertDialog.Builder(CourseDetails.this)
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

    /**
     * Check if the date in question is valid and parseable
     * @param sdf
     * @param dateString
     * @return
     */
    public static boolean isValidDate(SimpleDateFormat sdf, String dateString) {

        try {
            Date date = sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Refresh the screen
     * @param courseId
     */
    private void refreshScreen(int courseId) {
        editName.setText("");
        editEndDate.setText("");
        editStartDate.setText("");
        editAssignedInstructor.setText("");
        editInstructorEmail.setText("");
        editInstructorPhone.setText("");
        editNotes.setText("");
        //selectedStatus = CourseStatus.in_progress;
        this.courseId = courseId;
    }

}
