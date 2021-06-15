package skarlat.dev.ecoproject.includes.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import skarlat.dev.ecoproject.includes.dataclass.Course;
import skarlat.dev.ecoproject.includes.dataclass.EcoCard;
import skarlat.dev.ecoproject.includes.dataclass.EcoSoviet;
import skarlat.dev.ecoproject.includes.database.dao.CardsDao;
import skarlat.dev.ecoproject.includes.database.dao.CourseDao;
import skarlat.dev.ecoproject.includes.database.dao.SovietsDao;


@Database(entities = {EcoCard.class, Course.class, EcoSoviet.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CardsDao cardsDao();

    public abstract CourseDao courseDao();

    public abstract SovietsDao sovietsDao();
}