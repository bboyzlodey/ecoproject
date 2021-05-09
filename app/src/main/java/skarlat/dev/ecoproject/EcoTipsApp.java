package skarlat.dev.ecoproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import skarlat.dev.ecoproject.eitorjs.ArticleAdviceLinkData;
import skarlat.dev.ecoproject.eitorjs.ArticleEcoTipsBlocks;
import skarlat.dev.ecoproject.eitorjs.ArticleImageData;
import skarlat.dev.ecoproject.eitorjs.ArticleQuoteData;
import skarlat.dev.ecoproject.includes.database.AppDatabase;
import skarlat.dev.ecoproject.network.Authenticator;
import skarlat.dev.ecoproject.network.FireBaseAuthenticator;
import work.upstarts.editorjskit.EJKit;
import work.upstarts.editorjskit.models.EJAbstractCustomBlock;

public class EcoTipsApp extends Application {
    private final String DATABASE_NAME = "database";
    public static EcoTipsApp instance;
    public static Authenticator auth;
    private static AppDatabase database;

    private static final CompositeDisposable disposables = new CompositeDisposable();
    private static final io.reactivex.disposables.CompositeDisposable oldRxJavaDisposables = new io.reactivex.disposables.CompositeDisposable();

    private final static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Course RENAME TO Course_old");
            database.execSQL("CREATE TABLE \"Course_new\" (\n" +
                    "\t\"courseNameID\"\tTEXT NOT NULL,\n" +
                    "\t\"isActive\"\tINTEGER NOT NULL,\n" +
                    "\t\"title\"\tTEXT,\n" +
                    "\t\"description\"\tTEXT,\n" +
                    "\t\"fullDescription\"\tTEXT,\n" +
                    "\tPRIMARY KEY(\"courseNameID\")\n" +
                    ");");
            database.execSQL(
                    "INSERT INTO Course_new (courseNameID, isActive, title, description, fullDescription) " +
                            "SELECT courseNameID, isActive, title, description, fullDescription " +
                            "FROM Course_old ");
            database.execSQL("DROP TABLE Course_old");
            database.execSQL("ALTER TABLE Course_new RENAME TO Course");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        auth = new FireBaseAuthenticator(FirebaseAuth.getInstance());
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .createFromAsset("database")
                .addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries()
                .build();
        registerCustomBlocks();
    }

    private void registerCustomBlocks() {
        EJKit.INSTANCE.register(new EJAbstractCustomBlock(ArticleEcoTipsBlocks.QUOTE, ArticleQuoteData.class));
        EJKit.INSTANCE.register(new EJAbstractCustomBlock(ArticleEcoTipsBlocks.ADVICE_LINK, ArticleAdviceLinkData.class));
        EJKit.INSTANCE.register(new EJAbstractCustomBlock(ArticleEcoTipsBlocks.ARTICLE_IMAGE, ArticleImageData.class));
    }

    public static AppDatabase getDatabase() {
        return database;
    }

    @Override
    public void onTerminate() {
        disposables.dispose();
        disposables.clear();
        super.onTerminate();
    }

    public static void addDisposable(Disposable subscription) {
        disposables.add(subscription);
    }

    public static void addDisposable(io.reactivex.disposables.Disposable subscription) {
        oldRxJavaDisposables.add(subscription);
    }
}
