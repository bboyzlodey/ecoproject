package skarlat.dev.ecoproject.includes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SovietsDB {

    public String cardID;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long sovietID;

    public int isFavorite;

    public String title;

    public String description;
}
