package edu.xda.petstore.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.xda.petstore.model.Cart;
import edu.xda.petstore.model.Pet;

@Dao
public interface CartDAO {

    @Insert
    void insertCart(Cart cart);

}
