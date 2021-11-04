package uz.olimjon_rustamov.dictionarycoroutines.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import uz.olimjon_rustamov.dictionarycoroutines.MainActivity
import uz.olimjon_rustamov.dictionarycoroutines.R
import uz.olimjon_rustamov.dictionarycoroutines.databinding.FragmentSavedBinding
import uz.olimjon_rustamov.dictionarycoroutines.home.adapters.LastSearchedAdapter
import uz.olimjon_rustamov.dictionarycoroutines.home.models.LastSearched
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseBuilder
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseHelperImpl

class SavedFragment : Fragment() {

    private lateinit var vb: FragmentSavedBinding
    private lateinit var savedList: ArrayList<LastSearched>
    private lateinit var db: DatabaseHelperImpl
    private lateinit var adapter: LastSearchedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentSavedBinding.inflate(layoutInflater)



        loadData()

        return vb.root
    }

    private fun loadData() {
        db = DatabaseHelperImpl(DatabaseBuilder.getInstance(vb.root.context))
        savedList = db.getSavedLastSearched().reversed() as ArrayList<LastSearched>
        adapter = LastSearchedAdapter(savedList, vb.root.context, "saved")
        vb.savedRv.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbar_main?.title="Saved"
    }

}