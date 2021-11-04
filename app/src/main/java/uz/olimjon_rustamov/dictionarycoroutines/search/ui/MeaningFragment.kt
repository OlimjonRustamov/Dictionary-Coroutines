package uz.olimjon_rustamov.dictionarycoroutines.search.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import uz.olimjon_rustamov.dictionarycoroutines.R
import uz.olimjon_rustamov.dictionarycoroutines.databinding.FragmentMeaningBinding
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.models.Meaning
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.models.WordResponseItem
import uz.olimjon_rustamov.dictionarycoroutines.search.adapter.MeaningAdapter

class MeaningFragment : Fragment() {
    private var word: WordResponseItem? = null

    private lateinit var vb:FragmentMeaningBinding
    private lateinit var adapter:MeaningAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
             word= it.getSerializable("word") as WordResponseItem
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentMeaningBinding.inflate(layoutInflater)

        word.let { loadAdapter(it!!) }

        return vb.root
    }

    companion object {
        @JvmStatic
        fun newInstance(word: WordResponseItem) =
            MeaningFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("word", word)
                }
            }
    }

    private fun loadAdapter(word: WordResponseItem) {
        adapter = MeaningAdapter(word.meanings[0].definitions)
        vb.meaningRv.layoutManager = LinearLayoutManager(vb.root.context)
        vb.meaningRv.adapter = adapter
    }
}