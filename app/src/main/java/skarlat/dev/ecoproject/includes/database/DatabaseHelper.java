package skarlat.dev.ecoproject.includes.database;

import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import skarlat.dev.ecoproject.App;
import skarlat.dev.ecoproject.includes.dataclass.Course;
import skarlat.dev.ecoproject.includes.dataclass.EcoCard;
import skarlat.dev.ecoproject.includes.dataclass.EcoSoviet;
import skarlat.dev.ecoproject.includes.database.dao.CardsDao;
import skarlat.dev.ecoproject.includes.database.dao.CourseDao;
import skarlat.dev.ecoproject.includes.database.dao.SovietsDao;

/**
 * @Class - помошник в обработке данных для добавления\взятия данных
 */
public class DatabaseHelper {

    private AppDatabase db = App.getDatabase();
    private CourseDao coursesDao = db.courseDao();
    private CardsDao cardsDao = db.cardsDao();
    private SovietsDao sovietsDao = db.sovietsDao();

    /**
     * функции получения объектов Course из базы
     *
     * @return Courses
     */
    public List<Course> getAllCourses() {
        return coursesDao.getAll();
    }

    public Course getCurrentCourse() {
        return coursesDao.getCurrentCourse();
    }

    public Course getCourseByName(String courseName) {
        return coursesDao.getByCourseID(courseName);
    }

