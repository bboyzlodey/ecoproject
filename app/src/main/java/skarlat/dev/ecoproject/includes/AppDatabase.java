package skarlat.dev.ecoproject.includes;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Связь Room и SQLite
 * если нужно обновить структуру базы данных, то нужно делать миграцию сменив версию
 * не уверен как именно оставить код миграции
 */
@Database(entities = {СoursesDB.class, CardsDB.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CoursesDao cursCardDao();
    public abstract CardsDao cardsDao();
    public static final Migration MIGRATION = new Migration(4, 5) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE table_new (id INTEGER NOT NULL, cursID TEXT NOT NULL, isActive INTEGER NOT NULL, progressBar INTEGER NOT NULL, PRIMARY KEY(cursID))");
            database.execSQL("DROP TABLE СoursesDB");
            database.execSQL("ALTER TABLE table_new RENAME TO СoursesDB");
//            database.execSQL("CREATE TABLE CardsDB (cardID TEXT NOT NULL, cursID TEXT, isActive INTEGER NOT NULL, PRIMARY KEY(cardID))");
////            database.execSQL("DROP TABLE CardsDB");
////            database.execSQL("ALTER TABLE table_new RENAME TO CardsDB");
        }
    };
}