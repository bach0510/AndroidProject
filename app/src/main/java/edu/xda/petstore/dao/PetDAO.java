package edu.xda.petstore.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.xda.petstore.model.Pet;
import edu.xda.petstore.model.PetCartDto;

@Dao
public interface PetDAO {

    @Insert
    void insertPet(Pet pet);

    @Query("select * from pet")
    List<Pet> getListPet();

    @Query("select * from pet where maGiong like '%' || :input || '%'  or tenGiong like '%' || :input || '%' ")
    List<Pet> searchPet(String input);

    @Query("select * from pet where  loaiId = :type and  maGiong like '%' || :input || '%'  or loaiId = :type and tenGiong like '%' || :input || '%'  ")
    List<Pet> searchPetByType(String input,int type);

    @Query("select c.qty as qty, c.id as cartId, p.* from pet p join cart c on p.id == c.petId where c.userId = :userId")
    List<PetCartDto> searchCartInfo(int userId);

}
