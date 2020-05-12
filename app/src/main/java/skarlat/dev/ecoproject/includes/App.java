package skarlat.dev.ecoproject.includes;

import android.app.Application;

import androidx.room.Room;

/**
 * @CLass - подлючение к базе данных ассихнронно
 *
 */

public class App extends Application {

    public static App instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
//                .addMigrations(AppDatabase.MIGRATION)
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }


    public AppDatabase getDatabase() {
        return database;
    }
}
