
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import skarlat.dev.ecoproject.fragment.QuizFragment
/*
Adapter for QuizActivity
 */
class QuizAdapter(activity: AppCompatActivity, val itemsCount: Int)
    : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return QuizFragment.getinstance(position)
    }
}