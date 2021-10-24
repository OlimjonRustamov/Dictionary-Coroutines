package uz.olimjon_rustamov.dictionarycoroutines.entrance

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.tab_item.view.*
import uz.olimjon_rustamov.dictionarycoroutines.Cashe.Cashe
import uz.olimjon_rustamov.dictionarycoroutines.MainActivity
import uz.olimjon_rustamov.dictionarycoroutines.R
import uz.olimjon_rustamov.dictionarycoroutines.databinding.FragmentEntranceBinding
import uz.olimjon_rustamov.dictionarycoroutines.entrance.viewpager.MyPagerAdapter

class EntranceFragment : Fragment() {

    private lateinit var vb:FragmentEntranceBinding
    private lateinit var titles: ArrayList<String>
    private lateinit var infos: ArrayList<String>
    private lateinit var myViewPagerAdapter:MyPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        vb = FragmentEntranceBinding.inflate(layoutInflater)
        setUI()
        loadData()
        return vb.root
    }
    private fun setUI() {
        vb.skipBtn.setOnClickListener {
            skip()
        }
    }

    private fun skip() {
        Cashe.instance!!.setStatus()
        findNavController().navigate(R.id.homeFragment)
        (activity as MainActivity).setVisiblities(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).setVisiblities(false)
    }

    private fun loadData() {
        titles = ArrayList()
        titles.add("Katta hajmli\n ma'lumotlar bazasi")
        titles.add("Katta hajmli\n ma'lumotlar bazasi")
        titles.add("Katta hajmli\n ma'lumotlar bazasi")
        infos = ArrayList()
        infos.add("Siz bu ilova yordamida kamdan-kam\n ishlatiladigan so'zlarning ham\n tarjimasini osongina topishingiz\n mumkin")
        infos.add("Siz bu ilova yordamida kamdan-kam\n ishlatiladigan so'zlarning ham\n tarjimasini osongina topishingiz\n mumkin")
        infos.add("Siz bu ilova yordamida kamdan-kam\n ishlatiladigan so'zlarning ham\n tarjimasini osongina topishingiz\n mumkin")

        myViewPagerAdapter = MyPagerAdapter(titles, infos, childFragmentManager)

        vb.entranceViewPager.adapter=myViewPagerAdapter
        vb.entranceTablayout.setupWithViewPager(vb.entranceViewPager)

        setTabs()
    }

    private fun setTabs() {
        for (i in 0 until vb.entranceTablayout.tabCount) {
            val tabBind =
                LayoutInflater.from(vb.root.context).inflate(R.layout.tab_item, null, false)
            val tab = vb.entranceTablayout.getTabAt(i)
            tab?.customView = tabBind

            if (i == 0) {
                tabBind.tab_view.setBackgroundResource(R.drawable.tab_selected)
            } else {
                tabBind.tab_view.setBackgroundResource(R.drawable.tab_unselected)
            }
        }

        vb.entranceTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 2) {
                    vb.skipBtnBottom.visibility = View.VISIBLE
                    vb.skipBtnBottom.setOnClickListener {
                        skip()
                    }
                } else {
                    vb.skipBtnBottom.visibility = View.GONE
                }
                val view = tab?.customView
                view?.tab_view?.setBackgroundResource(R.drawable.tab_selected)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val view = tab?.customView
                view?.tab_view?.setBackgroundResource(R.drawable.tab_unselected)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }


}