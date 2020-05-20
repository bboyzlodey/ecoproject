package skarlat.dev.ecoproject.includes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseCopy
{
    private static String TAG = DataBaseCopy.class.getName();
    private  String DB_PATH; //= "/data/data/com.example.yourproject/database/";
    private static String DB_NAME = "database";
    private SQLiteDatabase myDataBase = null;
    private final Context myContext;

    public DataBaseCopy(Context context)
    {
//        super(context, DB_NAME, null, 1);

        this.myContext = context;
        DB_PATH="/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.v("log_tag", "DBPath: " + DB_PATH);
//          File f=getDatabasePath(DB_NAME);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(dbExist){
            Log.v("log_tag", "database.db does exist");
        }else{
            Log.v("log_tag", "database.db does not exist");
//            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database.db");
            }
        }
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private boolean checkDataBase(){

        File dbFile = new File(DB_PATH + DB_NAME);
        if (!dbFile.exists()){
            File dbFolder = new File(DB_PATH);
            if (!dbFolder.exists())
               return !dbFolder.mkdir();
        }
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();

    }

//    public boolean openDataBase() throws SQLException
//    {
//        String mPath = DB_PATH + DB_NAME;
//        //Log.v("mPath", mPath);
//        myDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
//        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
//        return myDataBase != null;
//
//    }
//
//
//    @Override
//    public synchronized void close()
//    {
//        if(myDataBase != null)
//            myDataBase.close();
//        super.close();
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db)
//    {
//
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
//    {
//        Log.v(TAG, "Upgrading database.db, this will drop database.db and recreate.");
//    }
}