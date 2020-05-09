package skarlat.dev.ecoproject;

import android.util.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Class - помошник в обработке данных для добавления\взятия данных
 */
public class DatabaseHelper {

    private AppDatabase db = App.getInstance().getDatabase();
    private CursCardDao cursCardDao = db.cursCardDao();

    /**
     * Список добавленных курсов
     */
    DatabaseHelper(){
        init("firstStep");
    }

    /**
     * Возвращает значение прогресс бара курса
     * @param cursName - имя нужного курса
     * @return int 0-100%
     */
    public int getCursProgressBar(String cursName) {
        CursCard curs = cursCardDao.getByCursID(cursName);
        return curs.progressBar;
    }

    /**
     * Обновление данных курса
     * @param cursName - имя нужного курса
     * @param progressBar - данные для обновления
     * @param checkList - данные для обновления
     */
    public void upDateCurs(String cursName, int progressBar, String checkList){
        CursCard curs = cursCardDao.getByCursID(cursName);
        curs.progressBar = progressBar;
        cursCardDao.update(curs);
    }

    /**
     * Создание таблицы в базе данных при первом подключении
     * @param cursName - имя курса по которому будет вестись поиск таблицы
     */
    public void init(String cursName){

        CursCard curs = cursCardDao.getByCursID(cursName);

        if (curs == null){
            CursCard initCurs = new CursCard();
            initCurs.id = 1;
            initCurs.cursID = cursName;
            initCurs.progressBar = 0;
            initCurs.checkList = null;
            cursCardDao.insert(initCurs);
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
