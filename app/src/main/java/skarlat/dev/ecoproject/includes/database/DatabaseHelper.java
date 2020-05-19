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
     * Курсы
     */
//    public ArrayMap<String, Course> getAllCourses(){
//        List<Course> list = coursesDao.get
//        for (int i = 0; i < list.size(); i++) {
//            coursesMap.put(list.get(i).getName(),list.get(i));
//        }
//        return this.coursesMap;
//    }

    public List<Course> getAllCourses(){
        return coursesDao.getAll();
    }

    public Course getCurrentCourse(){
        return coursesDao.getCurrentCourse();
    }

    public Course getCourseByName(String courseName){
        return coursesDao.getByCourseID(courseName);
    }

////////////////////////////


    /**
     * карточки
     */

    public List<EcoCard> getAllCards(){
        return cardsDao.getAll();
    }

    public List<EcoCard> getAllCardsByCourseNameID(String nameID){
        return cardsDao.getAllByCurs(nameID);
    }

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

    ///////////////////////////////////////////
//    /**
//     * Возвращает значение прогресс бара курса
//     * @param cursName - имя нужного курса
//     * @return int 0-100%
//     */
//    public int getCursProgressBar(String cursName) {
//        СoursesDB curs = coursesDao.getByCursID(cursName);
//        return curs.progressBar;
//    }


//    public ArrayMap<String, Object> getAllCourses(){
//        return this.courses;
//    }





//    public List<EcoCard> getListOfCards(){
//        return this.cards;
//    }

//    private void initCourses(){
//        List<СoursesDB> courseDB = coursesDao.getAll();
//        СoursesDB status;
//        status = coursesDao.getByCursID("lvl-1");
//        courses.put("lvl-1",new Course("lvl-1",
//                                            "Первые шаги","Первые шаги",
//                                        "Этот курс покажет, " +
//                                                "что помогать планете можно несложными действиями. " +
//                                                "Расскажет, как экономить воду, зачем спасать " +
//                                                "бананы и что такое добрые крышечки.",
//                                    "Количество пресной воды на Земле невелико, " +
//                                            "но относительно статично. Проблема в том, что люди " +
//                                            "используют её запасы быстрее, чем природа может пополнить" +
//                                            " их естественным путем. При этом пресная вода распределена " +
//                                            "неравномерно, поэтому для многих она в дефиците. Из-за пересыхания " +
//                                            "рек плохо рыбам и другим животным. У них нет такого краника, " +
//                                            "из которого течет вода." +
//                                            "<<///img///>>" +
//                                            "Поэтому чем меньше воды мы расходуем в никуда, тем больше вероятность, " +
//                                            "что водные ресурсы не исчерпают себя очень быстро. " +
//                                            "Плюс простыми действиями можно сократить сумму в счетах на " +
//                                            "оплату — немного, но всё равно приятно!",status == null ? 0 : status.isActive
//                                        ));
//        status = coursesDao.getByCursID("lvl-2");
//        courses.put("lvl-2",new Course("lvl-2","Продвинутый",
//                "Сложнее","Сложнее", "null",status == null ? 0 : status.isActive));
//        status = coursesDao.getByCursID("lvl-3");
//        courses.put("lvl-3",new Course("lvl-3","Профи",
//                "Очень сложно","Очень сложно", "null",status == null ? 0 : status.isActive));
//        status = coursesDao.getByCursID("lvl-4");
//        courses.put("lvl-4",new Course("lvl-4","Гипер сложно",
//                "ну попробуй","ну попробуй", "null",status == null ? 0 : status.isActive));
//        status = coursesDao.getByCursID("lvl-5");
//        courses.put("lvl-5",new Course("lvl-5","Ты не справишься!",
//                "Спорим?","Спорим?", "null",status == null ? 0 : status.isActive));
//        status = coursesDao.getByCursID("lvl-6");
//        courses.put("lvl-6",new Course("lvl-6","Успокойся парень",
//                "Почитать полезное","Почитать полезное", "null",status == null ? 0 : status.isActive));
//    }

