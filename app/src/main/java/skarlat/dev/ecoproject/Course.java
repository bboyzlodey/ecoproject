package skarlat.dev.ecoproject;

import skarlat.dev.ecoproject.includes.DatabaseHelper;

public class Course extends AbstractEco{
    public Course(String title, String desription, boolean status) {
        super(title, desription, status);
    }
    private DatabaseHelper db = new DatabaseHelper();
    public int getProgressBar(){
        return db.getCursProgressBar(getTitle());
    }

    public void upDate(int progressBar){
        db.upDateCourse(getTitle(),progressBar,"");
    }
}
