package uz.olimjon_rustamov.dictionarycoroutines.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import uz.olimjon_rustamov.dictionarycoroutines.Cashe.Cashe
import uz.olimjon_rustamov.dictionarycoroutines.R
import uz.olimjon_rustamov.dictionarycoroutines.databinding.FragmentHomeBinding
import uz.olimjon_rustamov.dictionarycoroutines.home.adapters.LastSearchedAdapter
import uz.olimjon_rustamov.dictionarycoroutines.home.models.LastSearched
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseBuilder
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseHelperImpl


class HomeFragment : Fragment() {

    private lateinit var vb: FragmentHomeBinding
    private lateinit var lastSearchedList: ArrayList<LastSearched>
    private lateinit var lastSearchedAdapter: LastSearchedAdapter
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
                vb.searchEtIconMicrophone.setImageResource(R.drawable.ic_send)
                vb.searchEtIconSearch.visibility = View.GONE
            } else {
                vb.searchEtIconMicrophone.setImageResource(R.drawable.microphone)
                vb.searchEtIconSearch.visibility = View.VISIBLE
            }
        }
        vb.searchEtIconMicrophone.setOnClickListener {
            Snackbar.make(vb.searchEtIconMicrophone, "Microphone", Snackbar.LENGTH_LONG).show()
        }
        vb.searchEtIconCopy.setOnClickListener {
            Snackbar.make(vb.searchEtIconCopy, "Copy", Snackbar.LENGTH_LONG).show()
        }
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

    private fun setlastSearchedAdapter() {
        lastSearchedAdapter = LastSearchedAdapter(lastSearchedList, vb.root.context, false)
        vb.lastSearchedRv.adapter = lastSearchedAdapter
        vb.lastSearchedRv2.adapter = lastSearchedAdapter
    }

    private fun setDB() {
        db = DatabaseHelperImpl(DatabaseBuilder.getInstance(vb.root.context))
//        for (i in 0..10) {
//            db.insertLastSearched(LastSearched("Muazzam", "Ko'p buyuk", true))
//        }
        lastSearchedList = db.getLastSearched() as ArrayList<LastSearched>
    }
}