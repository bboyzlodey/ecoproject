package skarlat.dev.ecoproject.fragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.adapter.CategoryAdapter
import skarlat.dev.ecoproject.databinding.FragmentUserBinding
import skarlat.dev.ecoproject.includes.dataclass.EcoCard
import skarlat.dev.ecoproject.network.FirebaseAPI
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class UserFragment : BaseFragment<FragmentUserBinding>() {
    private var cards: List<EcoCard>? = null

    var imageView: ImageView? = null

    override fun inflateBinding(): ViewBinding {
        return FragmentUserBinding.inflate(layoutInflater)
    }

    private fun initUI() {
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
        initUI()
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
        var userPhotoUrl: String? = null

    }
}