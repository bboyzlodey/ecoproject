package skarlat.dev.ecoproject.includes.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import skarlat.dev.ecoproject.Course;

/**
 * Подключение к базе данных
 *
 * @Interface CursCardDao - интерфейс с методами работы с базой данных
 * @methods Query - получение данных по заданным аргументам
 *          Insert - создание новой таблицы в базе данных
 *          Update - обновление данных существующей таблицы
 */
@Dao
public interface CourseDao {

    @Query("SELECT * FROM Course")
    List<Course> getAll();

    @Query("SELECT * FROM Course WHERE courseNameID = :courseNameID")
    Course getByCourseID(String courseNameID);

    @Query("SELECT * FROM Course WHERE isActive = 1")
    Course getCurrentCourse();

    @Insert
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);
}

