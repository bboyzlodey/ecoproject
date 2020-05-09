package skarlat.dev.ecoproject.includes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CardsDB {
    @PrimaryKey
    @NonNull
    public String cardID;

    public String cursID;

    public int isActive;
}
