package skarlat.dev.ecoproject.includes.database

import android.util.ArrayMap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromCallable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.core.SettingsManager
import skarlat.dev.ecoproject.includes.dataclass.Course
import skarlat.dev.ecoproject.includes.dataclass.EcoCard
import skarlat.dev.ecoproject.includes.dataclass.EcoSoviet
import java.util.*
import java.util.concurrent.Callable

/**
 * @Class - помошник в обработке данных для добавления\взятия данных
 */
class DatabaseHelper {
    private val db = EcoTipsApp.getDatabase()
    private val coursesDao = db.courseDao()
    private val cardsDao = db.cardsDao()
    private val sovietsDao = db.sovietsDao()

    /**
     * функции получения объектов Course из базы
     *
     * @return Courses
     */
    val allCourses: List<Course>
        get() = coursesDao.all

    val currentCourse: Course
        get() = coursesDao.currentCourse

    fun getCourseByName(courseName: String?): Course {
        return coursesDao.getByCourseID(courseName)
    }

    fun keyStoreCourses(): ArrayList<String> {
        val list = allCourses
        val keys = ArrayList<String>()
        for (i in list.indices) {
            keys.add(list[i].courseNameID)
        }
        return keys
    }

    /**
     * Функции получения объектов карточек из базы
     *
     * @return EcoCards
     */
    val allCards: List<EcoCard>
        get() = cardsDao.all

    fun getAllCardsByCourseNameID(nameID: String?): List<EcoCard> {
        return cardsDao.getAllByCurs(nameID)
    }

    /**
     * Функции получения объектов советов
     *
     * @param cardName
     * @return EcoSiviets
     */
    fun getAllByCardName(cardName: String?): List<EcoSoviet> {
        return sovietsDao.getAllByCardNameID(cardName)
    }

    val all: List<EcoSoviet>
        get() = sovietsDao.all

    val allFavorite: List<EcoSoviet>
        get() = sovietsDao.allFavorite

    /**
     * Возвращает текст остатка карточек в курсе
     *
     * @param courseNameID
     * @return String
     */
    fun getLeftCards(courseNameID: String?): String {
        val active = cardsDao.getAllActive(courseNameID).size
        val allCards = cardsDao.getAllByCurs(courseNameID).size
        var left = allCards - active
        val text = "Осталось $left"
        left = left % 100
        val left1 = left % 10
        if (left == 0) return "Курс пройден"
        if (left > 10 && left < 20) return "$text карточек"
        if (left1 > 1 && left1 < 5) return "$text карточки"
        return if (left1 == 1) "Осталась $left карточка" else "$text карточек"
    }

    /**
     * Обновляет текущий курс для главного меню курсов
     *
     * @param courseName
     */
    fun upDateIsCurrentCourse(courseName: String?) {
        val next = coursesDao.getByCourseID(courseName)
        val last = coursesDao.currentCourse
        if (last != null && last.isActive == 1) {
            if (last.getProgressBar() >= 100) last.isActive = 2 else last.isActive = 0
        }
        next.isActive = 1
        if (last != null) coursesDao.update(last)
        coursesDao.update(next)
    }

    /**
     * Кодирование массива в формат JSON
     *
     * @param - не реализована
     * @return String - строку в формате JSON
     * @throws JSONException
     */
    @Throws(JSONException::class)
    fun jsonEncode(): String {
        val obj = JSONObject()
        val resultJson = JSONObject()
        obj.put("water_0", 1)
        obj.put("water_1", 1)
        obj.put("water_2", 1)

//		resultJson.put("checkList", obj);
        return obj.toString()
    }

    /**
     * Декодирование строки формата JSON в массив
     *
     * @param json - строка в формате JSON
     * @return ArrayMap<String></String>, Integer>
     * @throws JSONException
     */
    @Throws(JSONException::class)
    fun jsonDecode(json: String?): ArrayMap<String, Int> {
        val parser = JSONObject(json)
        val map = ArrayMap<String, Int>()
        val iterator = parser.keys()
        while (iterator.hasNext()) {
            val key = iterator.next()
            map[key] = parser[key] as Int
        }
        return map
    }

    private val contentVersion: Int
        private get() {
            val version = db.versionDao().ver
            return version.versionContent.toInt()
        }

    private val progressVersion: Int
        private get() {
            val version = db.versionDao().ver
            return version.versionUserBar
        }

    private fun updateTips(map: HashMap<String, Any>?, cardName: String) {
        val tips = map!![cardName] as HashMap<String, Any>?
        for ((_, value) in tips!!) {
            val tip = value as HashMap<String, Any>
            val newEcoSoviet = EcoSoviet()
            newEcoSoviet.isFavorite = 0
            newEcoSoviet.cardNameID = cardName
            newEcoSoviet.description = tip["description"] as String?
            newEcoSoviet.title = tip["title"] as String?
            sovietsDao.insert(newEcoSoviet)
        }
    }

