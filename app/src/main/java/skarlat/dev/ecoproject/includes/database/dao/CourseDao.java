package skarlat.dev.ecoproject.includes.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import skarlat.dev.ecoproject.includes.dataclass.Course;

/**
 * Подключение к базе данных
 *
 * @Interface CursCardDao - интерфейс с методами работы с базой данных
 * @methods Query - получение данных по заданным аргументам
 * Insert - создание новой таблицы в базе данных
 * Update - обновление данных существующей таблицы
 */
@Dao
public interface CourseDao {

    @Query("SELECT * FROM Course")
    List<Course> getAll();

    @Query("SELECT * FROM Course WHERE isActive = 1")
    List<Course> getAllIsActive();

    @Query("SELECT * FROM Course WHERE isActive = 0")
    List<Course> getAllNonActive();

    @Query("SELECT * FROM Course WHERE isActive != 0 AND isActive != 1")
    List<Course> getAllFinished();

    @Query("SELECT * FROM Course WHERE courseNameID = :courseNameID")
    Course getCourse(String courseNameID);

    @Query("SELECT * FROM Course WHERE isActive = 1")
    Course getCurrentCourse();

    @Insert
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);
}

