package skarlat.dev.ecoproject.activity

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import skarlat.dev.ecoproject.adapter.CardsViewAdapter
import skarlat.dev.ecoproject.includes.dataclass.EcoCard

class CourseViewModel : ViewModel() {

    val adapter: Flow<RecyclerView.Adapter<RecyclerView.ViewHolder>> get() = _adapter
    private val _adapter = MutableStateFlow(CardsViewAdapter {})

    fun onOpenCardClicked(card: EcoCard) {

    }
}