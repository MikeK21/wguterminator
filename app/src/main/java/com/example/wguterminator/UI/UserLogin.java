package com.example.wguterminator.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
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
                String editUserText = editUser.getText().toString();
                User currentUser = validateCurrentUserObject(editUserText);
                if (currentUser != null) {
                    if (!currentUser.getUserName().isEmpty()) {
                        showAlertDialog("User is Logged In: " + currentUser.getUserName(), "Logged In");
                        Intent intent = new Intent(UserLogin.this, TermDetails.class);
                        intent.putExtra("user", currentUser.getUserName());
                        startActivity(intent);
                    }
                }
            }
        });
    }


    public User validateCurrentUserObject(String user) {
        List<User> userList = repository.getmAllUsers();

        Optional<User> processedUser = userList.stream().filter(x -> user.equals(x.getUserName())).findFirst();

        if (processedUser != null) {
            if (processedUser.isPresent()) {
                if (!processedUser.get().getUserName().isEmpty()) {
                    User currentUser = processedUser.get();
                    return currentUser;
                } else {
                    showAlertDialog("Unable to find User", "No User Match Found");
                    return null;
                }
            } else {
                showAlertDialog("Unable to find User", "No User Match Found");
                return null;
            }
        }
        else {
            showAlertDialog("Unable to find User", "No User Match Found");
            return null;
        }
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
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();

    }


}
