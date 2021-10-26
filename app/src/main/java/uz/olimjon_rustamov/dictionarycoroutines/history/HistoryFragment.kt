package uz.olimjon_rustamov.dictionarycoroutines.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import uz.olimjon_rustamov.dictionarycoroutines.R
import uz.olimjon_rustamov.dictionarycoroutines.databinding.FragmentHistoryBinding
import uz.olimjon_rustamov.dictionarycoroutines.home.adapters.LastSearchedAdapter
import uz.olimjon_rustamov.dictionarycoroutines.home.models.LastSearched
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseBuilder
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseHelperImpl

class HistoryFragment : Fragment() {

    private lateinit var vb:FragmentHistoryBinding
    private lateinit var lastSearchedList:ArrayList<LastSearched>
    private lateinit var adapter:LastSearchedAdapter
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
        vb = FragmentHistoryBinding.inflate(layoutInflater)

        loadData()

        return vb.root
    }
    private fun loadData() {
        db = DatabaseHelperImpl(DatabaseBuilder.getInstance(vb.root.context))
        lastSearchedList = db.getLastSearched() as ArrayList<LastSearched>
        adapter = LastSearchedAdapter(lastSearchedList, vb.root.context, true)
        vb.historyRv.adapter = adapter
    }
}