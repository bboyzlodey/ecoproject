package skarlat.dev.ecoproject.includes.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Version {

    @PrimaryKey
    @NonNull
    public String searchKey;

    public long versionContent;

    public int versionUserBar;

}
