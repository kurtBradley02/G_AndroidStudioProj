package com.example.testactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername, txtPassword;
    private TextView txtUsernameError, txtPasswordError;
    private Button btnTogglePassword;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtUsernameError = findViewById(R.id.txtUsernameError);
        txtPasswordError = findViewById(R.id.txtPasswordError);
        btnTogglePassword = findViewById(R.id.btnTogglePassword);

        btnTogglePassword.setOnClickListener(v -> togglePasswordVisibility());
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            btnTogglePassword.setText("Show");
        } else {
            txtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            btnTogglePassword.setText("Hide");
        }
        txtPassword.setSelection(txtPassword.getText().length());
        isPasswordVisible = !isPasswordVisible;
    }

    public void login(View v) {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        clearErrors();

        boolean isValid = true;

        if (!username.equals("admin")) {
            txtUsernameError.setVisibility(View.VISIBLE);
            txtUsername.setBackgroundResource(R.drawable.edit_text_error);
            isValid = false;
        }

        if (!password.equals("123456")) {
            txtPasswordError.setVisibility(View.VISIBLE);
            txtPassword.setBackgroundResource(R.drawable.edit_text_error);
            isValid = false;
        }

        if (isValid) {
            Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, GesturesPractice.class);
            startActivity(intent);
        }

        hideKeyboard();
    }

    private void clearErrors() {
        txtUsernameError.setVisibility(View.GONE);
        txtPasswordError.setVisibility(View.GONE);
        txtUsername.setBackgroundResource(android.R.drawable.edit_text);
        txtPassword.setBackgroundResource(android.R.drawable.edit_text);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
