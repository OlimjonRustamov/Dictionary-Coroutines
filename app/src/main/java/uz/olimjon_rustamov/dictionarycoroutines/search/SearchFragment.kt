package uz.olimjon_rustamov.dictionarycoroutines.search

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import uz.olimjon_rustamov.dictionarycoroutines.Cashe.Cashe
import uz.olimjon_rustamov.dictionarycoroutines.MainActivity
import uz.olimjon_rustamov.dictionarycoroutines.R
import uz.olimjon_rustamov.dictionarycoroutines.databinding.FragmentSearchBinding
import uz.olimjon_rustamov.dictionarycoroutines.home.models.LastSearched
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.viewmodel.SingleNetworkCallViewModel
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseBuilder
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseHelperImpl
import uz.olimjon_rustamov.dictionarycoroutines.search.adapter.SearchPagerAdapter
import uz.olimjon_rustamov.dictionarycoroutines.utils.Status
import java.lang.Exception

class SearchFragment : Fragment() {

    private lateinit var vb:FragmentSearchBinding
    private lateinit var viewModel: SingleNetworkCallViewModel
    private lateinit var db: DatabaseHelperImpl
    private lateinit var vpAdapter:SearchPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentSearchBinding.inflate(layoutInflater)

        setupViewModelDb()

        return vb.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbar_main?.title=Cashe.instance!!.getWord()
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
                        try {
                            if (db.getLastSearched().reversed()[0].title != it.word) {
                                db.insertLastSearched(lastSearched)
                            }
                        } catch (e: Exception) {
                            db.insertLastSearched(lastSearched)
                        }
                        vpAdapter = SearchPagerAdapter(it, requireFragmentManager())
                        vb.searchViewPager.adapter = vpAdapter
                        vb.tabLayoutBar.setupWithViewPager(vb.searchViewPager)
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

    private fun setupToolClicks() {
        vb.apply {
            copyTool.setOnClickListener {
                copyToClipBoard(Cashe.instance!!.getWord())
            }
            savedTool.setOnClickListener {
                val lastSearched = db.getLastSearched().reversed()[0]
                lastSearched.isSaved = true
                db.updateLastSearched(lastSearched)
                Toast.makeText(root.context, "Saved!", Toast.LENGTH_SHORT).show()
            }
            shareTool.setOnClickListener {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    Cashe.instance!!.getWord()
                )
                startActivity(Intent.createChooser(shareIntent, "Share to"))
            }
            speakerTool.setOnClickListener {
                speakerFunc()
            }
        }
    }

    private fun copyToClipBoard(word: String) {
        val clipboard: ClipboardManager =
            activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("word", word))
        Toast.makeText(vb.root.context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sound_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_sound) {
            speakerFunc()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun speakerFunc() {
        Toast.makeText(vb.root.context, "Sound", Toast.LENGTH_SHORT).show()
    }
}