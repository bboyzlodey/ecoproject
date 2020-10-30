package skarlat.dev.ecoproject.includes.dataclass;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Education implements Serializable {
    List<Course> courses;
    Map<Course.Status, List<Course>> mapOfCouses;

    public Education(InputStream inputStream) {

    }

}
