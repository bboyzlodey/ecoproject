package skarlat.dev.ecoproject.fragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.ContentNavigationDirections
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
        cards = EcoTipsApp.getDatabase().cardsDao().all
        binding.pressBackFromFragment.setOnClickListener {
            requireActivity().onBackPressed()
        }
        // @TODO: Заменить заполнение листа с ипользованием БД
        val adapter = CategoryAdapter(context, cards)
        val itemDecoration = DividerItemDecoration(context, RecyclerView.HORIZONTAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_category))
        binding.cardsByCategory.addItemDecoration(itemDecoration)
        binding.cardsByCategory.adapter = adapter
        binding.logoutButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                EcoTipsApp.appComponent.getAuthManager().logout()
                findNavController().navigate(ContentNavigationDirections.globalActionToMain())
                requireActivity().finishAfterTransition()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.openSettingsButton.setOnClickListener {
//            fragmentManager!!.beginTransaction().add(R.id.home_layout, ProfileSettingsFragment.newInstance()).commit()
        }
        initUI()
    }

    override fun onStart() {
        super.onStart()
        val appCache = EcoTipsApp.appComponent.getAppCache()
        FirebaseAPI.getUsersCount { appCache.setCount(it.toInt()) }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                appCache.userProgressFlow.collectLatest {
                    binding.percentProgress.text = "${it}%"
                    binding.totalProgressBar.progress = it
                }
            }
            launch {
                appCache.userFlow.collectLatest {
                    binding.userName.text = it.name
                }
            }
            launch {
                appCache.userCountFlow.collectLatest {
                    binding.countUsers.text = getString(R.string.count_users_format, it)
                }
            }
        }
    }

    private fun showUserAvatar() {
        // TODO Use glide
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