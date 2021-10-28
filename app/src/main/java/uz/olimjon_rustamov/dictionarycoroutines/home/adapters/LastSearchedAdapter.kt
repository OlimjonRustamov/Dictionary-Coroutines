package uz.olimjon_rustamov.dictionarycoroutines.home.adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import uz.olimjon_rustamov.dictionarycoroutines.databinding.ItemHistoryBinding
import uz.olimjon_rustamov.dictionarycoroutines.databinding.ItemHomepageLastSearchedBinding
import uz.olimjon_rustamov.dictionarycoroutines.databinding.ItemLastSearchSavedBinding
import uz.olimjon_rustamov.dictionarycoroutines.home.models.LastSearched
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseBuilder
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.DatabaseHelperImpl

class LastSearchedAdapter(
    var last_searched_list: ArrayList<LastSearched>,
    var context: Context,
    var status: String
) : RecyclerView.Adapter<LastSearchedAdapter.Vh>() {
    val db = DatabaseHelperImpl(DatabaseBuilder.getInstance(context))

    inner class Vh(var vdb: ViewDataBinding) : RecyclerView.ViewHolder(vdb.root) {
        fun onBindHistory(last: LastSearched, position: Int) {
            (vdb as ItemHistoryBinding).lastSearched = last
            (vdb as ItemHistoryBinding).lastSearchedHistoryImg.setOnClickListener {
                if (last.isSaved!!) {
                    last.isSaved = false
                    db.updateLastSearched(last)
                    notifyItemChanged(position)
                } else {
                    last.isSaved = true
                    db.updateLastSearched(last)
                    notifyItemChanged(position)
                }
            }
            (vdb as ItemHistoryBinding).x.setOnClickListener {
                db.deleteLastSearched(last)
                notifyItemRemoved(position)
                last_searched_list.removeAt(position)
                notifyItemRangeChanged(0, last_searched_list.size)
            }
        }

        fun onBindHomePage(last: LastSearched, position: Int) {
            (vdb as ItemHomepageLastSearchedBinding).lastSearched = last
            (vdb as ItemHomepageLastSearchedBinding).lastSearchedHomePageImg.setOnClickListener {
                if (last.isSaved!!) {
                    last.isSaved = false
                    db.updateLastSearched(last)
                    notifyItemChanged(position)
                } else {
                    last.isSaved = true
                    db.updateLastSearched(last)
                    notifyItemChanged(position)
                }
            }
        }

        fun onBindSaved(last: LastSearched, position: Int) {
            (vdb as ItemLastSearchSavedBinding).lastSearched = last
            (vdb as ItemLastSearchSavedBinding).lastSearchedSavedImg.setOnClickListener {
                if (last.isSaved!!) {
                    last.isSaved = false
                    db.updateLastSearched(last)
                    notifyItemChanged(position)
                } else {
                    last.isSaved = true
                    db.updateLastSearched(last)
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        if (status == "history") return Vh(
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        else if (status == "homepage") return Vh(
            ItemHomepageLastSearchedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) else return Vh(
            ItemLastSearchSavedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        if (status == "history") {
            holder.onBindHistory(last_searched_list[position], position)
        } else if (status == "homepage") {
            holder.onBindHomePage(last_searched_list[position], position)
        } else holder.onBindSaved(last_searched_list[position], position)
    }

    override fun getItemCount(): Int = last_searched_list.size
}