package skarlat.dev.ecoproject.includes.database

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromCallable
import io.reactivex.rxjava3.schedulers.Schedulers
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.addDisposable
import skarlat.dev.ecoproject.core.AppCache
import skarlat.dev.ecoproject.core.SettingsManager
import skarlat.dev.ecoproject.includes.dataclass.Course
import skarlat.dev.ecoproject.includes.dataclass.EcoCard
import skarlat.dev.ecoproject.includes.dataclass.EcoSoviet
import java.util.concurrent.Callable

/**
 * @Class - помошник в обработке данных для добавления\взятия данных
 */
class DatabaseHelper {
    private val db = EcoTipsApp.getDatabase()
    private val coursesDao = db.courseDao()
    private val cardsDao = db.cardsDao()
    private val sovietsDao = db.sovietsDao()
    private val appCache: AppCache by lazy { EcoTipsApp.appComponent.getAppCache() }

    /**
     * функции получения объектов Course из базы
     *
     * @return Courses
     */
    val allCourses: List<Course>
        get() = coursesDao.all


    fun getCourseByName(courseName: String?): Course {
        return coursesDao.getCourse(courseName)
    }

    /**
     * Функции получения объектов карточек из базы
     *
     * @return EcoCards
     */

    fun getAllCardsByCourseNameID(nameID: String?): List<EcoCard> {
        return cardsDao.getAllByCurs(nameID)
    }

    /**
     * Функции получения объектов советов
     *
     * @param cardName
     * @return EcoSiviets
     */
    fun getAllCards(cardName: String?): List<EcoSoviet> {
        return sovietsDao.getAllByCardNameID(cardName)
    }

    /**
     * Обновляет текущий курс для главного меню курсов
     *
     * @param courseName
     */
    fun updateCourse(courseName: String?) {
        val next = coursesDao.getCourse(courseName)
        val last = coursesDao.currentCourse
        if (last != null && last.isActive == 1) {
        }
        next.isActive = 1
        if (last != null) coursesDao.update(last)
        coursesDao.update(next)
    }

    /**
     * Percentage of Eco
     */
    fun calculateUserProgress(settingsManager: SettingsManager?) {

        val disposable = ObservableFromCallable(Callable { db.cardsDao().all })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    val percentageOpened =
                            (it.filter { ecoCard -> ecoCard.status == EcoCard.Status.WATCHED }.size).toFloat() / it.size.toFloat()
                    Observable.just(percentageOpened)
                }
                .subscribe {
                    appCache.setUserProgress(it.times(100).toInt())
                    settingsManager?.updateProgress(it.times(100).toInt())
                    println("The percentage: $it")
                }
        addDisposable(disposable)
    }
}