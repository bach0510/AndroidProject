package edu.xda.petstore.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import edu.xda.petstore.model.Pet;

public class SqliteHelper extends SQLiteOpenHelper {

    private Context context ;
    private static final String DATABASE_NAME = "PetStore.db";
    private static final int DATABASE_VERSION = 1;

//    public static final String DATABASE_NAME = "PetStore.db";
//    public static final String DATABASE_NAME = "PetStore.db";
//    public static final String DATABASE_NAME = "PetStore.db";
//    public static final String DATABASE_NAME = "PetStore.db";
//    public static final String DATABASE_NAME = "PetStore.db";

    public SqliteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = " CREATE TABLE LOAI( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MALOAI TEXT, " +
                "TENLOAI TEXT);";

        String query2 = "CREATE TABLE GIONG (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MAGIONG TEXT," +
                "LOAIID INTEGER," +
                "TENGIONG TEXT," +
                "MOTA TEXT," +
                "DONGIA INTEGER," +
                "ANH BLOB" +
                ");";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("DROP TABLE");
        onCreate(sqLiteDatabase);
    }

    public void addPet(Pet pet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("LOAIID",pet.getLoaiId());
        cv.put("MAGIONG",pet.getMaGiong());
        cv.put("TENGIONG",pet.getTenGiong());
        cv.put("MOTA",pet.getMoTa());
        cv.put("DONGIA",pet.getDonGia());
        cv.put("ANH",pet.getAnh());

        long result = db.insert("GIONG",null,cv);
    }
}
