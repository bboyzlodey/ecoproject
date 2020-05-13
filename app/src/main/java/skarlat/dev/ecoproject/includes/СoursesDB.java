package skarlat.dev.ecoproject.includes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Class - структура таблицы базы данных
 * каждая переменная - поле в таблице
 */
@Entity
public class СoursesDB {
    @PrimaryKey
    @NonNull
    public String cursID;

    public long id;

    public int progressBar;

    public int isActive;

    public int countAllCards;
}

