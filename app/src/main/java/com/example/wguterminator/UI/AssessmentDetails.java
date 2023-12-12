package com.example.wguterminator.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;


import com.example.wguterminator.Entities.AssessmentType;
import com.example.wguterminator.R;

public class AssessmentDetails extends AppCompatActivity {


    EditText editName;
    EditText editEndDate;
    EditText editAssessmentType;
    String name;
    String stringEndDate;
    String assessmentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        editName.findViewById(R.id.assessName);
        editEndDate.findViewById(R.id.assessEndDate);
        editAssessmentType.findViewById(R.id.assessType);
        // Need to fill this out to match in AssessmentAdapter
        name = getIntent().getStringExtra("name");
        stringEndDate = getIntent().getStringExtra("endDate");
        assessmentType = getIntent().getStringExtra("type");
    }
}