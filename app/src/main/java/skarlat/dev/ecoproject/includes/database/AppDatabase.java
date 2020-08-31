package skarlat.dev.ecoproject.includes.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import skarlat.dev.ecoproject.Course;
import skarlat.dev.ecoproject.EcoCard;
import skarlat.dev.ecoproject.EcoSoviet;
import skarlat.dev.ecoproject.includes.database.dao.CardsDao;
import skarlat.dev.ecoproject.includes.database.dao.CourseDao;
import skarlat.dev.ecoproject.includes.database.dao.SovietsDao;
import skarlat.dev.ecoproject.includes.database.dao.VersionDao;

/**
 * Связь Room и SQLite
 * если нужно обновить структуру базы данных, то нужно делать миграцию сменив версию
 * не уверен как именно оставить код миграции
 */
@Database(entities = {EcoCard.class, Course.class, EcoSoviet.class, Version.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CardsDao cardsDao();
    public abstract SovietsDao sovietsDao();
    public abstract CourseDao courseDao();
    public abstract VersionDao versionDao();
}