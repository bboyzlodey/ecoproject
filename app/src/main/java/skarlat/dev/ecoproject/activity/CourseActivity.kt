package skarlat.dev.ecoproject.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import skarlat.dev.ecoproject.ConnectionCardDecorator
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.adapter.CardsViewAdapter
import skarlat.dev.ecoproject.databinding.ActivityCourseCardBinding
import skarlat.dev.ecoproject.includes.database.DatabaseHelper
import skarlat.dev.ecoproject.includes.dataclass.Course
import skarlat.dev.ecoproject.includes.dataclass.EcoCard
import skarlat.dev.ecoproject.setImageFromAssets

class CourseActivity : AppCompatActivity() {
    private var progressOfCourse = 0
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


//        if ( progress > 0  && progress < 100)
//            startCourse.setText("Продолжить обучение");
//        else if ( progress >= 100){
//            startCourse.setVisibility(View.GONE);
//        }
//        else
//            startCourse.setText("Начать обучение");
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // TODO("LOGIC FOR CLOSE CARD")
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

    override fun onBackPressed() {
        super.onBackPressed()
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
            upDateCurrentCourse()
            startActivityForResult(intent, REQUST)
        }
        return null
    }

    /**
     * Progress бар для базы данных
     */
    private fun upDateCurrentCourse() {
        var res = 100.00 / ecoCards!!.size.toDouble()
        res = Math.ceil(res)
        progressOfCourse += res.toInt()
        if (progressOfCourse >= 100) {
            currentCourse!!.upDate(progressOfCourse, Course.Status.FINISHED)
        } else {
            currentCourse!!.upDate(progressOfCourse, Course.Status.CURRENT)
        }
        db.updateFirebaseProgress("Courses", currentCourse!!.name, "progress", progressOfCourse)
    }

    private fun updateData() {
        val tagView = intent.extras
        currentCourse = db.getCourseByName(tagView!!["OPEN_COURSE"].toString())
        courseName = currentCourse?.getName()
        ecoCards = db.getAllCardsByCourseNameID(courseName)
        progressOfCourse = currentCourse?.getProgressBar()!!
        progressOfCourse = db.getCourseByName(courseName).getProgressBar()
        val adapterList = arrayListOf<Any>(currentCourse!!)
        adapterList.addAll(ecoCards!!)
        adapter!!.submitList(adapterList)
        db.upDateIsCurrentCourse(courseName)
    }
}