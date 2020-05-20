package skarlat.dev.ecoproject.includes.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import skarlat.dev.ecoproject.Course;
import skarlat.dev.ecoproject.EcoCard;
import skarlat.dev.ecoproject.EcoSoviet;
import skarlat.dev.ecoproject.includes.database.dao.CardsDao;
import skarlat.dev.ecoproject.includes.database.dao.CourseDao;
import skarlat.dev.ecoproject.includes.database.dao.SovietsDao;

/**
 * Связь Room и SQLite
 * если нужно обновить структуру базы данных, то нужно делать миграцию сменив версию
 * не уверен как именно оставить код миграции
 */
@Database(entities = {EcoCard.class, Course.class, EcoSoviet.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CardsDao cardsDao();
    public abstract SovietsDao sovietsDao();
    public abstract CourseDao courseDao();
//    public static final Migration MIGRATION = new Migration(4, 5) {
//        @Override
//        public void migrate(final SupportSQLiteDatabase database.db) {
//            database.db.execSQL("CREATE TABLE table_new (id INTEGER NOT NULL, cursID TEXT NOT NULL, isActive INTEGER NOT NULL, progressBar INTEGER NOT NULL, PRIMARY KEY(cursID))");
//            database.db.execSQL("DROP TABLE СoursesDB");
//            database.db.execSQL("ALTER TABLE table_new RENAME TO СoursesDB");
////            database.db.execSQL("CREATE TABLE CardsDB (cardID TEXT NOT NULL, cursID TEXT, isActive INTEGER NOT NULL, PRIMARY KEY(cardID))");
//////            database.db.execSQL("DROP TABLE CardsDB");
//////            database.db.execSQL("ALTER TABLE table_new RENAME TO CardsDB");
//        }
//    };
}