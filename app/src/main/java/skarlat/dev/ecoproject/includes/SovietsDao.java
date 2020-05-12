package skarlat.dev.ecoproject.includes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SovietsDao {

    @Query("SELECT * FROM SovietsDB")
    List<SovietsDB> getAllFavorite();

    @Query("SELECT * FROM SovietsDB")
    List<SovietsDB> getAll();

    @Query("SELECT * FROM SovietsDB WHERE cardID = :cardID")
    List<SovietsDB> getAllByCardID(String cardID);

    @Insert
    void insert(SovietsDB sovietsDB);

    @Update
    void update(SovietsDB sovietsDB);

    @Delete
    void delete(SovietsDB sovietsDB);

}