    private fun updateCards(map: HashMap<String?, Any?>, courseName: String) {
        val cards = map["Cards"] as HashMap<String, Any>?
        val cardLvl = cards!![courseName] as HashMap<String, Any>?
        for ((_, value) in cardLvl!!) {
            val card = value as HashMap<String, Any>
            val newCard = EcoCard()
            val active = card["isActive"] as Long
            newCard.isActive = active.toInt()
            newCard.cardNameID = (Objects.requireNonNull(card["cardNameID"]) as String?)!!
            newCard.courseNameID = courseName
            newCard.description = card["description"] as String?
            newCard.fullDescription = card["fullDescription"] as String?
            newCard.title = card["title"] as String?
            cardsDao.insert(newCard)
            updateTips(map["Tips"] as HashMap<String, Any>?, newCard.cardNameID)
        }
    }

    private fun updateCourses(map: HashMap<String?, Any?>) {
        val keys = keyStoreCourses()
        val courses = map["Courses"] as HashMap<String, Any>?
        for ((_, value) in courses!!) {
            val course = value as HashMap<String, Any>
            if (!keys.contains(course["courseNameID"])) {
                val newCourse = Course()
                newCourse.courseNameID = (course["courseNameID"] as String?)!!
                newCourse.isActive = 0
                newCourse.description = course["description"] as String?
                newCourse.fullDescription = course["fullDescription"] as String?
                newCourse.title = course["title"] as String?
                newCourse.progressBar = 0
                coursesDao.insert(newCourse)
                updateCards(map, newCourse.courseNameID)
            }
        }
    }

    fun updateDatabase() {
        val mDb = FirebaseDatabase.getInstance()
        val mRef = mDb.reference
        mRef.child("content").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val indicator: GenericTypeIndicator<HashMap<String?, Any?>?> = object : GenericTypeIndicator<HashMap<String?, Any?>?>() {}
                val map = dataSnapshot.getValue(indicator)!!
                val verFirebase = map["version"] as Long
                val verLocalbase = contentVersion
                if (verFirebase > verLocalbase) {
                    updateCourses(map)
                    val version = db.versionDao().ver
                    version.versionContent = verFirebase
                    db.versionDao().update(version)
                }
                updateUserProfile()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun updateUserProgress(map: HashMap<String?, Any?>?) {
        val courseProgress = map?.get("Courses") as HashMap<String, Objects>?
        val cardProgress = map?.get("Cards") as HashMap<*, *>?
        val tip = map?.get("Tips") as HashMap<String, Objects>?
        for ((key, value) in courseProgress!!) {
            val course = db.courseDao().getByCourseID(key)
            val temp = value as HashMap<String, Any>
            val progress = temp["progress"] as Long
            course.progressBar = progress.toInt()
            if (progress >= 100) course.setStatus(Course.Status.FINISHED)
            db.courseDao().update(course)
        }
        for ((key, value) in cardProgress!!) {
            val card = db.cardsDao().getByCardID(key as String?)
            val temp = value as HashMap<String, Any>
            val status = temp["status"] as Long
            card.isActive = status.toInt()
            db.cardsDao().update(card)
        }
        for ((key, value) in tip!!) {
            val ecoSoviet = db.sovietsDao().getByTipID(key.toInt().toLong())
            val temp = value as HashMap<String, Any>
            val status = temp["status"] as Long
            ecoSoviet.isFavorite = status.toInt()
            db.sovietsDao().update(ecoSoviet)
        }
    }

    fun updateFirebaseProgress(group: String?, child: String?, key: String?, value: Int) {
        val mDb = FirebaseDatabase.getInstance()
        val mRef = mDb.reference
        val mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        //        if (child != null && group != null)
//            mRef.child(user.getUid()).child("progress").child(group).child(child).child(key).setValue(value);
//        else
//            mRef.child(user.getUid()).child("progress").child(key).setValue(value);
    }

    fun updateUserProfile() {
        val mDb = FirebaseDatabase.getInstance()
        val mRef = mDb.reference
        val mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        mRef.setPriority(1)
        mRef.child(user!!.uid).child("progress").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val indicator: GenericTypeIndicator<HashMap<String?, Any?>?> = object : GenericTypeIndicator<HashMap<String?, Any?>?>() {}
                val map = dataSnapshot.getValue(indicator)
                if (map == null) {
                    updateFirebaseProgress(null, null, "version", 1)
                } else {
                    val ver = map["version"] as Long
                    val localVer = progressVersion
                    if (ver > localVer) {
                        updateUserProgress(map)
                        val version = db.versionDao().ver
                        version.versionUserBar = ver.toInt()
                        db.versionDao().update(version)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    /**
     * Percentage of Eco
     */
    fun calculateUserProgress(settingsManager: SettingsManager?) {

        ObservableFromCallable(Callable { db.cardsDao().all })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    val percentageOpened =
                            (it.filter { ecoCard -> ecoCard.status == EcoCard.Status.WATCHED }.size).toFloat() / it.size.toFloat()
                    Observable.just(percentageOpened)
                }
                .subscribe {
                    settingsManager?.updateProgress(it.times(100).toInt())
                    println("The percentage: $it")
                }
    }
}