package skarlat.dev.ecoproject.includes.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import skarlat.dev.ecoproject.EcoSoviet;

@Dao
public interface SovietsDao {

    @Query("SELECT * FROM EcoSoviet")
    List<EcoSoviet> getAllFavorite();

    @Query("SELECT * FROM EcoSoviet")
    List<EcoSoviet> getAll();

    @Query("SELECT * FROM EcoSoviet WHERE cardNameID = :cardNameID")
    List<EcoSoviet> getAllByCardNameID(String cardNameID);

    @Insert
    void insert(EcoSoviet ecoSoviet);

    @Update
    void update(EcoSoviet ecoSoviet);

    @Delete
    void delete(EcoSoviet ecoSoviet);

}
