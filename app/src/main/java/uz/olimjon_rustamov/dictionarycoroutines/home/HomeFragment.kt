package uz.olimjon_rustamov.dictionarycoroutines.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
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
import uz.olimjon_rustamov.dictionarycoroutines.search.adapter.SearchPagerAdapter
import uz.olimjon_rustamov.dictionarycoroutines.utils.Status
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var vb: FragmentHomeBinding
    private lateinit var lastSearchedList: ArrayList<LastSearched>
    private lateinit var savedList: ArrayList<LastSearched>
    private lateinit var lastSearchedAdapter: LastSearchedAdapter
    private lateinit var savedAdapter: LastSearchedAdapter
    private lateinit var db: DatabaseHelperImpl

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
        setDayWordTools()
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
        vb.textBayrak.setOnClickListener {
            Cashe.instance!!.setWord("education")
            findNavController().navigate(R.id.searchFragment)
        }
        vb.searchEt.addTextChangedListener {
            if (it?.length!! >= 1) {
                val word = it.toString()
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
        vb.searchEtIconCopy.setOnClickListener {
            copyToClipBoard(vb.searchEt.text.toString())
        }
        vb.lastSearchedTvs.setOnClickListener { findNavController().navigate(R.id.historyFragment) }
        vb.lastSearchedTvs2.setOnClickListener { findNavController().navigate(R.id.savedFragment) }
        vb.bayrakSampleDate.text = getTime()
    }

    private fun copyToClipBoard(word: String) {
        val clipboard: ClipboardManager =
            activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("word", word))
        Toast.makeText(vb.root.context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
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
        (activity as MainActivity).toolbar_main?.title = "Dictionary Coroutines"
    }

    private fun clickMicrophone() {
        Toast.makeText(vb.root.context, "Microphone", Toast.LENGTH_SHORT).show()
    }

    private fun clickGetWord(word: String) {
        Cashe.instance!!.setWord(word)
        closeKeyBoard(vb.searchEt)
        findNavController().navigate(R.id.searchFragment)
    }

    private fun setDayWordTools() {
        vb.apply {
            copyTool.setOnClickListener {
                copyToClipBoard("education")
            }
            speakerTool.setOnClickListener {
                Cashe.instance!!.setWord("education")
                findNavController().navigate(R.id.searchFragment)
            }
            savedTool.setOnClickListener {
                savedToolFun()
            }
            shareTool.setOnClickListener {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "education"
                )
                startActivity(Intent.createChooser(shareIntent, "Share to"))
            }
        }
    }

    private fun savedToolFun() {
            val viewModel = ViewModelProvider(this).get(SingleNetworkCallViewModel::class.java)
            db= DatabaseHelperImpl(DatabaseBuilder.getInstance(vb.root.context))
            viewModel.fetchWord("education")
            viewModel.getWordResponse().observe(viewLifecycleOwner, {
                when (it.status) {
                    Status.SUCCESS -> {
                        vb.savedProgress.visibility = View.GONE
                        vb.savedToolIv.visibility = View.VISIBLE
                        it.data?.let {
                            val lastSearched = LastSearched(it.word, it.meanings[0].definitions[0].definition, true)
                            try {
                                if (db.getLastSearched().reversed()[0].title != it.word) {
                                    db.insertLastSearched(lastSearched)
                                } else {
                                    val t = db.getLastSearched().reversed()[0]
                                    t.isSaved = true
                                    db.updateLastSearched(t)
                                }
                            } catch (e: Exception) {
                                db.insertLastSearched(lastSearched)
                            }
                            Toast.makeText(vb.root.context, "This word is saved", Toast.LENGTH_SHORT).show()
                        }
//                    recyclerView.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        vb.savedToolIv.visibility = View.GONE
                        vb.savedProgress.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        vb.savedProgress.visibility = View.GONE
                        vb.savedToolIv.visibility = View.VISIBLE
                        Toast.makeText(vb.root.context, it.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

}