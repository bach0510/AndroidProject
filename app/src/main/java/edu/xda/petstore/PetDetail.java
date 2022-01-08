package edu.xda.petstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PetDetail extends AppCompatActivity {

    ImageView anh;
    TextView tenGiong , moTa, donGia;
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

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() { // bắt sự kiện mở menu bằng menu icon
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PetDetail.this, Home.class);
                PetDetail.this.startActivity(i);
            }
        });
    }
}