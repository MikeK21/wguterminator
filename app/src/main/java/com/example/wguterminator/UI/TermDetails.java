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
    String name;
    String date;
    int id;
    Repository repository;
    Term term;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editDate = findViewById(R.id.termDate);
        editName = findViewById(R.id.termName);
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(new Date()));
        name = getIntent().getStringExtra("name");
        date = getIntent().getStringExtra("date");
        editName.setText(name);
        editDate.setText(date);
        id = getIntent().getIntExtra("id", -1);
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.courserecyclerview);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Added this at end of video 3
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getmAllCourses()) {
            if (c.getCourseId() == id) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);
        Button button = findViewById(R.id.saveTerm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == -1) {
                    term = new Term(0, editName.getText().toString(), editDate.getText().toString());
                    repository.insert(term);
                } else {
                    term = new Term(id, editName.getText().toString(), editDate.getText().toString());
                    repository.update(term);
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetails.this, CourseList.class);
                intent.putExtra("termId", id);
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
                if (info.equals("")) info = "09/12/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
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
    }
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    @Override
        protected void onResume() {
            super.onResume();
            RecyclerView receyclerView = findViewById(R.id.courserecyclerview);
            final CourseAdapter courseAdapter2 = new CourseAdapter(this);
            receyclerView.setAdapter(courseAdapter2);
            receyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Course> filteredCourses2 = new ArrayList<>();
            for (Course c : repository.getmAllCourses()) {
                if (c.getCourseId() == id) filteredCourses2.add(c);
            }
            courseAdapter2.setCourses(filteredCourses2);
        }

        //pick up at ProductDetails.java line 101 from dolphinlive github




    }