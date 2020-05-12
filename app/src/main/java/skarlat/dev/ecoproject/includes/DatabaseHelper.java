package skarlat.dev.ecoproject.includes;

import android.util.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import skarlat.dev.ecoproject.Course;
import skarlat.dev.ecoproject.EcoCard;
import static skarlat.dev.ecoproject.Course.Status.CLOSED;
import static skarlat.dev.ecoproject.Course.Status.CURRENT;
import static skarlat.dev.ecoproject.Course.Status.FINISHED;

/**
 * @Class - помошник в обработке данных для добавления\взятия данных
 */
public class DatabaseHelper {

    private AppDatabase db = App.getInstance().getDatabase();
    private CoursesDao coursesDao = db.cursCardDao();
    private CardsDao cardsDao = db.cardsDao();
    private SovietsDao sovietsDao = db.sovietsDao();
    private ArrayMap<String,Object> courses = new ArrayMap<>();
    private List<Object> cards = new ArrayList<>();


    public DatabaseHelper(){
        initCourses();
    }

    /**
     * Список добавленных курсов
     */
    public DatabaseHelper(InputStream inputStream){
            initCourses();
            initCoursesDB();
            initSoviets(inputStream);
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

    public ArrayMap<String, Object> getAllCourses(){
        return this.courses;
    }

    public Course getCurrentCourse(){

        СoursesDB courseDB = coursesDao.getCurrentCurs();
        if (courseDB == null)
            return null;

        String courseName = courseDB.cursID;

        return (Course) courses.get(courseName);
    }

    public СoursesDB getCourseByName(String courseName){
        return coursesDao.getByCursID(courseName);
    }


    public List<Object> getListOfCards(){
        return this.cards;
    }

    private void initCourses(){
        List<СoursesDB> courseDB = coursesDao.getAll();
        СoursesDB status;
        status = coursesDao.getByCursID("lvl-1");
        courses.put("lvl-1",new Course("lvl-1",
                                            "Первые шаги","Первые шаги",
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
                                            "оплату — немного, но всё равно приятно!",status == null ? 0 : status.isActive
                                        ));
        status = coursesDao.getByCursID("lvl-2");
        courses.put("lvl-2",new Course("lvl-2","Продвинутый",
                "Сложнее","Сложнее", "null",status == null ? 0 : status.isActive));
        status = coursesDao.getByCursID("lvl-3");
        courses.put("lvl-3",new Course("lvl-3","Профи",
                "Очень сложно","Очень сложно", "null",status == null ? 0 : status.isActive));
        status = coursesDao.getByCursID("lvl-4");
        courses.put("lvl-4",new Course("lvl-4","Гипер сложно",
                "ну попробуй","ну попробуй", "null",status == null ? 0 : status.isActive));
        status = coursesDao.getByCursID("lvl-5");
        courses.put("lvl-5",new Course("lvl-5","Ты не справишься!",
                "Спорим?","Спорим?", "null",status == null ? 0 : status.isActive));
        status = coursesDao.getByCursID("lvl-6");
        courses.put("lvl-6",new Course("lvl-6","Успокойся парень",
                "Почитать полезное","Почитать полезное", "null",status == null ? 0 : status.isActive));
    }

    public void initCards(String cursName){
        CardsDB cardsDB;

        switch (cursName){
            case "firstStep":
                cards.clear();
                cardsDB = intiCardDB(cursName,"resourceSaving");
                cards.add(new EcoCard("resourceSaving","Экономим водные ресурсы", "Ресурсосбережение", null, 1));
                cardsDB = intiCardDB(cursName,"second");
                cards.add(new EcoCard("second",null, null, null,cardsDB.isActive));
                cardsDB = intiCardDB(cursName,"Third");
                cards.add(new EcoCard("Third",null, null, null,cardsDB.isActive));
                break;

            default:
                return;
        }
    }

    public void initSoviets(InputStream inputStream){

        List<SovietsDB> sovietsDB = sovietsDao.getAll();

        if (sovietsDB == null){
            CSVFile csvFile = new CSVFile(inputStream);
            List list = csvFile.read();
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i) == null )
                    continue;
                String[] row = (String[]) list.get(i);
                SovietsDB initSoviet = new SovietsDB();
                initSoviet.cardID = row[0];
                initSoviet.title = row[1];
                initSoviet.description = row[2];
                initSoviet.isFavorite = 0;
                sovietsDao.insert(initSoviet);
            }
        }

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
    public void upDateCourse(String cursName, int progressBar, int isActive){
        СoursesDB curs = coursesDao.getByCursID(cursName);
        curs.progressBar = progressBar;
        curs.isActive = isActive;
        coursesDao.update(curs);
    }

    public void upDateCard(String cardName, int isActive){
        CardsDB cardsDB = cardsDao.getByCardID(cardName);
        cardsDB.isActive = isActive;
        cardsDao.update(cardsDB);
    }

    /**
     * Создание таблицы в базе данных при первом подключении
     */
    private void initCoursesDB(){

        for (int i = 0; i < courses.size(); i++) {
            String key = courses.keyAt(i);
            СoursesDB curs = coursesDao.getByCursID(key);
            if (curs == null){
                СoursesDB initCurs = new СoursesDB();
                initCurs.id = 1;
                initCurs.cursID = key;
                initCurs.progressBar = 0;
                initCurs.isActive = 0;
                coursesDao.insert(initCurs);
            } else
                curs = null;
        }


    }


    private CardsDB intiCardDB(String cursName, String cardName){

        CardsDB cardsDB = cardsDao.getByCardID(cardName);
        CardsDB initCard;

        if (cardsDB == null){
            initCard = new CardsDB();
            initCard.cardID = cardName;
            initCard.cursID = cursName;
            initCard.isActive = 0;
        }
        else{
            return cardsDB;
        }
        return initCard;
    }

    /**
     * delete method
     * @param cursName
     */
    private void initCardsDB(String cursName){

        List<CardsDB> cardDB = cardsDao.getAllByCurs(cursName);
        EcoCard ecoCard;

        if ( cardDB.isEmpty() ){
            for (int i = 0; i < cards.size() ; i++) {
                CardsDB initCard = new CardsDB();
                ecoCard = (EcoCard) cards.get(i);
                initCard.cardID = ecoCard.getName();
                initCard.cursID = cursName;
//                if ( ecoCard.getStatus() )
//                    initCard.isActive = 1;
//                else
//                    initCard.isActive = 0;
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
