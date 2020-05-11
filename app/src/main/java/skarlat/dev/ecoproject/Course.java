package skarlat.dev.ecoproject;

import java.io.Serializable;

import skarlat.dev.ecoproject.includes.DatabaseHelper;

public class Course extends AbstractEco implements Serializable {
    public Course(String name,String title, String desription,String fullDescription, int status) {
        super(name, title, desription,fullDescription, status);
    }
    public Course(String name, String title, boolean status){
        super(name, title);
    }
}
