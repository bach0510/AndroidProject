package edu.xda.petstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import edu.xda.petstore.database.PetDatabase;
import edu.xda.petstore.model.User;

public class Login extends AppCompatActivity {

    Button login;

    TextInputEditText username, password;

    public User currentUser = null;

    public void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Home.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        username = findViewById(R.id.username_txt);
        password = findViewById(R.id.password_txt);
        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                currentUser = PetDatabase.getInstance(Login.this).userDao().searchUserByUserNameAndPass(username.getText().toString(),password.getText().toString());
                if(currentUser != null){
                    Intent i = new Intent(Login.this, Home.class);
                    Login.this.startActivity(i);
                }
                else {
                    Toast.makeText(Login.this,"Vui lòng check lại thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}