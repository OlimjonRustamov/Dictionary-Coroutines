package uz.olimjon_rustamov.dictionarycoroutines.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import uz.olimjon_rustamov.dictionarycoroutines.MainActivity
import uz.olimjon_rustamov.dictionarycoroutines.R
import uz.olimjon_rustamov.dictionarycoroutines.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var vb:FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentSearchBinding.inflate(layoutInflater)



        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbar_main?.title="Search"
    }
}