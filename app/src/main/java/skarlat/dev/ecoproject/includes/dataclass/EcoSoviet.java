package skarlat.dev.ecoproject.includes.dataclass;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import skarlat.dev.ecoproject.EcoTipsApp;
import skarlat.dev.ecoproject.includes.database.AppDatabase;
import skarlat.dev.ecoproject.includes.database.dao.SovietsDao;

@Entity
public class EcoSoviet {

    public String cardNameID;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long sovietID;

    public String title;

    public String description;

}
