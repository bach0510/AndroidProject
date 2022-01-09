package edu.xda.petstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CartIcon extends AppCompatActivity {

    private TextView cart_number;

    private final int max = 99;

    public CartIcon (View view){
        cart_number = view.findViewById(R.id.cart_number);
    }

    public void setNumber(int number){
        if (number > max){
            cart_number.setText("99");
        }
        else cart_number.setText(String.valueOf(number));

    }
}