package uz.olimjon_rustamov.dictionarycoroutines.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.olimjon_rustamov.dictionarycoroutines.databinding.ItemMeaningBinding
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.models.Definition
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.models.Meaning

class MeaningAdapter(var defs:List<Definition>) : RecyclerView.Adapter<MeaningAdapter.Vh>() {
    inner class Vh(var vb: ItemMeaningBinding) : RecyclerView.ViewHolder(vb.root) {
        fun onBind(def: Definition, position: Int) {
            vb.def = def
            vb.itemMeaningNumber.text=(position+1).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemMeaningBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(defs[position], position)
    }

    override fun getItemCount(): Int {
        return defs.size
    }
}