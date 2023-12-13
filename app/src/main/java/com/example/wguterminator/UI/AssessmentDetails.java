package com.example.wguterminator.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.wguterminator.Database.Repository;
import com.example.wguterminator.Entities.Assessment;
import com.example.wguterminator.Entities.AssessmentType;
import com.example.wguterminator.R;

public class AssessmentDetails extends AppCompatActivity {


    EditText editName;
    EditText editEndDate;
    EditText editAssessmentType;
    Assessment assessment;
    Repository repository;
    int assessId;
    String name;
    String stringEndDate;
    String assessmentTypeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        editName.findViewById(R.id.assessName);
        editEndDate.findViewById(R.id.assessEndDate);
        editAssessmentType.findViewById(R.id.assessType);
        // Need to fill this out to match in AssessmentAdapter
        assessId = getIntent().getIntExtra("assessId",-1);
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

        Button saveButton = findViewById(R.id.saveAssess);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssessmentType assessmentType = AssessmentType.unknown;
                if (assessmentTypeString != null) {
                    switch (assessmentTypeString) {
                        case "performance":
                            assessmentType = AssessmentType.performance;
                            break;
                        case "completed":
                            assessmentType = AssessmentType.objective;
                            break;
                    }
                    if (assessId == -1) {
                        // need to add associated course id
                        assessment = new Assessment(0,editName.getText().toString(),editEndDate.getText().toString(),assessmentType);
                        repository.insert(assessment);
                    } else {
                        // need to add associated course id
                        assessment = new Assessment(assessId,editName.getText().toString(),editEndDate.getText().toString(),assessmentType);
                        repository.update(assessment);
                    }
                }
            }
        });
    }
}