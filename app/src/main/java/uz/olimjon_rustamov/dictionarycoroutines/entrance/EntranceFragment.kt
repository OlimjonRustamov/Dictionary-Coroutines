package uz.olimjon_rustamov.dictionarycoroutines.entrance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.olimjon_rustamov.dictionarycoroutines.Cashe.Cashe
import uz.olimjon_rustamov.dictionarycoroutines.MainActivity
import uz.olimjon_rustamov.dictionarycoroutines.R
import uz.olimjon_rustamov.dictionarycoroutines.databinding.ActivityMainBinding
import uz.olimjon_rustamov.dictionarycoroutines.databinding.FragmentEntranceBinding

class EntranceFragment : Fragment() {

    private lateinit var binding:FragmentEntranceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val status = Cashe.instance!!.getStatus()
        if (status == "entered") {
            findNavController().navigate(R.id.homeFragment)
        }


        binding = FragmentEntranceBinding.inflate(layoutInflater)
        setUI()

        return binding.root
    }
    private fun setUI() {
        binding.skipBtn.setOnClickListener {
            Cashe.instance!!.setStatus()
            findNavController().navigate(R.id.homeFragment)
            (activity as MainActivity).setVisiblities(true)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).setVisiblities(false)
    }


}