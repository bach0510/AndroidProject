package edu.xda.petstore.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.xda.petstore.model.User;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("select * from user where username = :username and password = :password")
    User searchUserByUserNameAndPass(String username,String password);

    @Query("select * from user where username = :username and id <> :userId")
    List<User> checkExist(String username, int userId);
}
