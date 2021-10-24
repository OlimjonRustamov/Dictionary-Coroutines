package uz.olimjon_rustamov.dictionarycoroutines.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import uz.olimjon_rustamov.dictionarycoroutines.Cashe.Cashe
import uz.olimjon_rustamov.dictionarycoroutines.R
import uz.olimjon_rustamov.dictionarycoroutines.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), IOnBackPressed {

    private lateinit var vb: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentHomeBinding.inflate(layoutInflater)

        val status = Cashe.instance!!.getStatus()
        if (status == "first") {
            findNavController().navigate(R.id.entranceFragment)
        }
        backPressed()


        return vb.root
    }

    private fun backPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}