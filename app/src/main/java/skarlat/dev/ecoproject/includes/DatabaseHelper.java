package skarlat.dev.ecoproject.includes;

import android.util.ArrayMap;

import androidx.room.Query;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import skarlat.dev.ecoproject.Course;
import skarlat.dev.ecoproject.EcoCard;

/**
 * @Class - помошник в обработке данных для добавления\взятия данных
 */
public class DatabaseHelper {

    private AppDatabase db = App.getInstance().getDatabase();
    private CoursesDao coursesDao = db.cursCardDao();
    private CardsDao cardsDao = db.cardsDao();
    private ArrayMap<String,Object> courses = new ArrayMap<>();
    private ArrayList<Object> cards = new ArrayList<>();


    public DatabaseHelper(){
    }

    /**
     * Список добавленных курсов
     */
    public DatabaseHelper(boolean init){
        if ( init ){
            initCourses();
            initDB("firstStep");
        }
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

    public ArrayMap<String,Object> getListOfCourses(){
        return this.courses;
    }

    public ArrayList<Object> getListOfCards(){
        return this.cards;
    }

    private void initCourses(){
        courses.put("firstStep",new Course("firstStep",
                                            "Первые шаги",
                                        "Этот курс покажет, " +
                                                "что помогать планете можно несложными действиями. " +
                                                "Расскажет, как экономить воду, зачем спасать " +
                                                "бананы и что такое добрые крышечки.",
                                    "Количество пресной воды на Земле невелико, " +
                                            "но относительно статично. Проблема в том, что люди " +
                                            "используют её запасы быстрее, чем природа может пополнить" +
                                            " их естественным путем. При этом пресная вода распределена " +
                                            "неравномерно, поэтому для многих она в дефиците. Из-за пересыхания " +
                                            "рек плохо рыбам и другим животным. У них нет такого краника, " +
                                            "из которого течет вода." +
                                            "<<///img///>>" +
                                            "Поэтому чем меньше воды мы расходуем в никуда, тем больше вероятность, " +
                                            "что водные ресурсы не исчерпают себя очень быстро. " +
                                            "Плюс простыми действиями можно сократить сумму в счетах на " +
                                            "оплату — немного, но всё равно приятно!",
                                        true));
    }

    public void initCards(String cursName){
        switch (cursName){
            case "firstStep":
                cards.clear();
                cards.add(new EcoCard("resourceSaving","Экономим водные ресурсы", "Ресурсосбережение", null,true));
                cards.add(new EcoCard("second",null, null, null,false));
                cards.add(new EcoCard("Third",null, null, null,false));
                break;

            default:
                return;
        }

        initCardsDB(cursName);
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

    public String getLeftCards(String cursName){
        List<CardsDB> cardDB = cardsDao.getAllActive(cursName);
        int active = cardDB.size();
        int allCards = cards.size();
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
     * Обновление данных курса
     * @param cursName - имя нужного курса
     * @param progressBar - данные для обновления
     */
    public void upDateCourse(String cursName, int progressBar){
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
    private void initDB(String cursName){

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

    private void initCardsDB(String cursName){

        List<CardsDB> cardDB = cardsDao.getAllByCurs(cursName);
        EcoCard ecoCard;

        if ( cardDB.isEmpty() ){
            for (int i = 0; i < cards.size() ; i++) {
                CardsDB initCard = new CardsDB();
                ecoCard = (EcoCard) cards.get(i);
                initCard.cardID = ecoCard.getName();
                initCard.cursID = cursName;
                if ( ecoCard.getStatus() )
                    initCard.isActive = 1;
                else
                    initCard.isActive = 0;
            }
        }else
            cardDB = null;

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