//    public void initCards(String cursName){
//        CardsDB cardsDB;
//
//        switch (cursName){
//            case "lvl-1":
//                cards.clear();
//                cardsDB = intiCardDB(cursName,"lvl-1.1");
//                cards.add(new EcoCard("lvl-1.1",
//                                        "Сберегаем электроэнергию",
//                                        "В ладошках молния",
//                                        "Зачем экономить электроэнергию?\n<img name=nature_1>\n" +
//                                                "В России для производства электроэнергии используют теплоэлектростанции (ТЭС 67,7%)," +
//                                                " гидроэлектростанции (20%) и атомные станции (12%). На наиболее экологичную солнечную," +
//                                                " ветровую и гидротермальную энергетику, а также электростанции на биотопливе приходится" +
//                                                " всего около 0,1% в России (а, например, во Франции более 20%).\n" +
//                                                "ТЭС получают электроэнергию за счет сжигания газа, угля и нефти." +
//                                                " В год для производства электроэнергии на ТЭС нужно около 160 млрд" +
//                                                " м3 природного газа, около 110 млн тонн угля и 3 млн тонн нефти." +
//                                                " Представляешь какой сумасшедший расход ресурсов?\n" +
//                                                "При сжигании угля в атмосферу выбрасываются: оксиды азота, оксид углерода," +
//                                                " фтористые соединения, бензапирен, окисиды серы, сажа, неорганическая" +
//                                                " пыль и другие. После сжигания образуется большое количество" +
//                                                " золы с большим содержанием токсичных химических элементов. На ТЭС стоят" +
//                                                " специальные очистные конструкции, но они не способны работать со 100%-м " +
//                                                "улавливанием всех веществ, в то же время золу нужно куда-то девать.\n" +
//                                                "Поэтому на вопрос *зачем сберегать электроэнергию?*, ответ прост," +
//                                                " так мы бережем окружающую среду и уменьшаем негативное воздействие.\n" +
//                                                "Любое зарядное устройство даже при полном бездействии включенное" +
//                                                " в розетку продолжает расходовать электроэнергию. Некоторые зарядные устройства," +
//                                                " включенные в розетку, могут привести к возгоранию.\n" +
//                                                "При покупке бытовой техники обращайте внимание на модели с наименьшим энергопотреблением" +
//                                                " с маркировкой А+++, A++, A+, и далее A, B, C, D.\n<img><img>\n" +
//                                                "Правда такие приборы стоят дороже, но такой прибор себя точно окупит" +
//                                                " в будущем, как и аккумуляторные батарейки. Аккумуляторные батарейки можно" +
//                                                " использовать многократно, подзаряжая их через розетку.\n",
//                                        cardsDB.isActive == 2 ? 2 : 1));
//                cardsDB = intiCardDB(cursName,"lvl-1.2");
//                cards.add(new EcoCard("lvl-1.2","Экономим водные ресурсы", "В ладошках капелька воды", null,cardsDB.isActive));
//                cardsDB = intiCardDB(cursName,"lvl-1.3");
//                cards.add(new EcoCard("lvl-1.3","Сберегаем тепло", "В ладошках градусник", null,cardsDB.isActive));
//                cardsDB = intiCardDB(cursName,"lvl-1.4");
//                cards.add(new EcoCard("lvl-1.4","Сберегаем пищу", "В ладошках банан", null,cardsDB.isActive));
//                break;
//            case "lvl-2":
//                cards.clear();
//                cardsDB = intiCardDB(cursName,"1");
//                cards.add(new EcoCard("1","title", "desc", null, cardsDB.isActive == 2 ? 2 : 1));
//                cardsDB = intiCardDB(cursName,"2");
//                cards.add(new EcoCard("2","title", "desc", null,cardsDB.isActive));
//                cardsDB = intiCardDB(cursName,"3");
//                cards.add(new EcoCard("3","title", "desc", null,cardsDB.isActive));
//                break;
//            default:
//                cards.clear();
//                break;
//        }
//    }

