package skarlat.dev.ecoproject.fragment

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.User
import skarlat.dev.ecoproject.adapter.CategoryAdapter
import skarlat.dev.ecoproject.core.SettingsManager
import skarlat.dev.ecoproject.databinding.FragmentUserBinding
import skarlat.dev.ecoproject.includes.dataclass.EcoCard
import skarlat.dev.ecoproject.network.FirebaseAPI
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
    private var cards: List<EcoCard>? = null

    var imageView: ImageView? = null
    private var binding: FragmentUserBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(layoutInflater)
        val recyclerView = binding!!.cardsByCategory
        val textView = binding!!.userName
        imageView = binding!!.profileImage
//        textView.text = User.currentUser?.name
        val runnable = Runnable { showUserAvatar() }
        cards = EcoTipsApp.getDatabase().cardsDao().all
        val fab = binding!!.pressBackFromFragment
        fab.setOnClickListener {
//            Objects.requireNonNull(activity)?.onBackPressed()
//            onDestroy()
        }
        // @TODO: Заменить заполнение листа с ипользованием БД
        val adapter = CategoryAdapter(context, cards)
        val itemDecoration = DividerItemDecoration(context, RecyclerView.HORIZONTAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_category))
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.adapter = adapter
        return binding!!.root
    }

    private fun closeFragment() {
//        fragmentManager!!.beginTransaction().remove(this).detach(this).commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.settingsButton.setOnClickListener {
//            assert(fragmentManager != null)
//            fragmentManager!!.beginTransaction().add(R.id.home_layout, ProfileSettingsFragment.newInstance()).commit()
        }
//        val settingsManager = SettingsManager(context!!.getSharedPreferences(Const.ECO_TIPS_PREFERENCES, Context.MODE_PRIVATE))
//        binding?.percentProgress?.text = "${settingsManager.userProgress}%"
//        binding?.totalProgressBar?.progress = settingsManager.userProgress
    }

    override fun onStart() {
        super.onStart()
        FirebaseAPI
                .getUsersCount { count ->
                    binding?.countUsers?.text = getString(R.string.count_users_format, count)
                }
    }

    private fun showUserAvatar() {
        try {
            val photoURL = URL(userPhotoUrl)
            val urlConnection = photoURL.openConnection()
            val inputStream = urlConnection.getInputStream()
            imageView!!.setImageDrawable(BitmapDrawable(resources, inputStream))
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        var userFragment: UserFragment? = null

//        @JvmField
        var userPhotoUrl: String? = null

//        @JvmField
        var userName: String? = null

        // TODO: Rename and change types and number of parameters
//        @JvmStatic
        fun newInstance(page: Int): UserFragment {
            val args = Bundle()
            args.putInt("UserFragment", page)
            val fragment = UserFragment()
            fragment.arguments = args
            userFragment = fragment
            return fragment
        }
    }
}