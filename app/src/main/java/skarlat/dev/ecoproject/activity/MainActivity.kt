package skarlat.dev.ecoproject.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.databinding.ActivityMainBinding
import skarlat.dev.ecoproject.di.AppCacheModule
import skarlat.dev.ecoproject.di.DaggerAppComponent
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

//    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("StaticFieldLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_launch)
    }
}