//    public void initSoviets(InputStream inputStream){
//
//        List<SovietsDB> sovietsDB = sovietsDao.getAll();
//
//        if (sovietsDB == null || sovietsDB.size() == 0){
//            CSVFile csvFile = new CSVFile(inputStream);
//            List list = csvFile.read();
//            for (int i = 0; i < list.size() - 1; i++) {
//                if (list.get(i) == null )
//                    continue;
//                String[] row = (String[]) list.get(i);
//                SovietsDB initSoviet = new SovietsDB();
//                initSoviet.cardID = row[0];
//                initSoviet.title = row[1];
//                initSoviet.description = row[2];
//                initSoviet.isFavorite = 0;
//                sovietsDao.insert(initSoviet);
//            }
//        }
//
//    }




//    public boolean isActive(String cardName){
//        CardsDB card = cardsDao.getByCardID(cardName);
//        if (card.isActive == 0)
//            return false;
//        else
//            return true;
//    }



    public List<EcoSoviet> getAllByCardName(String cardName){
        return sovietsDao.getAllByCardNameID(cardName);
    }

    public List<EcoSoviet> getAllFavorite(){
        return sovietsDao.getAllFavorite();
    }

//    public void upDateCourse(String cursName, int progressBar, int isActive){
//        СoursesDB curs = coursesDao.getByCursID(cursName);
//        curs.progressBar = progressBar;
//        curs.isActive = isActive;
//        coursesDao.update(curs);
//    }

    public void upDateIsCurrentCourse(String courseName){
        Course next = coursesDao.getByCourseID(courseName);
        Course last = coursesDao.getCurrentCourse();

        if (last != null && last.isActive == 1 ){
            last.isActive = 0;
        }
        next.isActive = 1;

        coursesDao.update(next);
        if (last != null)
            coursesDao.update(last);
    }
//
//    @SuppressLint("WrongConstant")
//    public void upDateCard(String cardName, int isActive){
//        CardsDB cardsDB = cardsDao.getByCardID(cardName);
//        if (cardsDB == null) {
//            Log.println(1, "error", "Object CardsDB = null");
//            return;
//        }
//        cardsDB.isActive = isActive;
//        cardsDao.update(cardsDB);
//    }

    /**
     * delete method
     * @param cursName
     */

//    private CardsDB intiCardDB(String cursName, String cardName){
//
//        CardsDB cardsDB = cardsDao.getByCardID(cardName);
//        CardsDB initCard;
//
//        if (cardsDB == null){
//            initCard = new CardsDB();
//            initCard.cardID = cardName;
//            initCard.cursID = cursName;
//            initCard.isActive = 0;
//            cardsDao.insert(initCard);
//        }
//        else{
//            return cardsDB;
//        }
//        return initCard;
//    }


//    /**
//     * Создание таблицы в базе данных при первом подключении
//     */
//    private void initCoursesDB(){
//
//        for (int i = 0; i < courses.size(); i++) {
//            String key = courses.keyAt(i);
//            СoursesDB curs = coursesDao.getByCursID(key);
//            if (curs == null){
//                СoursesDB initCurs = new СoursesDB();
//                initCards(key);
//                int count = cards.size();
//                initCurs.id = 1;
//                initCurs.cursID = key;
//                initCurs.progressBar = 0;
//                initCurs.isActive = 0;
//                initCurs.countAllCards = count;
//                coursesDao.insert(initCurs);
//            } else
//                curs = null;
//        }
//    }


//    private void initCardsDB(String cursName){
//
//        List<CardsDB> cardDB = cardsDao.getAllByCurs(cursName);
//        EcoCard ecoCard;
//
//        if ( cardDB.isEmpty() ){
//            for (int i = 0; i < cards.size() ; i++) {
//                CardsDB initCard = new CardsDB();
//                ecoCard = (EcoCard) cards.get(i);
//                initCard.cardID = ecoCard.getName();
//                initCard.cursID = cursName;
////                if ( ecoCard.getStatus() )
////                    initCard.isActive = 1;
////                else
////                    initCard.isActive = 0;
//            }
//        }else
//            cardDB = null;
//
//    }

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
