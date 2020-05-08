package skarlat.dev.ecoproject.includes;

import android.util.ArrayMap;

import androidx.room.Query;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * @Class - помошник в обработке данных для добавления\взятия данных
 */
public class DatabaseHelper {

    private AppDatabase db = App.getInstance().getDatabase();
    private CoursesDao coursesDao = db.cursCardDao();
    private CardsDao cardsDao = db.cardsDao();

    /**
     * Список добавленных курсов
     */
    public DatabaseHelper(){
        init("firstStep");
    }

    /**
     * Возвращает значение прогресс бара курса
     * @param cursName - имя нужного курса
     * @return int 0-100%
     */
    public int getCursProgressBar(String cursName) {
        СoursesDB curs = coursesDao.getByCursID(cursName);
        return curs.progressBar;
    }

    public int getCountActiveCards(String cursName){
        List<CardsDB> cardsDB = cardsDao.getAllActive(cursName);
        return cardsDB.size();
    }


    public List getAllCards(String cursName){
        List<CardsDB> cards = cardsDao.getAllByCurs(cursName);

        return cards;
    }

    public boolean isActive(String cardName){
        CardsDB card = cardsDao.getByCardID(cardName);
        if (card.isActive == 0)
            return false;
        else
            return true;
    }

    /**
     * Обновление данных курса
     * @param cursName - имя нужного курса
     * @param progressBar - данные для обновления
     * @param checkList - данные для обновления
     */
    public void upDateCourse(String cursName, int progressBar, String checkList){
        СoursesDB curs = coursesDao.getByCursID(cursName);
        curs.progressBar = progressBar;
        coursesDao.update(curs);
    }

    public void upDateCard(String cardName, boolean isActive){
        CardsDB cardsDB = cardsDao.getByCardID(cardName);
        if (isActive == false)
            cardsDB.isActive = 0;
        else
            cardsDB.isActive = 1;
    }

    /**
     * Создание таблицы в базе данных при первом подключении
     * @param cursName - имя курса по которому будет вестись поиск таблицы
     */
    public void init(String cursName){

        СoursesDB curs = coursesDao.getByCursID(cursName);

        if (curs == null){
            СoursesDB initCurs = new СoursesDB();
            initCurs.id = 1;
            initCurs.cursID = cursName;
            initCurs.progressBar = 0;
            initCurs.checkList = null;
            coursesDao.insert(initCurs);
        } else
            curs = null;

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
