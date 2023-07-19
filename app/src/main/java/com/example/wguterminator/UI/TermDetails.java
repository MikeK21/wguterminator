package com.example.wguterminator.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wguterminator.Database.Repository;
import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.Entities.Term;
import com.example.wguterminator.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TermDetails extends AppCompatActivity {

    EditText editName;
    EditText editDate;
    String name;
    String date;
    int id;
    Repository repository;
    Term term;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editDate = findViewById(R.id.termDate);
        editName = findViewById(R.id.termName);
        name = getIntent().getStringExtra("name");
        date = getIntent().getStringExtra("date");
        editName.setText(name);
        editDate.setText(date);
        Button button = findViewById(R.id.saveTerm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == -1) {
                    term = new Term(0, editName.getText().toString(), editDate.getText().toString());
                    repository.insert(term);
                }
                else {
                    term = new Term(id, editName.getText().toString(), editDate.getText().toString());
                    repository.update(term);
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetails.this,CourseList.class);
                startActivity(intent);
            }
        });
    }
}