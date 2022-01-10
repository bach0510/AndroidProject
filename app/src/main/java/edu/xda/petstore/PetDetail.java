package edu.xda.petstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.xda.petstore.database.PetDatabase;
import edu.xda.petstore.model.Cart;

public class PetDetail extends AppCompatActivity {

    ImageView anh;
    TextView tenGiong , moTa, donGia;

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

        findViewById(R.id.cart_detail).setOnClickListener(new View.OnClickListener() { // bắt sự kiện mở menu bằng menu icon
            @Override
            public void onClick(View view) {
                Cart cartInfo = new Cart(Login.currentUser.getId(),i.getExtras().getInt("id"));
                PetDatabase.getInstance(PetDetail.this).cartDao().insertCart(cartInfo);
                Toast.makeText(PetDetail.this,"Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                //Intent i = new Intent(PetDetail.this, Home.class);
                //i.putExtra("number",coutNumberPetInCart());
//                Intent i = new Intent(PetDetail.this, Home.class);
//                PetDetail.this.startActivity(i);
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