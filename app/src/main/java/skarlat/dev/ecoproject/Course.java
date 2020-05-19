package skarlat.dev.ecoproject;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import skarlat.dev.ecoproject.includes.database.App;
import skarlat.dev.ecoproject.includes.database.AppDatabase;
import skarlat.dev.ecoproject.includes.database.dao.CourseDao;

@Entity
public class Course implements EcoInterface, Serializable {

    @PrimaryKey
    @NonNull
    public String courseNameID;

    public String title;

    public String description;

    public String fullDescription;

    public int progressBar;

    public int isActive;

    public enum Status{
        CLOSED,
        CURRENT,
        FINISHED;
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
        switch (isActive){
            case 1:
                return Status.CURRENT;
            case 2:
                return Status.FINISHED;
            default:
                return Status.CLOSED;
        }
    }

    public int getProgressBar(){
        return this.progressBar;
    }

    public void setProgressBar(int progress){
        this.progressBar = progress;
    }

    public void setStatus(Course.Status status){
        switch (status){
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

    public void upDate(int progressBar, Course.Status status){
        AppDatabase db = App.getInstance().getDatabase();
        CourseDao coursesDao = db.courseDao();
        setProgressBar(progressBar);
        setStatus(status);
        coursesDao.update(this);
    }
}
