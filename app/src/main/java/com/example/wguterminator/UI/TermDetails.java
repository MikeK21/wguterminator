package com.example.wguterminator.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.wguterminator.Database.Repository;
import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.Entities.Term;
import com.example.wguterminator.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {

    EditText editName;
    EditText editDate;
    EditText editEndDate;
    String name;
    String date;
    String stringEndDate;
    String stringId;
    int id;
    int termId;
    EditText editId;
    Repository repository;
    Term term;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editId = findViewById(R.id.termId);
        editDate = findViewById(R.id.termDate);
        editEndDate = findViewById(R.id.termEndDate);
        editName = findViewById(R.id.termName);
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(new Date()));
        stringId = getIntent().getStringExtra("id");
        termId = getIntent().getIntExtra("termId", -1);
        name = getIntent().getStringExtra("name");
        date = getIntent().getStringExtra("date");
        stringEndDate = getIntent().getStringExtra("endDate");
        editId.setText(stringId);
        editName.setText(name);
        editDate.setText(date);
        editEndDate.setText(stringEndDate);
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Added this at end of video 3
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getmAllCourses()) {
            if (c.getTermId() == termId) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);
        Button button = findViewById(R.id.saveTerm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == -1) {
                    term = new Term(0, editName.getText().toString(), editDate.getText().toString(), editEndDate.getText().toString());
                    repository.insert(term);
                } else {
                    term = new Term(id, editName.getText().toString(), editDate.getText().toString(), editEndDate.getText().toString());
                    repository.update(term);
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetails.this, TermList.class);
                intent.putExtra("termId", id);
                startActivity(intent);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.floatingActionButtonCourses);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                intent.putExtra("courseId", id);
                startActivity(intent);
            }
        });

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editDate.getText().toString();
                //String endInfo = editEndDate.getText().toString();
                if (info.equals("")) info = "09/01/23";
                //if (endInfo.equals("")) endInfo = "03/01/24";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                    //myCalendarEnd.setTime(sdf.parse(endInfo));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, startDate, myCalendarStart
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

                new DatePickerDialog(TermDetails.this, endDate, myCalendarEnd
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

        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    @Override
        protected void onResume() {
            super.onResume();
            RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
            final CourseAdapter courseAdapter2 = new CourseAdapter(this);
            recyclerView.setAdapter(courseAdapter2);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Course> filteredCourses2 = new ArrayList<>();
            for (Course c : repository.getmAllCourses()) {
                if (c.getTermId() != 0 && c.getTermId() == termId) {
                    filteredCourses2.add(c);
                }
                else if (c.getCourseId() == id) {
                    filteredCourses2.add(c);
                }
            }
            courseAdapter2.setCourses(filteredCourses2);
        }

        //pick up at ProductDetails.java line 101 from dolphinlive github




    }