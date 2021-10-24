package uz.olimjon_rustamov.dictionarycoroutines.entrance.viewpager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.olimjon_rustamov.dictionarycoroutines.R
import uz.olimjon_rustamov.dictionarycoroutines.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment() {
    private lateinit var title:String
    private lateinit var info:String

    private lateinit var vb: FragmentViewPagerBinding
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = arguments!!.getString("title", "")
            info = arguments!!.getString("info", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentViewPagerBinding.inflate(layoutInflater)

        vb.viewPagerItemTitle.text = title
        vb.viewPagerItemInfo.text = info
        return vb.root
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, info: String) =
            ViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("info", info)
                }
            }
    }
}