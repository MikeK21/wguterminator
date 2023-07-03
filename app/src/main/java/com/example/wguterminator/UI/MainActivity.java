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
import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.Entities.Term;
import com.example.wguterminator.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TermDetails.class);
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
                Term term = new Term(0, "May 2023", "05-01-2023");
                Repository repository = new Repository(getApplication());
                repository.insert(term);
                Course course = new Course(0,"Biology of the Chesapeake", "599.99");
                repository.insert(course);
                Assessment assessment = new Assessment(0, "Chesapeake Critters", "742.99");
                repository.insert(assessment);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}