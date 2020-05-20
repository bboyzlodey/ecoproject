package skarlat.dev.ecoproject.includes.database;

import android.util.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import skarlat.dev.ecoproject.Course;
import skarlat.dev.ecoproject.EcoCard;
import skarlat.dev.ecoproject.EcoSoviet;
import skarlat.dev.ecoproject.includes.database.dao.CardsDao;
import skarlat.dev.ecoproject.includes.database.dao.CourseDao;
import skarlat.dev.ecoproject.includes.database.dao.SovietsDao;

/**
 * @Class - помошник в обработке данных для добавления\взятия данных
 */
public class DatabaseHelper {

    private AppDatabase db = App.getInstance().getDatabase();
    private CourseDao coursesDao = db.courseDao();
    private CardsDao cardsDao = db.cardsDao();
    private SovietsDao sovietsDao = db.sovietsDao();

    /**
     * функции получения объектов Course из базы
     * @return Courses
     */
    public List<Course> getAllCourses(){
        return coursesDao.getAll();
    }

    public Course getCurrentCourse(){
        return coursesDao.getCurrentCourse();
    }

    public Course getCourseByName(String courseName){
        return coursesDao.getByCourseID(courseName);
    }


    /**
     * Функции получения объектов карточек из базы
     * @return EcoCards
     */
    public List<EcoCard> getAllCards(){
        return cardsDao.getAll();
    }

    public List<EcoCard> getAllCardsByCourseNameID(String nameID){
        return cardsDao.getAllByCurs(nameID);
    }

    /**
     * Функции получения объектов советов
     * @param cardName
     * @return EcoSiviets
     */
    public List<EcoSoviet> getAllByCardName(String cardName){
        return sovietsDao.getAllByCardNameID(cardName);
    }

    public List<EcoSoviet> getAllFavorite(){
        return sovietsDao.getAllFavorite();
    }


    /**
     * Возвращает текст остатка карточек в курсе
     * @param courseNameID
     * @return String
     */

    public String getLeftCards(String courseNameID){
        int active = cardsDao.getAllActive(courseNameID).size();
        int allCards = cardsDao.getAllByCurs(courseNameID).size();
        int left = allCards - active;
        String text = "Осталось " + left;

        left = left % 100;
        int left1 = left % 10;

        if (left == 0)
            return "Курс пройден";
        if (left > 10 && left < 20)
            return text + " карточек";
        if (left1 > 1 && left1 < 5)
            return text + " карточки";
        if (left1 == 1)
            return "Осталась " + left + " карточка";
        return text + " карточек";
    }


    /**
     * Обновляет текущий курс для главного меню курсов
     * @param courseName
     */

    public void upDateIsCurrentCourse(String courseName){
        Course next = coursesDao.getByCourseID(courseName);
        Course last = coursesDao.getCurrentCourse();

        if (last != null && last.isActive == 1 ){
            if (last.getProgressBar() >= 100)
                last.isActive = 2;
            else
                last.isActive = 0;
        }
        next.isActive = 1;

        if (last != null)
            coursesDao.update(last);
        coursesDao.update(next);
    }


    /**
     * Кодирование массива в формат JSON
     * @return String - строку в формате JSON
     * @throws JSONException
     * @param - не реализована
     */
    public String jsonEncode () throws JSONException {
        JSONObject obj = new JSONObject();
        JSONObject resultJson = new JSONObject();

        obj.put("water_0", 1);
        obj.put("water_1", 1);
        obj.put("water_2", 1);

//		resultJson.put("checkList", obj);

        return obj.toString();
    }

    /**
     * Декодирование строки формата JSON в массив
     * @param json - строка в формате JSON
     * @return ArrayMap<String, Integer>
     * @throws JSONException
     */
    public ArrayMap<String, Integer> jsonDecode(String json) throws JSONException {
        JSONObject parser = new JSONObject(json);

        ArrayMap<String, Integer> map = new ArrayMap<>();

        Iterator<String> iterator = parser.keys();

        while (iterator.hasNext()){
            String key = iterator.next();
            map.put(key, (Integer) parser.get(key));
        }

        return map;
    }


}
