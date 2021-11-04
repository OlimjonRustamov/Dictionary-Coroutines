package uz.olimjon_rustamov.dictionarycoroutines.search.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.olimjon_rustamov.dictionarycoroutines.R
import uz.olimjon_rustamov.dictionarycoroutines.databinding.FragmentOriginBinding

class OriginFragment : Fragment() {

    private var origin: String? = null

    private lateinit var vb:FragmentOriginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            origin = it.getString("origin")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vb = FragmentOriginBinding.inflate(layoutInflater)

        vb.originTv.text = origin

        return vb.root
    }

    companion object {
        @JvmStatic fun newInstance(origin: String) =
                OriginFragment().apply {
                    arguments = Bundle().apply {
                        putString("origin", origin)
                    }
                }
    }
}