package skarlat.dev.ecoproject.includes.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import skarlat.dev.ecoproject.includes.database.Version;

@Dao
public interface VersionDao {

    @Query("SELECT * FROM Version")
    List<Version> getAllVer();

    @Query("SELECT * FROM Version WHERE versionContent")
    int getVersionContent();

    @Query("SELECT * FROM Version WHERE versionUserBar")
    int getVersionUserBar();

    @Insert
    void insert(Version version);

    @Update
    void update(Version version);

    @Delete
    void delete(Version version);
}
