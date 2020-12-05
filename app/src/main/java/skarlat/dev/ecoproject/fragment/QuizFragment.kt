package skarlat.dev.ecoproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_page.*
import skarlat.dev.ecoproject.R


/*
Class of Fragments for QuizActivity
 */
class QuizFragment : Fragment() {

    companion object {
        const val ARG_PAGE = "ARG_PAGE"

        // Method for creating new instances of the fragment

        fun getinstance(page: Int): QuizFragment {
            val fragment = QuizFragment()
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            fragment.arguments = args
            return fragment

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = requireArguments().getInt(ARG_PAGE) + 1

        textViewFragment.text = "Fragment #$position"


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewfragment = inflater.inflate(R.layout.fragment_page, container, false)
        var position = requireArguments().getInt(ARG_PAGE)

        val textView = viewfragment.findViewById<TextView>(R.id.textViewFragment)
        textView.setText("Fragment #${++position}")

        return viewfragment


    }

}

