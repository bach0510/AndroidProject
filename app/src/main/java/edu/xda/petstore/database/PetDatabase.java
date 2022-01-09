package edu.xda.petstore.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.xda.petstore.dao.CartDAO;
import edu.xda.petstore.dao.PetDAO;
import edu.xda.petstore.dao.UserDAO;
import edu.xda.petstore.model.Cart;
import edu.xda.petstore.model.Pet;
import edu.xda.petstore.model.User;

@Database(entities = {Pet.class, User.class, Cart.class},version = 1)
public abstract class PetDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "pet.db";
    private static PetDatabase instance;

    public static synchronized  PetDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),PetDatabase.class,"pet3.db")
                    .createFromAsset("database/pet.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public static void destroyInstance() {
        instance = null;
    }

    public abstract PetDAO petDao();
    public abstract UserDAO userDao();
    public abstract CartDAO cartDao();
}
