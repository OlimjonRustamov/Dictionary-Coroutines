package uz.olimjon_rustamov.dictionarycoroutines.search.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.models.WordResponseItem
import uz.olimjon_rustamov.dictionarycoroutines.search.ui.MeaningFragment
import uz.olimjon_rustamov.dictionarycoroutines.search.ui.OriginFragment

class SearchPagerAdapter(var word:WordResponseItem, var frm:FragmentManager) :
    FragmentStatePagerAdapter(
        frm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return MeaningFragment.newInstance(word)
        } else return OriginFragment.newInstance(word.origin)
    }


}