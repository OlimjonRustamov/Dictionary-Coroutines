package uz.olimjon_rustamov.dictionarycoroutines.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import uz.olimjon_rustamov.dictionarycoroutines.Cashe.Cashe
import uz.olimjon_rustamov.dictionarycoroutines.MainActivity
import uz.olimjon_rustamov.dictionarycoroutines.R
import uz.olimjon_rustamov.dictionarycoroutines.databinding.FragmentSearchBinding
import uz.olimjon_rustamov.dictionarycoroutines.home.models.LastSearched
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.RetrofitBuilder
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.models.WordResponseItem
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.viewmodel.SingleNetworkCallViewModel
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseBuilder
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseHelperImpl
import uz.olimjon_rustamov.dictionarycoroutines.utils.Status

class SearchFragment : Fragment() {

    private lateinit var vb:FragmentSearchBinding
    private lateinit var viewModel: SingleNetworkCallViewModel
    private lateinit var db: DatabaseHelperImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentSearchBinding.inflate(layoutInflater)

        setupViewModelDb()

        return vb.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbar_main?.title="Search"
    }
    private fun setupViewModelDb() {
        viewModel = ViewModelProvider(this).get(SingleNetworkCallViewModel::class.java)
        db= DatabaseHelperImpl(DatabaseBuilder.getInstance(vb.root.context))
        val word=Cashe.instance!!.getWord()
        viewModel.fetchWord(word)
        viewModel.getWordResponse().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    vb.progressSearch.visibility = View.GONE
                    vb.searchRoot.visibility = View.VISIBLE
                    it.data?.let {
                        val lastSearched = LastSearched(it.word, it.meanings[0].definitions[0].definition, false)
                        if (db.getLastSearched().reversed()[0].title != it.word) {
                            db.insertLastSearched(lastSearched)
                        }
                        vb.textTest.text=it.meanings[0].definitions[0].definition
                    }
//                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    vb.searchRoot.visibility = View.GONE
                    vb.progressSearch.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    vb.progressSearch.visibility = View.GONE
                    vb.root.visibility = View.VISIBLE
                    Toast.makeText(vb.root.context, it.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}