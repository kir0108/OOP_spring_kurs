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

import com.university.client.api.JsonPlaceHolderApi;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        button = findViewById(R.id.button_signin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openListActivity();
            }
        });
        textView = findViewById(R.id.button_to_signup);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpActivity();
            }
        });
    }

    public void openSignUpActivity(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void openListActivity(){
        EditText login = findViewById(R.id.text_login_in);
        EditText password = findViewById(R.id.text_pwd_in);

        String name = login.getText().toString();
        String pwd = password.getText().toString();

        if (name.isEmpty() || pwd.isEmpty()){
            Toast toast = Toast.makeText(context, "Uncorrected login or password", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Authorization authorization = new Authorization();
        authorization.auth(name, pwd, context);
    }
}


