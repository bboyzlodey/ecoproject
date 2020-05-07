package skarlat.dev.ecoproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
/**
 * Подключение к базе данных
 *
 * @Interface CursCardDao - интерфейс с методами работы с базой данных
 * @methods Query - получение данных по заданным аргументам
 *          Insert - создание новой таблицы в базе данных
 *          Update - обновление данных существующей таблицы
 */
@Dao
public interface CursCardDao {

    @Query("SELECT * FROM CursCard")
    List<CursCard> getAll();

    @Query("SELECT * FROM CursCard WHERE id = :id")
    CursCard getById(long id);

    @Query("SELECT * FROM CursCard WHERE cursID = :cursID")
    CursCard getByCursID(String cursID);

    @Insert
    void insert(CursCard cursCard);

    @Update
    void update(CursCard cursCard);

    @Delete
    void delete(CursCard cursCard);

}