    public ArrayList<String> keyStoreCourses() {
        List<Course> list = getAllCourses();
        ArrayList<String> keys = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            keys.add(list.get(i).courseNameID);
        }
        return keys;
    }

    /**
     * Функции получения объектов карточек из базы
     *
     * @return EcoCards
     */
    public List<EcoCard> getAllCards() {
        return cardsDao.getAll();
    }

    public List<EcoCard> getAllCardsByCourseNameID(String nameID) {
        return cardsDao.getAllByCurs(nameID);
    }

    /**
     * Функции получения объектов советов
     *
     * @param cardName
     * @return EcoSiviets
     */
    public List<EcoSoviet> getAllByCardName(String cardName) {
        return sovietsDao.getAllByCardNameID(cardName);
    }

    public List<EcoSoviet> getAll() {
        return sovietsDao.getAll();
    }

    public List<EcoSoviet> getAllFavorite() {
        return sovietsDao.getAllFavorite();
    }


    /**
     * Возвращает текст остатка карточек в курсе
     *
     * @param courseNameID
     * @return String
     */

    public String getLeftCards(String courseNameID) {
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
     *
     * @param courseName
     */

    public void upDateIsCurrentCourse(String courseName) {
        Course next = coursesDao.getByCourseID(courseName);
        Course last = coursesDao.getCurrentCourse();

        if (last != null && last.isActive == 1) {
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
     *
     * @param - не реализована
     * @return String - строку в формате JSON
     * @throws JSONException
     */
    public String jsonEncode() throws JSONException {
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
     *
     * @param json - строка в формате JSON
     * @return ArrayMap<String, Integer>
     * @throws JSONException
     */
    public ArrayMap<String, Integer> jsonDecode(String json) throws JSONException {
        JSONObject parser = new JSONObject(json);

        ArrayMap<String, Integer> map = new ArrayMap<>();

        Iterator<String> iterator = parser.keys();

        while (iterator.hasNext()) {
            String key = iterator.next();
            map.put(key, (Integer) parser.get(key));
        }

        return map;
    }

    private int getContentVersion() {
        Version version = db.versionDao().getVer();
        return (int) version.versionContent;
    }

    private int getProgressVersion() {
        Version version = db.versionDao().getVer();
        return version.versionUserBar;
    }

    private void updateTips(HashMap<String, Object> map, String cardName) {
        HashMap<String, Object> tips = (HashMap<String, Object>) map.get(cardName);
        for (HashMap.Entry entry : tips.entrySet()
        ) {
            HashMap<String, Object> tip = (HashMap<String, Object>) entry.getValue();
            EcoSoviet newEcoSoviet = new EcoSoviet();
            newEcoSoviet.isFavorite = 0;
            newEcoSoviet.cardNameID = cardName;
            newEcoSoviet.description = (String) tip.get("description");
            newEcoSoviet.title = (String) tip.get("title");
            sovietsDao.insert(newEcoSoviet);
        }
    }

    private void updateCards(HashMap<String, Object> map, String courseName) {
        HashMap<String, Object> cards = (HashMap<String, Object>) map.get("Cards");
        HashMap<String, Object> cardLvl = (HashMap<String, Object>) cards.get(courseName);

        for (HashMap.Entry entry : cardLvl.entrySet()
        ) {
            HashMap<String, Object> card = (HashMap<String, Object>) entry.getValue();
            EcoCard newCard = new EcoCard();
            long active = (long) card.get("isActive");
            newCard.isActive = (int) active;
            newCard.cardNameID = (String) Objects.requireNonNull(card.get("cardNameID"));
            newCard.courseNameID = courseName;
            newCard.description = (String) card.get("description");
            newCard.fullDescription = (String) card.get("fullDescription");
            newCard.title = (String) card.get("title");
            cardsDao.insert(newCard);
            updateTips((HashMap<String, Object>) map.get("Tips"), newCard.cardNameID);
        }
    }

    private void updateCourses(HashMap<String, Object> map) {
        ArrayList<String> keys = keyStoreCourses();
        HashMap<String, Object> courses = (HashMap<String, Object>) map.get("Courses");
        for (HashMap.Entry entry : courses.entrySet()
        ) {
            HashMap<String, Object> course = (HashMap<String, Object>) entry.getValue();
            if (!keys.contains(course.get("courseNameID"))) {
                Course newCourse = new Course();
                newCourse.courseNameID = (String) course.get("courseNameID");
                newCourse.isActive = 0;
                newCourse.description = (String) course.get("description");
                newCourse.fullDescription = (String) course.get("fullDescription");
                newCourse.title = (String) course.get("title");
                newCourse.progressBar = 0;
                coursesDao.insert(newCourse);
                updateCards(map, newCourse.courseNameID);
            }
        }
    }


    public void updateDatabase() {
        FirebaseDatabase mDb = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDb.getReference();
        mRef.child("content").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                GenericTypeIndicator<HashMap<String, Object>> indicator = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                HashMap<String, Object> map = dataSnapshot.getValue(indicator);

                long verFirebase = (long) map.get("version");
                int verLocalbase = getContentVersion();
                if (verFirebase > verLocalbase) {
                    updateCourses(map);
                    Version version = db.versionDao().getVer();
                    version.versionContent = (int) verFirebase;
                    db.versionDao().update(version);
                }
                updateUserProfile();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateUserProgress(HashMap<String, Object> map) {
        HashMap<String, Objects> courseProgress = (HashMap<String, Objects>) map.get("Courses");
        HashMap<String, Objects> cardProgress = (HashMap<String, Objects>) map.get("Cards");
        HashMap<String, Objects> tip = (HashMap<String, Objects>) map.get("Tips");
        for (HashMap.Entry entry : courseProgress.entrySet()
        ) {
            Course course = db.courseDao().getByCourseID((String) entry.getKey());
            HashMap<String, Object> temp = (HashMap<String, Object>) entry.getValue();
            long progress = (long) temp.get("progress");
            course.progressBar = (int) progress;
            if (progress >= 100)
                course.setStatus(Course.Status.FINISHED);
            db.courseDao().update(course);
        }
        for (HashMap.Entry entry : cardProgress.entrySet()
        ) {
            EcoCard card = db.cardsDao().getByCardID((String) entry.getKey());
            HashMap<String, Object> temp = (HashMap<String, Object>) entry.getValue();
            long status = (long) temp.get("status");
            card.isActive = (int) status;
            db.cardsDao().update(card);
        }

        for (HashMap.Entry entry : tip.entrySet()
        ) {
            EcoSoviet ecoSoviet = db.sovietsDao().getByTipID(Integer.parseInt((String) entry.getKey()));
            HashMap<String, Object> temp = (HashMap<String, Object>) entry.getValue();
            long status = (long) temp.get("status");
            ecoSoviet.isFavorite = (int) status;
            db.sovietsDao().update(ecoSoviet);
        }

    }

    public void updateFirebaseProgress(String group, String child, String key, int value) {
        FirebaseDatabase mDb = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDb.getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
//        if (child != null && group != null)
//            mRef.child(user.getUid()).child("progress").child(group).child(child).child(key).setValue(value);
//        else
//            mRef.child(user.getUid()).child("progress").child(key).setValue(value);
    }

    public void updateUserProfile() {
        FirebaseDatabase mDb = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDb.getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mRef.setPriority(1);
        mRef.child(user.getUid()).child("progress").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Object>> indicator = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                HashMap<String, Object> map = dataSnapshot.getValue(indicator);
                if (map == null) {
                    updateFirebaseProgress(null, null, "version", 1);
                } else {
                    long ver = (long) map.get("version");
                    int localVer = getProgressVersion();
                    if (ver > localVer) {
                        updateUserProgress(map);
                        Version version = db.versionDao().getVer();
                        version.versionUserBar = (int) ver;
                        db.versionDao().update(version);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
