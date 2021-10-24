package uz.olimjon_rustamov.dictionarycoroutines.entrance.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MyPagerAdapter(var titles: ArrayList<String>, var infos: ArrayList<String>, frm:FragmentManager) :
    FragmentStatePagerAdapter(
        frm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    override fun getCount(): Int = titles.size

    override fun getItem(position: Int): Fragment {
        return ViewPagerFragment.newInstance(titles[position], infos[position])
    }


}