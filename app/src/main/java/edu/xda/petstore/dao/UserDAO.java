package edu.xda.petstore.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.xda.petstore.model.User;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(User user);

    @Query("select * from user where username = :username and password = :password")
    List<User> searchUserByUserNameAndPass(String username,String password);
}
