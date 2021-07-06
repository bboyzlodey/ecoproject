package skarlat.dev.ecoproject.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.di.delegates.provideComponent
import timber.log.Timber

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    private val component by provideComponent()
    val authManager = component!!.authManager

    @SuppressLint("RestrictedApi")
    override fun onStart() {
        super.onStart()
        findNavController(R.id.nav_host)
                .addOnDestinationChangedListener { controller, destination, arguments ->
                    Timber.d("Destination from: ${controller.previousBackStackEntry?.destination?.label} to: ${controller?.currentDestination?.label}. Stacksize : ${controller?.backStack?.size}")
                }
    }
}