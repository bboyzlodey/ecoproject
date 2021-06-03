package skarlat.dev.ecoproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow

class SingleLiveData<T>: MutableLiveData<T>() {
    override fun setValue(value: T) {
        super.setValue(value)
        if (value != null) {
            super.setValue(null)
        }
    }
}