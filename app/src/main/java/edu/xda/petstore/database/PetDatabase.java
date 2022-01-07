package edu.xda.petstore.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.xda.petstore.dao.PetDAO;
import edu.xda.petstore.model.Pet;

@Database(entities = {Pet.class},version = 1)
public abstract class PetDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "pet.db";
    private static PetDatabase instance;

    public static synchronized  PetDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),PetDatabase.class,"pet.db")
                    .createFromAsset("database/pet.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract PetDAO petDao();
}
