package edu.xda.petstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import edu.xda.petstore.adapter.PetAdapter;
import edu.xda.petstore.database.PetDatabase;
import edu.xda.petstore.model.Cart;
import edu.xda.petstore.model.Pet;
import edu.xda.petstore.sqlite.SqliteHelper;

public class Home extends AppCompatActivity {

    ImageButton searchIcon;
    ImageView menuIcon;

    TextView searchText;
    RecyclerView recyclerView;
    PetAdapter petAdapter;
    List<Pet> petList;
    SqliteHelper db;

    LinearLayout cat,dog,bird,mouse;

    NavigationView navView;

    void addPet(){
//        Pet pet = new Pet(1,"","","",1,null);
//        PetDatabase.getInstance(this).petDao().insertPet(pet);
    }

    void searchPet(){
        petList = PetDatabase.getInstance(this).petDao().getListPet();
        setRecyclerView(petList);
    }

    void searchPetByInput(String input){
        petList = PetDatabase.getInstance(this).petDao().searchPet(input);
        setRecyclerView(petList);
    }

    int coutNumberPetInCart(){
//        int userId = getIntent().getExtras().getInt("userId");
        int userId = Login.currentUser.getId();
        return PetDatabase.getInstance(this).cartDao().searchCart(userId).size();
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
    protected void onRestart() { // được call khi 1 activity con finish()
        super.onRestart();
        CartIcon homeCart = new CartIcon(findViewById(R.id.home_cart));
        homeCart.setNumber(coutNumberPetInCart());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        searchIcon = findViewById(R.id.searchIcon);
        searchText = findViewById(R.id.searchText);
        recyclerView = findViewById(R.id.rcv_home);
        menuIcon = findViewById(R.id.menuIcon);
        navView = findViewById(R.id.nav_main);

        cat = findViewById(R.id.cat_type);
        dog = findViewById(R.id.dog_type);
        bird = findViewById(R.id.bird_type);
        mouse = findViewById(R.id.mouse_type);

        CartIcon homeCart = new CartIcon(findViewById(R.id.home_cart));
        homeCart.setNumber(coutNumberPetInCart());

        findViewById(R.id.home_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, CartInfo.class);
                Home.this.startActivity(i);
            }
        });

        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, PetTypeList.class);
                i.putExtra("type",2);
                Home.this.startActivity(i);
            }
        });
        dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, PetTypeList.class);
                i.putExtra("type",1);
                Home.this.startActivity(i);
            }
        });
        bird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, PetTypeList.class);
                i.putExtra("type",3);
                Home.this.startActivity(i);
            }
        });
        mouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, PetTypeList.class);
                i.putExtra("type",4);
                Home.this.startActivity(i);
            }
        });

        //tìm drawer layout
        final DrawerLayout navbar = findViewById(R.id.drawerLayout);

//        navView.setCheckedItem(R.id.home_menu);
        navView.getMenu().getItem(0).setChecked(true);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home_menu:
                        Intent i = new Intent(Home.this, Home.class);
                        Home.this.startActivity(i);
                        break;
                    case R.id.logout_menu:
                        Intent i1 = new Intent(Home.this, Login.class);
                        Login.currentUser = null;
                        Home.this.startActivity(i1);

                        Toast.makeText(Home.this,"Logout", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.personal_menu:
                        Intent i2 = new Intent(Home.this, Personal.class);
                        Home.this.startActivity(i2);
                        break;
                }
                return true;
            }
        });

        menuIcon.setOnClickListener(new View.OnClickListener() { // bắt sự kiện mở menu bằng menu icon
            @Override
            public void onClick(View view) {
                navbar.openDrawer(GravityCompat.START);
            }
        });
        // search tự động dữ liệu về pet trong data base
        searchPet();
        searchIcon.setOnClickListener(new View.OnClickListener() { //bắt sựu kiện onclick cho nút search
            @Override
            public void onClick(View view) {
                //addPet();
                //Toast.makeText(Home.this,"Đã Search", Toast.LENGTH_SHORT).show();

                //recyclerView();
                searchPetByInput(searchText.getText().toString());
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