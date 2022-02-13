package edu.xda.petstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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

    public static User currentUser = null;

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Home.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static User getCurrentUser(){
        return currentUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        username = findViewById(R.id.username_txt);
        password = findViewById(R.id.password_txt);

        // set sự kiện click cho nút đăng nhập
        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(!isNetworkConnected()){// neeus khong co internet thi khong cho login 
                    Toast.makeText(Login.this,"Vui lòng check lại internet", Toast.LENGTH_SHORT).show();
                }
                else{
                    // tìm kiếm user bằng username và password
                    currentUser = PetDatabase.getInstance(Login.this).userDao().searchUserByUserNameAndPass(username.getText().toString(),password.getText().toString());
                    if(currentUser != null){ // nếu tìm user bằng tài khoản ko bị null thì chuyển sang màn home
                        Intent i = new Intent(Login.this, Home.class);
                        i.putExtra("userId",currentUser.getId());// truyển userId sang màn homme
                        Login.this.startActivity(i); // start activity (mở intent màn home)
                    }
                    else { //Nếu không tìm ra user thì thông báo thông tin tài khoản bị lỗi
                        Toast.makeText(Login.this,"Vui lòng check lại thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        findViewById(R.id.sign_up_btn).setOnClickListener(new View.OnClickListener(){ // bắt sự kiện click cho nút đăng ký

            @Override
            public void onClick(View view) {
                // mở màn đăng ký
                    Intent i = new Intent(Login.this, SignUp.class);
                    Login.this.startActivity(i);

            }
        });


    }
}