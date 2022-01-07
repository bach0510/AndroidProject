package edu.xda.petstore.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.xda.petstore.model.Pet;

@Dao
public interface PetDAO {

    @Insert
    void insertPet(Pet pet);

    @Query("select * from pet")
    List<Pet> getListPet();
}
