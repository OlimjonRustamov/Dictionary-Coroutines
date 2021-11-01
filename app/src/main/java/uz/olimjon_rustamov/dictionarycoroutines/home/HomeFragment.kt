package uz.olimjon_rustamov.dictionarycoroutines.home

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import uz.olimjon_rustamov.dictionarycoroutines.Cashe.Cashe
import uz.olimjon_rustamov.dictionarycoroutines.MainActivity
import uz.olimjon_rustamov.dictionarycoroutines.R
import uz.olimjon_rustamov.dictionarycoroutines.databinding.FragmentHomeBinding
import uz.olimjon_rustamov.dictionarycoroutines.home.adapters.LastSearchedAdapter
import uz.olimjon_rustamov.dictionarycoroutines.home.models.LastSearched
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.viewmodel.SingleNetworkCallViewModel
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseBuilder
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseHelperImpl
import uz.olimjon_rustamov.dictionarycoroutines.utils.Status
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var vb: FragmentHomeBinding
    private lateinit var lastSearchedList: ArrayList<LastSearched>
    private lateinit var savedList: ArrayList<LastSearched>
    private lateinit var lastSearchedAdapter: LastSearchedAdapter
    private lateinit var savedAdapter: LastSearchedAdapter
    private lateinit var db:DatabaseHelperImpl

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
        setClicks()
        setDB()
        setlastSearchedAdapter()

        return vb.root
    }

    private fun setClicks() {
        vb.searchEtRoot.setOnClickListener {
            val et = vb.searchEt
            et.requestFocus()
            showKeyBoard(et)
        }
        vb.searchEt.addTextChangedListener{
            if (it?.length!! >= 1) {
                val word=it.toString()
                vb.searchEtIconMicrophone.setImageResource(R.drawable.ic_send)
                vb.searchEtIconMicrophone.setOnClickListener {
                    clickGetWord(word)
                }
                vb.searchEtIconSearch.visibility = View.GONE
            } else {
                vb.searchEtIconMicrophone.setImageResource(R.drawable.microphone)
                vb.searchEtIconMicrophone.setOnClickListener { clickMicrophone() }
                vb.searchEtIconSearch.visibility = View.VISIBLE
            }
        }
        vb.searchEtIconCopy.setOnClickListener { Snackbar.make(vb.searchEtIconCopy, "Copy", Snackbar.LENGTH_LONG).show() }
        vb.lastSearchedTvs.setOnClickListener { findNavController().navigate(R.id.historyFragment) }
        vb.lastSearchedTvs2.setOnClickListener { findNavController().navigate(R.id.savedFragment) }
        vb.bayrakSampleDate.text = getTime()
    }

    private fun backPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

    private fun showKeyBoard(et: EditText) {
        val keyboard =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        keyboard!!.showSoftInput(et, 0)

    }
    private fun closeKeyBoard(et: EditText) {
        val keyboard =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        keyboard!!.hideSoftInputFromWindow(et.windowToken, 0)

    }

    private fun setlastSearchedAdapter() {
        lastSearchedAdapter = LastSearchedAdapter(lastSearchedList, vb.root.context, "homepage")
        savedAdapter = LastSearchedAdapter(savedList, vb.root.context, "homepage")
        vb.lastSearchedRv.adapter = lastSearchedAdapter
        vb.savedHomeRv.adapter = savedAdapter
    }

    private fun setDB() {
        db = DatabaseHelperImpl(DatabaseBuilder.getInstance(vb.root.context))
//        for (i in 0..10) {
//            db.insertLastSearched(LastSearched("Muazzam Dunyo ishlari tushunarsiz", "Ko'p buyuk insonlar ham oddiy odam bo'lishgan. Shuni unutmaylik", true))
//        }
        lastSearchedList = db.getLastSearched() as ArrayList<LastSearched>
        savedList = db.getSavedLastSearched() as ArrayList<LastSearched>
    }
    private fun getTime(): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val time = sdf.format(Date())
        return time
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbar_main?.title="Dictionary Coroutines"
    }

    private fun clickMicrophone() {
        Snackbar.make(vb.searchEtIconMicrophone, "Microphone", Snackbar.LENGTH_LONG).show()
    }

    private fun clickGetWord(word: String) {
        Cashe.instance!!.setWord(word)
        findNavController().navigate(R.id.searchFragment)
    }

}