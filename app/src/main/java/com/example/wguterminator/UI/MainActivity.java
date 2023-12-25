package com.example.wguterminator.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.wguterminator.Database.Repository;
import com.example.wguterminator.Entities.Assessment;
import com.example.wguterminator.Entities.AssessmentType;
import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.Entities.CourseStatus;
import com.example.wguterminator.Entities.Term;
import com.example.wguterminator.Entities.User;
import com.example.wguterminator.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserLogin.class);
                startActivity(intent);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addSampleData:
                Repository repository = new Repository(getApplication());
                User user = new User(0, "MK", "password");
                repository.insert(user);
                Term term = new Term(0, "May 2023", "05-01-2023", "11-01-2023");
                repository.insert(term);
                Course course2 = new Course(0,1,"Calculus", "07/01/23", "11/01/23", CourseStatus.in_progress, "Josh Harris", "301-111-5555","riveraera@commanders.com", "coach");
                repository.insert(course2);
                Assessment assessment = new Assessment(0, 1,"Chesapeake Critters", "05/01/2023", AssessmentType.performance);
                repository.insert(assessment);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}