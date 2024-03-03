package com.example.wguterminator.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wguterminator.Database.Repository;
import com.example.wguterminator.Entities.User;
import com.example.wguterminator.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class UserLogin extends AppCompatActivity {

    public Repository repository;
    String user;
    String password;

    public void handleIntent(Intent intent) {
        onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        TextInputEditText editUser = findViewById(R.id.usernameLoginField);
        TextInputEditText editPassword = findViewById(R.id.passwordLoginField);
        user = getIntent().getStringExtra("user");
        password = getIntent().getStringExtra("password");
        editUser.setText(user);
        editPassword.setText(password);
        Button button = findViewById(R.id.loginButton);
        repository = new Repository(getApplication());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> userList = repository.getmAllUsers();
                String editUserText = editUser.getText().toString();
                Optional<User> userMatch = userList.stream().filter(x -> editUserText.equals(x.getUserName())).findFirst();
                if (userMatch.isPresent()) {
                    User currentUser = userMatch.get();
                    showAlertDialog("User is Logged In: " + currentUser.getUserName(), "Logged In");
                    Intent intent = new Intent(UserLogin.this, TermDetails.class);
                    startActivity(intent);
                } else {
                    showAlertDialog("Unable to find User", "No User Match Found");
                }
            }
        });
    }


    /**
     * Set up Alert Dialog for entire class
     * @param message
     * @param title
     */
    public void showAlertDialog(String message, String title) {
        AlertDialog dialog =  new AlertDialog.Builder(UserLogin.this)
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
}
