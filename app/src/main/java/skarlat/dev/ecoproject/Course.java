package skarlat.dev.ecoproject;

import android.util.Log;

import java.io.Serializable;

import skarlat.dev.ecoproject.includes.DatabaseHelper;

public class Course extends AbstractEco implements Serializable {
    private static final String KEY_LOG= "Course";
    
    private enum Status{
        CLOSED,
        CURRENT,
        FINISHED;
    }
    
    Course.Status status;
    
    public Course(String name, String title, boolean status){
        super(name, title);
    }
    
    public Course(String name,String title, String desription,String fullDescription, int status) {
        super(name, title, desription,fullDescription, status);
        switch (status){
            case 0:
                this.status = Status.CLOSED;
                break;
            case 1:
                this.status = Status.CURRENT;
                break;
            case 2:
                this.status = Status.FINISHED;
        }
        Log.d(KEY_LOG, "status is: " + this.status.name());
    }
    
    @Override
    public Enum getStatus() {
        return null;
    }
    
}
