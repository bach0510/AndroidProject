package edu.xda.petstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import edu.xda.petstore.database.PetDatabase;
import edu.xda.petstore.model.User;

public class SignUp extends AppCompatActivity {

    TextInputEditText nameTxt  , usernameTxt , passwordTxt;

    boolean validateBeforeSave(){// validate thông tin đăng ký trước khi insert mới từ thông tin vừa nhập
        if (TextUtils.isEmpty(nameTxt.getText())) {
            Toast.makeText(SignUp.this, "Họ tên không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(usernameTxt.getText())) {
            Toast.makeText(SignUp.this, "Tên đăng nhập không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(passwordTxt.getText())) {
            Toast.makeText(SignUp.this, "Mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(PetDatabase.getInstance(SignUp.this).userDao().checkExistWhenCreate(usernameTxt.getText().toString()).size() > 0){
            Toast.makeText(SignUp.this, "Tên đăng nhập đã tồn tại vui lòng chọn 1 tên đăng nhập khác", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void hideKeyboard(Context context, View view) {// hide bàn phím
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Home.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameTxt = findViewById(R.id.name_signup);

        usernameTxt = findViewById(R.id.uname_signup);
        passwordTxt = findViewById(R.id.pass_signup);


        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() { // bắt sự kiện cho nutrs đăng ký
            @Override
            public void onClick(View view) {
                if (validateBeforeSave()== true){// check validate trước khi insert thông tin đăng ký
                    // tạo user mới từ nhưng thông tin mới nhập
                    User user = new User(0,usernameTxt.getText().toString(),passwordTxt.getText().toString(),nameTxt.getText().toString(),"","");
//                    user.setName(nameTxt.getText().toString());
//                    user.setTel("");
//                    user.setAddress("");
//                    user.setUsername(usernameTxt.getText().toString());
//                    user.setPassword(passwordTxt.getText().toString());

                    hideKeyboard(SignUp.this,view);// hide bàn phím
                    PetDatabase.getInstance(SignUp.this).userDao().insertUser(user);// insert user mới vào trong database
                    Toast.makeText(SignUp.this, "Đã đăng ký thành công", Toast.LENGTH_SHORT).show(); // thông báo đăng ký thành công
                    finish(); // finish activity hiện taị và quay lại activity trước đó (activity màn login )


                }
            }
        });

        findViewById(R.id.cancel_signup).setOnClickListener(new View.OnClickListener() { // bắt sự kiện nút hủy
            @Override
            public void onClick(View view) {
                finish();// finish activity hiện ttại và quay lại activity trước đó (activity màn login )
//                Intent i = new Intent(SignUp.this, Home.class);
//                SignUp.this.startActivity(i);
            }
        });
    }
}