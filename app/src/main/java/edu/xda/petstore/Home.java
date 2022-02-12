package edu.xda.petstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    AlertDialog dialog;
    AlertDialog.Builder builder;

    void addPet(){
//        Pet pet = new Pet(1,"","","",1,null);
//        PetDatabase.getInstance(this).petDao().insertPet(pet);
    }

    // hàm search thông tin pet
    void searchPet(){
        petList = PetDatabase.getInstance(this).petDao().getListPet(); //lấy thông tin và list pet
        setRecyclerView(petList);// set list pet cho recycle View
    }

    //hàm search pet bằng thanh search
    void searchPetByInput(String input){
        petList = PetDatabase.getInstance(this).petDao().searchPet(input);// lấy thông tin pet
        setRecyclerView(petList);// set list cho recycle View
    }

    int coutNumberPetInCart(){ // đếm số pet trong giỏ hàng
//        int userId = getIntent().getExtras().getInt("userId");
        int userId = Login.currentUser.getId();// đặt biến cho thông tin người dùng đăng nhập (userId)
        return PetDatabase.getInstance(this).cartDao().searchCart(userId).size();// tìm danh sách thông tin giỏ hnagf và đếm sô sbanr ghi bằng hàm (size())
    }

    private void setRecyclerView(List<Pet> petList){// set recycle view (input là list pet)
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        petAdapter = new PetAdapter(this,petList);
        recyclerView.setAdapter(petAdapter);// set adapter
    }

    public void hideKeyboard(Context context, View view) {// hàm dùng để hide đi bàn phím nhập
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

        //set tên cho thanh navigation
        navView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {

                navView.removeOnLayoutChangeListener( this );

                TextView textView = (TextView) navView.findViewById(R.id.currentName);

                textView.setText(Login.currentUser.getName());// set text view thành tên người dùng đang đăng nhập
            }
        });

        CartIcon homeCart = new CartIcon(findViewById(R.id.home_cart));// tạp new CartIcon
        homeCart.setNumber(coutNumberPetInCart()); // call hàm set number cho icon giỏ hàng
        // hàm coutNumberPetInCart dùng để return ra số pet trong giỏ hàng hiện tại
        // setNumber là set số pet trong giỏ hàng của người dùng (hàm này public trong class CartIcon)

        findViewById(R.id.home_cart).setOnClickListener(new View.OnClickListener() { // set sự kiện onclick cho icon giỏ hàng
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, CartInfo.class); // ra danh sách giỏ hang
                Home.this.startActivity(i);
            }
        });

        // 4 sự kiện bên dưới dùng để mở ra trang phân loại các loại thú cưng
        // putexxtra put thêm tham số cho activity PetTypeList để phân biệt và search loại theo tham số truyền vào trong đó
        // mèo : type = 2
        // chó : type = 1
        // chim : type = 3
        // chuột : type = 4
        cat.setOnClickListener(new View.OnClickListener() {// set sự kiện click cho các thẻ loại thú cưng
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, PetTypeList.class);
                i.putExtra("type",2);// truyền tham số loại thú cưng cho activity Pettype
                Home.this.startActivity(i);
            }
        });
        dog.setOnClickListener(new View.OnClickListener() { // set sự kiện click cho các loại thẻ thú cưng
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, PetTypeList.class);
                i.putExtra("type",1);// truyền tham số loại thú cưng cho activity Pettype
                Home.this.startActivity(i);
            }
        });
        bird.setOnClickListener(new View.OnClickListener() { // set sự kiện click cho các loại thẻ thú cưng
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, PetTypeList.class);
                i.putExtra("type",3);// truyền tham số loại thú cưng cho activity Pettype
                Home.this.startActivity(i);
            }
        });
        mouse.setOnClickListener(new View.OnClickListener() { // set sự kiện click cho các loại thẻ thú cưng
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, PetTypeList.class);
                i.putExtra("type",4);// truyền tham số loại thú cưng cho activity Pettype
                Home.this.startActivity(i);
            }
        });

        //tìm drawer layout
        final DrawerLayout navbar = findViewById(R.id.drawerLayout);

//        navView.setCheckedItem(R.id.home_menu);

        navView.getMenu().getItem(0).setChecked(true); // tự động set item đang đc select là item đầu
        // bắt sự kiện chọn item trong thanh navigate menu
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home_menu:// nếu item chọn có id là home thí mở activity Home
                        Intent i = new Intent(Home.this, Home.class);
                        Home.this.startActivity(i);
                        break;
                    case R.id.logout_menu:// nếu item chọn có id là Logout thì logout khỏi app
                        //tạo dialog confirm yes no
                        builder = new AlertDialog.Builder(Home.this);
                        builder.setTitle("Bạn có chắc muốn đăng xuất ?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() { // tạo và bắt sự kiện chọn có
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent i1 = new Intent(Home.this, Login.class);
                                Login.currentUser = null;
                                Home.this.startActivity(i1);

                                Toast.makeText(Home.this,"Đã đăng xuất", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() { // tạo và bắt sự kiện khi chọn không
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        dialog = builder.create();
                        dialog.show();// show dialog
                        break;
                    case R.id.personal_menu:// nếu item có id là thông tin cá nhân thì mở activity Personal
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
                searchPetByInput(searchText.getText().toString());// hàm tìm kiếm danh sách các loại thú cưng bằng input search
                // hiện tại query đang search theo tên giống và mã giống
                searchText.clearFocus(); // bỏ focus khỏi input search
                hideKeyboard(Home.this, view);// hide bàn phím
            }

            private void recyclerView() {

                recyclerView.setHasFixedSize(true);
                //recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
            }
        });
    }
}