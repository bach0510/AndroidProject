package edu.xda.petstore.database;

import android.content.Context;

import androidx.annotation.Keep;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import edu.xda.petstore.dao.CartDAO;
import edu.xda.petstore.dao.PetDAO;
import edu.xda.petstore.dao.UserDAO;
import edu.xda.petstore.model.Cart;
import edu.xda.petstore.model.Pet;
import edu.xda.petstore.model.User;

@Database(entities = {Pet.class, User.class, Cart.class},version = 3)
@Keep
public abstract class PetDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "pet.db";
    private static PetDatabase instance;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE user "
//                    + " ADD COLUMN image BLOB");
        }
    };
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

        }
    };

    public static synchronized  PetDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),PetDatabase.class,"petshopdb.db")
                    .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                    .createFromAsset("database/pet.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
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
