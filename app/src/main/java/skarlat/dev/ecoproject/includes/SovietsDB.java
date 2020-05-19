package skarlat.dev.ecoproject.includes;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import skarlat.dev.ecoproject.Course;

@Entity
public class SovietsDB {

    public String cardID; // Имя карточки

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long sovietID; // порядковый номер совета

    public int isFavorite;

    public String title; // загловок

    public String description; // описание
    
    // Можно игнорировать методы и другие поля, и можно их вызывать
    @Ignore
    public Course course;
    @Ignore
    public void ignoredMethod(){
        Log.d("debug", "Ignored method");
    }
}
