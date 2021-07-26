package skarlat.dev.ecoproject.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import skarlat.dev.ecoproject.*
import skarlat.dev.ecoproject.adapter.CardsViewAdapter
import skarlat.dev.ecoproject.databinding.ActivityCourseCardBinding
import skarlat.dev.ecoproject.includes.database.DatabaseHelper
import skarlat.dev.ecoproject.includes.dataclass.Course
import skarlat.dev.ecoproject.includes.dataclass.Course.Status.FINISHED
import skarlat.dev.ecoproject.includes.dataclass.EcoCard

class CourseActivity : AppCompatActivity() {
    private var courseName: String? = null
    private val db = DatabaseHelper()
    private var ecoCards: List<EcoCard>? = null
    private var currentCourse: Course? = null
    private val REQUST = 1
    private var binding: ActivityCourseCardBinding? = null
    private var adapter: CardsViewAdapter? = null

    // TODO Refactor it
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseCardBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        adapter = CardsViewAdapter { view: View -> openCard(view) }
        updateData()
        binding!!.recycleCards.adapter = adapter
        binding!!.courseAvatar.setImageFromAssets(assets, currentCourse!!.pathBarImage())
        binding!!.recycleCards.addItemDecoration(ConnectionCardDecorator())
        if (currentCourse?.isActive == Course.Status.CLOSED.ordinal) {
            markCourseAsCurrent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Const.CARD_OPENED) {
            if (resultCode == Const.CARD_ACTIVITY_OK) {
                updateData()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun onBackBtn(view: View?) {
        onBackPressed()
    }

    /**
     * View карточки обновляет базу данных при переходе
     *
     * @param view
     */
    fun openCard(view: View): Void? {
        val currentCard = view.tag as EcoCard
        val intent = Intent(this, CardActivity::class.java)
        intent.putExtra(currentCard.javaClass.simpleName, currentCard)
        val nextCard = ecoCards?.itemAfter(currentCard)

        if (currentCard.status === EcoCard.Status.WATCHED && nextCard?.status != EcoCard.Status.CLOSED) {
            startActivityForResult(intent, REQUST)
        } else {
            currentCard.setStatus(EcoCard.Status.WATCHED)
            nextCard?.setStatus(EcoCard.Status.OPENED)

            addDisposable(
                    EcoTipsApp.getDatabase().cardsDao().update(currentCard)
                            .IOSchedulers
                            .subscribe { })
            startActivityForResult(intent, REQUST)
        }
        if (nextCard == null) {
            markCourseAsFinished()
        } else {
            addDisposable(
                    EcoTipsApp.getDatabase().cardsDao().update(nextCard)
                            .IOSchedulers
                            .subscribe { })
        }
        return null
    }

    private fun markCourseAsFinished() {
        currentCourse?.let {
            it.isActive = FINISHED.ordinal
            val disposable = EcoTipsApp.getDatabase().courseDao().update(it)
        }
    }

    private fun markCourseAsCurrent() {
        currentCourse?.let {
            it.isActive = Course.Status.CURRENT.ordinal
            val disposable = EcoTipsApp.getDatabase().courseDao().update(it)
        }
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData() {
        val tagView = intent.extras
        currentCourse = db.getCourseByName(tagView!!["OPEN_COURSE"].toString())
        courseName = currentCourse?.getName()
        ecoCards = db.getAllCardsByCourseNameID(courseName)
        currentCourse?.leftCards = ecoCards?.count { it.status != EcoCard.Status.WATCHED } ?: 0
        currentCourse?.countCards = ecoCards?.size ?: 0
        val adapterList = arrayListOf<Any>(currentCourse!!)
        adapterList.addAll(ecoCards!!)
        adapter!!.submitList(adapterList)
    }
}