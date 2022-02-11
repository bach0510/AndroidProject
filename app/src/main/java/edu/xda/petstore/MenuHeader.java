package edu.xda.petstore;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuHeader extends AppCompatActivity {

    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = findViewById(R.id.currentName);
        name.setText(Login.currentUser.getName());
    }

    public MenuHeader (View view){
        name = view.findViewById(R.id.currentName);
    }

    public void setName(){
        name.setText(Login.currentUser.getName());
    }
}