package skarlat.dev.ecoproject.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import skarlat.dev.ecoproject.ConnectionCardDecorator
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.adapter.CardsViewAdapter
import skarlat.dev.ecoproject.databinding.ActivityCourseCardBinding
import skarlat.dev.ecoproject.includes.database.DatabaseHelper
import skarlat.dev.ecoproject.includes.dataclass.Course
import skarlat.dev.ecoproject.includes.dataclass.EcoCard
import skarlat.dev.ecoproject.setImageFromAssets

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
        adapter = CardsViewAdapter(this@CourseActivity) { view: View -> openCard(view) }
        updateData()
        binding!!.recycleCards.adapter = adapter
        binding!!.courseAvatar.setImageFromAssets(assets, currentCourse!!.pathBarImage())
        binding!!.recycleCards.addItemDecoration(ConnectionCardDecorator())

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
        if (currentCard.status === EcoCard.Status.WATCHED) {
            startActivityForResult(intent, REQUST)
        } else {
            for (i in ecoCards!!.indices) {
                if (ecoCards!![i].name === currentCard.name && i + 1 < ecoCards!!.size) {
                    ecoCards!![i + 1].upDate(EcoCard.Status.OPENED)
                    db.updateFirebaseProgress("Cards", ecoCards!![i + 1].name, "status", 1)
                    break
                }
            }
            currentCard.upDate(EcoCard.Status.WATCHED)
            db.updateFirebaseProgress("Cards", currentCard.name, "status", 2)
            startActivityForResult(intent, REQUST)
        }
        return null
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
        db.upDateIsCurrentCourse(courseName)
    }
}