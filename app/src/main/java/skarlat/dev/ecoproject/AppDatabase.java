package skarlat.dev.ecoproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Связь Room и SQLite
 * если нужно обновить структуру базы данных, то нужно делать миграцию сменив версию
 * не уверен как именно оставить код миграции
 */
@Database(entities = {CursCard.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CursCardDao cursCardDao();
//    public static final Migration MIGRATION = new Migration(2, 3) {
//        @Override
//        public void migrate(final SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE table_new (id INTEGER NOT NULL, cursID TEXT NOT NULL, checkList TEXT, progressBar INTEGER NOT NULL, PRIMARY KEY(cursID))");
//            database.execSQL("DROP TABLE CursCard");
//            database.execSQL("ALTER TABLE table_new RENAME TO CursCard");
//        }
//    };
}
