package skarlat.dev.ecoproject.activity

import androidx.lifecycle.ViewModel
import skarlat.dev.ecoproject.EcoTipsApp

class HomeActivityViewModel: ViewModel() {

    private fun getUser() {
        EcoTipsApp.auth.currentUser
    }
}