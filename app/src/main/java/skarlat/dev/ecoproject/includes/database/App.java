package skarlat.dev.ecoproject.includes.database;

import android.app.Application;

import androidx.room.Room;

/**
 * @CLass - подлючение к базе данных ассихнронно
 *
 */

public class App extends Application {
    private final String DATABASE_NAME = "database";
    public static App instance;

    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
//                .addMigrations(AppDatabase.MIGRATION)
//                .openHelperFactory(new AssetSQLiteOpenHelperFactory())
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public static AppDatabase getDatabase() {
        return database;
    }
    
    public String getDatabaseName(){return DATABASE_NAME;}
}
