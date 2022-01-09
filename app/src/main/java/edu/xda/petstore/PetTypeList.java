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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.xda.petstore.adapter.PetAdapter;
import edu.xda.petstore.database.PetDatabase;
import edu.xda.petstore.model.Pet;

public class PetTypeList extends AppCompatActivity {

    ImageButton searchIcon;

    TextView searchText;
    TextView petType;
    RecyclerView recyclerView;
    PetAdapter petAdapter;
    List<Pet> petList;

    void searchPetByInput(String input,int type){
        petList = PetDatabase.getInstance(this).petDao().searchPetByType(input,type);
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
        setContentView(R.layout.activity_pet_type_list);

        searchIcon = findViewById(R.id.searchIcon);
        searchText = findViewById(R.id.searchText);
        recyclerView = findViewById(R.id.rcv_home);

        Intent i = getIntent();
        searchPetByInput("",i.getExtras().getInt("type"));
        petType = findViewById(R.id.pet_type);
        petType.setText(i.getExtras().getInt("type") == 1? "Chó": i.getExtras().getInt("type") == 2 ? "Mèo" : i.getExtras().getInt("type") == 3 ? "Chim" : "Chuột");
        searchIcon.setOnClickListener(new View.OnClickListener() { //bắt sựu kiện onclick cho nút search
            @Override
            public void onClick(View view) {
                //addPet();
                //Toast.makeText(Home.this,"Đã Search", Toast.LENGTH_SHORT).show();

                //recyclerView();
                searchPetByInput(searchText.getText().toString(),i.getExtras().getInt("type"));
                searchText.clearFocus();
                hideKeyboard(PetTypeList.this, view);
            }

            private void recyclerView() {

                recyclerView.setHasFixedSize(true);
                //recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
            }
        });

        findViewById(R.id.back_button2).setOnClickListener(new View.OnClickListener() { // bắt sự kiện mở menu bằng menu icon
            @Override
            public void onClick(View view) {
                finish();
//                Intent i = new Intent(PetTypeList.this, Home.class);
//                PetTypeList.this.startActivity(i);
            }
        });
    }
}