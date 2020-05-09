package skarlat.dev.ecoproject.includes;

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
public interface CoursesDao {

    @Query("SELECT * FROM СoursesDB")
    List<СoursesDB> getAll();

    @Query("SELECT * FROM СoursesDB WHERE id = :id")
    СoursesDB getById(long id);

    @Query("SELECT * FROM СoursesDB WHERE cursID = :cursID")
    СoursesDB getByCursID(String cursID);

    @Insert
    void insert(СoursesDB сoursesDB);

    @Update
    void update(СoursesDB сoursesDB);

    @Delete
    void delete(СoursesDB сoursesDB);

}
