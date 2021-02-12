package skarlat.dev.ecoproject.includes.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import skarlat.dev.ecoproject.includes.dataclass.EcoCard;

/**
 * Подключение к базе данных
 *
 * @Interface CursCardDao - интерфейс с методами работы с базой данных
 * @methods Query - получение данных по заданным аргументам
 * Insert - создание новой таблицы в базе данных
 * Update - обновление данных существующей таблицы
 */
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
    Maybe<Integer> update(EcoCard ecoCard);

    @Delete
    void delete(EcoCard ecoCard);

}
