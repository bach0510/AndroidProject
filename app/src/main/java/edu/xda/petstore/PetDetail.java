package edu.xda.petstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.xda.petstore.adapter.CartAdapter;
import edu.xda.petstore.database.PetDatabase;
import edu.xda.petstore.model.Cart;
import edu.xda.petstore.model.User;

public class PetDetail extends AppCompatActivity {

    ImageView anh;
    TextView tenGiong , moTa, donGia;

    AlertDialog dialog;
    AlertDialog.Builder builder;

    int coutNumberPetInCart(){
//        int userId = getIntent().getExtras().getInt("userId");
        int userId = Login.currentUser.getId();
        return PetDatabase.getInstance(this).cartDao().searchCart(userId).size();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        Intent i = getIntent();

        i.getStringExtra("tenGiong");
        i.getStringExtra("moTa");
        i.getExtras().getInt("donGia");
        i.getExtras().getByteArray("anh");

        anh = findViewById(R.id.pet_image_detail);
        tenGiong = findViewById(R.id.pet_name_detail);
        moTa = findViewById(R.id.pet_info_detail);
        donGia = findViewById(R.id.pet_price_detail);

        tenGiong.setText(i.getStringExtra("tenGiong"));
        moTa.setText(i.getStringExtra("moTa"));
        donGia.setText(i.getExtras().getInt("donGia") + " VNĐ");

        if (i.getExtras().getByteArray("anh") != null){
            byte[] blob= Base64.decode( i.getExtras().getByteArray("anh"), Base64.DEFAULT);
            Bitmap bmp= BitmapFactory.decodeByteArray(blob,0,blob.length);
//        ImageView image=new ImageView(this);
            anh.setImageBitmap(bmp);
        }

        findViewById(R.id.cart_detail).setOnClickListener(new View.OnClickListener() { // bắt
            @Override
            public void onClick(View view) {
                //tạo dialog
                builder = new AlertDialog.Builder(PetDetail.this);
                builder.setTitle("Bạn có chắc muốn thêm vào giỏ hàng ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent i2 = getIntent();
                        Cart cartExist = PetDatabase.getInstance(PetDetail.this).cartDao().findCart(Login.currentUser.getId(),i2.getExtras().getInt("id"));
                        Cart cartInfo = new Cart(Login.currentUser.getId(),i2.getExtras().getInt("id"),1);
                        if (cartExist == null){
                            PetDatabase.getInstance(PetDetail.this).cartDao().insertCart(cartInfo);
                        }
                        else {
                            int qty = cartExist.getQty();
                            cartExist.setQty(qty += 1);
                            PetDatabase.getInstance(PetDetail.this).cartDao().updateCart(cartExist);
                        }

                        Toast.makeText(PetDetail.this,"Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog = builder.create();
                dialog.show();


            }
        });

        findViewById(R.id.buy_detail).setOnClickListener(new View.OnClickListener() { // bắt sự kiện mở menu bằng menu icon
            @Override
            public void onClick(View view) {
                //tạo dialog
                builder = new AlertDialog.Builder(PetDetail.this);
                builder.setTitle("Xác nhận thông tin liên hệ");

                // tạo input cho dialog xác nhận mua
                final EditText tel = new EditText(PetDetail.this);
                final EditText address = new EditText(PetDetail.this);
                final TextView telTxt = new TextView(PetDetail.this);
                final TextView addressTxt = new TextView(PetDetail.this);
                final Button confirm = new Button(PetDetail.this);
                confirm.setText("Xác nhận");


                telTxt.setText("Số điện thoại liên hệ");
                addressTxt.setText("Địa chỉ liên hệ/ nhận hàng");
                tel.setText(Login.currentUser.getTel());
                address.setText(Login.currentUser.getAddress());
                LinearLayout layout = new LinearLayout(PetDetail.this);

                layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(telTxt);
                layout.addView(tel);
                layout.addView(addressTxt);
                layout.addView(address);
                layout.addView(confirm);
                builder.setView(layout);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(tel.getText()) || TextUtils.isEmpty(address.getText())){
                            Toast.makeText(PetDetail.this,"Vui lòng cung cấp đầy đủ thông tin xác nhận trước khi mua hàng", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            User u = Login.currentUser;
                            u.setAddress(address.getText().toString());
                            u.setTel(tel.getText().toString());
                            PetDatabase.getInstance(PetDetail.this).userDao().updateUser(u);
                            dialog.dismiss();
                            Toast.makeText(PetDetail.this,"Cám ơn đã mua hàng của cửa hàng PetStore , chúng tôi sẽ liên hệ và giao hàng cho bạn sớm nhất có thể", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

                dialog = builder.create();
                dialog.show();


            }
        });

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent i = new Intent(PetDetail.this, Home.class);
//                PetDetail.this.startActivity(i);
            }
        });
    }
}