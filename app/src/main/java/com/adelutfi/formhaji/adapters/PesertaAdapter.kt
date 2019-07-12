package com.adelutfi.formhaji.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adelutfi.formhaji.R
import com.adelutfi.formhaji.models.Peserta
import kotlinx.android.synthetic.main.item_complain.view.*

class PesertaAdapter (private var complains : List<Peserta>, private var context : Context) : RecyclerView.Adapter<PesertaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_complain, parent, false))
    }

    override fun getItemCount() = complains.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.binding(complains[position], context)

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun binding(c : Peserta, context: Context){
            itemView.title.text = c.title
            itemView.message.text = c.message
            itemView.jk.text = c.input2
            itemView.alamat.text = c.input1
        }
    }

}