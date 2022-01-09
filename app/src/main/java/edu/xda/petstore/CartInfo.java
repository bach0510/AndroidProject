package edu.xda.petstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import edu.xda.petstore.adapter.PetAdapter;
import edu.xda.petstore.database.PetDatabase;
import edu.xda.petstore.model.Pet;

public class CartInfo extends AppCompatActivity {

    RecyclerView recyclerView;
    PetAdapter petAdapter;
    List<Pet> petList;

    void searchCart(int userId){
        petList = PetDatabase.getInstance(this).petDao().searchCartInfo(userId);
        setRecyclerView(petList);
    }

    private void setRecyclerView(List<Pet> petList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        petAdapter = new PetAdapter(this,petList);
        recyclerView.setAdapter(petAdapter);
    }

    public void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Home.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_info);

        recyclerView = findViewById(R.id.rcv_home);

        Intent i = getIntent();
        searchCart(Login.currentUser.getId());


        findViewById(R.id.back_button3).setOnClickListener(new View.OnClickListener() { // bắt sự kiện mở menu bằng menu icon
            @Override
            public void onClick(View view) {
                finish();
//                Intent i = new Intent(PetTypeList.this, Home.class);
//                PetTypeList.this.startActivity(i);
            }
        });
    }
}