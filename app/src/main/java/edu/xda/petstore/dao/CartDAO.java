package edu.xda.petstore.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.xda.petstore.model.Cart;
import edu.xda.petstore.model.User;

@Dao
public interface CartDAO {

    @Insert
    void insertCart(Cart cart);

    @Delete
    void deleteCart(Cart cart);

    @Query("select * from cart where userId = :userId")
    List<Cart> searchCart(int userId);

}
