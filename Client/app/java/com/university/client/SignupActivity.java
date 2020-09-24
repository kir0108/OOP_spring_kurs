package com.university.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        context = this;

        button = findViewById(R.id.button_signup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openListActivity();
            }
        });

        textView = findViewById(R.id.button_to_signin);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignInActivity();
            }
        });
    }

    public void openSignInActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openListActivity(){
        EditText login = findViewById(R.id.text_login_up);
        EditText password1 = findViewById(R.id.text_pwd_up_1);
        EditText password2 = findViewById(R.id.text_pwd_up_2);

        String name = login.getText().toString();
        String pwd1 = password1.getText().toString();
        String pwd2 = password2.getText().toString();

        if (name.isEmpty() || pwd1.isEmpty() || pwd2.isEmpty()){
            Toast toast = Toast.makeText(context, "Uncorrected login or password", Toast.LENGTH_SHORT);
            toast.show();
            return;
        } else if(!pwd1.equals(pwd2)) {
            Toast toast = Toast.makeText(context, "Uncorrected password", Toast.LENGTH_SHORT);
            toast.show();
            return;
        } else {
            Authorization authorization = new Authorization();
            authorization.registration(name, pwd1, context);
        }
    }
}