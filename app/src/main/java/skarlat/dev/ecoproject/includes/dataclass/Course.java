package skarlat.dev.ecoproject.includes.dataclass;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import skarlat.dev.ecoproject.EcoTipsApp;
import skarlat.dev.ecoproject.Const;
import skarlat.dev.ecoproject.includes.database.AppDatabase;
import skarlat.dev.ecoproject.includes.database.dao.CourseDao;

@Entity
public class Course implements EcoInterface, Serializable {

    @PrimaryKey
    @NonNull
    public String courseNameID;

    public int isActive;

    public String title;

    public String description;

    public String fullDescription;


    @Ignore
    public int leftCards;

    @Ignore
    public int countCards;

    public enum Status {
        CLOSED,
        CURRENT,
        FINISHED
    }

    @Override
    public String getName() {
        return this.courseNameID;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getFullDescription() {
        return this.fullDescription;
    }

    @Override
    public Enum getStatus() {
        switch (isActive) {
            case 1:
                return Status.CURRENT;
            case 2:
                return Status.FINISHED;
            default:
                return Status.CLOSED;
        }
    }

    @Override
    public String getPathImage() {
        return Const.IMAGES_ROOT_FOLDER + courseNameID + "/" + courseNameID + ".png";
    }

    public void setStatus(Course.Status status) {
        switch (status) {
            case CURRENT:
                this.isActive = 1;
                break;
            case FINISHED:
                this.isActive = 2;
                break;
            case CLOSED:
                this.isActive = 0;
                break;
        }
    }


    public String pathBarImage() {
        return Const.IMAGES_ROOT_FOLDER  + courseNameID + "/" + "bar_card.png";
    }
    public String pathItemCardImage() {
        return Const.IMAGES_ROOT_FOLDER  + courseNameID + "/" + "item_card.png";
    }
}
