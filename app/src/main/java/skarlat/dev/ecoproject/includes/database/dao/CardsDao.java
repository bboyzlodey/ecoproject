package skarlat.dev.ecoproject.includes.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import skarlat.dev.ecoproject.EcoCard;

@Dao
public interface CardsDao {
    @Query("SELECT * FROM EcoCard")
    List<EcoCard> getAll();

    @Query("SELECT * FROM EcoCard WHERE courseNameID = :courseNameID")
    List<EcoCard> getAllByCurs(String courseNameID);

    @Query("SELECT * FROM EcoCard WHERE cardNameID = :cardNameID")
    EcoCard getByCardID(String cardNameID);

    @Query("SELECT * FROM EcoCard WHERE courseNameID = :courseNameID AND isActive = 2")
    List<EcoCard> getAllActive(String courseNameID);

    @Insert
    void insert(EcoCard ecoCard);

    @Update
    void update(EcoCard ecoCard);

    @Delete
    void delete(EcoCard ecoCard);

}
