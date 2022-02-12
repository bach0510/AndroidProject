package edu.xda.petstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.xda.petstore.database.PetDatabase;
import edu.xda.petstore.model.User;

public class Personal extends AppCompatActivity {

    TextView nameTxt , telTxt , addressTxt , usernameTxt , passwordTxt;

    boolean validateBeforeSave(){ // validate các thông tin cá nhân trước khi lưu
        if (TextUtils.isEmpty(nameTxt.getText())) {
            Toast.makeText(Personal.this, "Họ tên không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(usernameTxt.getText())) {
            Toast.makeText(Personal.this, "Tên đăng nhập không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(passwordTxt.getText())) {
            Toast.makeText(Personal.this, "Mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        // câu if dưới check xem trong database đã có người dùng này chưa thì thông báo người dùng đã tồn tại
        if(PetDatabase.getInstance(Personal.this).userDao().checkExist(usernameTxt.getText().toString(),Login.currentUser.getId()).size() > 0){
            Toast.makeText(Personal.this, "Tên đăng nhập đã tồn tại vui lòng chọn 1 tên đăng nhập khác", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_personal);

        nameTxt = findViewById(R.id.name_txt);
        telTxt = findViewById(R.id.tel_txt);
        addressTxt = findViewById(R.id.address_txt);
        usernameTxt = findViewById(R.id.uname_text);
        passwordTxt = findViewById(R.id.pwd_text);

        nameTxt.setText(Login.currentUser.getName());
        telTxt.setText(Login.currentUser.getTel());
        addressTxt.setText(Login.currentUser.getAddress());
        usernameTxt.setText(Login.currentUser.getUsername());
        passwordTxt.setText(Login.currentUser.getPassword());

        findViewById(R.id.update_btn).setOnClickListener(new View.OnClickListener() {// set sự kiện onclick cho nút cập nhật thông tin cá nhân
            @Override
            public void onClick(View view) {
                if (validateBeforeSave()== true){
                    User user = Login.currentUser;
                    user.setName(nameTxt.getText().toString());
                    user.setTel(telTxt.getText().toString());
                    user.setAddress(addressTxt.getText().toString());
                    user.setUsername(usernameTxt.getText().toString());
                    user.setPassword(passwordTxt.getText().toString());

                    hideKeyboard(Personal.this,view);
                    PetDatabase.getInstance(Personal.this).userDao().updateUser(user);// lưu thông tin người dùng
                    Toast.makeText(Personal.this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();


                }
            }
        });

        findViewById(R.id.back_button4).setOnClickListener(new View.OnClickListener() {// set sự kiện cho nút back
            @Override
            public void onClick(View view) {
//                finish();
                Intent i = new Intent(Personal.this, Home.class);// mở Home
                Personal.this.startActivity(i);
            }
        });
    }
}