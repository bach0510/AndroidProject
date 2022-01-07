package edu.xda.petstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.xda.petstore.adapter.PetAdapter;
import edu.xda.petstore.database.PetDatabase;
import edu.xda.petstore.model.Pet;
import edu.xda.petstore.sqlite.SqliteHelper;

public class Home extends AppCompatActivity {

    ImageButton searchIcon;

    TextView searchText;
    RecyclerView recyclerView;
    PetAdapter petAdapter;
    List<Pet> petList;
    SqliteHelper db;

    void addPet(){
//        Pet pet = new Pet(1,"","","",1,null);
//        PetDatabase.getInstance(this).petDao().insertPet(pet);
    }

    void searchPet(){
        petList = PetDatabase.getInstance(this).petDao().getListPet();
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
        setContentView(R.layout.activity_home);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        searchIcon = findViewById(R.id.searchIcon);
        searchText = findViewById(R.id.searchText);
        recyclerView = findViewById(R.id.rcv_home);
        // search tự động dữ liệu về pet trong data base
        searchPet();
        searchIcon.setOnClickListener(new View.OnClickListener() { //bắt sựu kiện onclick cho nút search
            @Override
            public void onClick(View view) {
                //addPet();
                Toast.makeText(Home.this,"Đã Search", Toast.LENGTH_SHORT).show();

                //recyclerView();
                searchPet();
                searchText.clearFocus();
                hideKeyboard(Home.this, view);
            }

            private void recyclerView() {

                recyclerView.setHasFixedSize(true);
                //recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
            }
        });
    }
}