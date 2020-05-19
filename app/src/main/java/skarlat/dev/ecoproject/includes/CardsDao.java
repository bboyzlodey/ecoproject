package skarlat.dev.ecoproject.includes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CardsDao {
    
    @Query("SELECT * FROM CardsDB")
    List<CardsDB> getAll();

    @Query("SELECT * FROM CardsDB WHERE cursID = :cursID")
    List<CardsDB> getAllByCurs(String cursID);

    @Query("SELECT * FROM CardsDB WHERE cardID = :cardID")
    CardsDB getByCardID(String cardID);

    @Query("SELECT * FROM CardsDB WHERE cursID = :cursID AND isActive = 2")
    List<CardsDB> getAllActive(String cursID);
    
    @Insert
    void insert(CardsDB cardsDB);

    @Update
    void update(CardsDB cardsDB);

    @Delete
    void delete(CardsDB cardsDB);

}
