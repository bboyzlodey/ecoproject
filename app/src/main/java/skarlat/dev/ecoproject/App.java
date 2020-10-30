package skarlat.dev.ecoproject;

import android.app.Application;

import androidx.room.Room;

import com.google.firebase.auth.FirebaseAuth;

import skarlat.dev.ecoproject.eitorjs.ArticleAdviceLinkData;
import skarlat.dev.ecoproject.eitorjs.ArticleEcoTipsBlocks;
import skarlat.dev.ecoproject.eitorjs.ArticleImageData;
import skarlat.dev.ecoproject.eitorjs.ArticleQuoteData;
import skarlat.dev.ecoproject.includes.database.AppDatabase;
import skarlat.dev.ecoproject.network.Authenticator;
import skarlat.dev.ecoproject.network.FireBaseAuthenticator;
import work.upstarts.editorjskit.EJKit;
import work.upstarts.editorjskit.models.EJAbstractCustomBlock;

/**
 * @CLass - подлючение к базе данных ассихнронно
 */

public class App extends Application {
    private final String DATABASE_NAME = "database";
    public static App instance;
    public static Authenticator auth;
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        auth = new FireBaseAuthenticator(FirebaseAuth.getInstance());
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
//                .addMigrations(AppDatabase.MIGRATION)
//                .openHelperFactory(new AssetSQLiteOpenHelperFactory())
                .allowMainThreadQueries()
                .build();
        registerCustomBlocks();
    }

    private void registerCustomBlocks() {
        EJKit.INSTANCE.register(new EJAbstractCustomBlock(ArticleEcoTipsBlocks.QUOTE, ArticleQuoteData.class));
        EJKit.INSTANCE.register(new EJAbstractCustomBlock(ArticleEcoTipsBlocks.ADVICE_LINK, ArticleAdviceLinkData.class));
        EJKit.INSTANCE.register(new EJAbstractCustomBlock(ArticleEcoTipsBlocks.ARTICLE_IMAGE, ArticleImageData.class));
    }

    public static App getInstance() {
        return instance;
    }

    public static AppDatabase getDatabase() {
        return database;
    }

    public String getDatabaseName() {
        return DATABASE_NAME;
    }
}
