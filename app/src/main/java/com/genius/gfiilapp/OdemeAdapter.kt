package com.genius.gfiilapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OdemeAdapter (private val context: Context, private val mList: ArrayList<TamamlananOdemeler>) : RecyclerView.Adapter<OdemeAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.odeme_cardview, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]



        // sets the text to the textview from our itemHolder class
        holder.talepTarihi.text = ItemsViewModel.ct_date
        holder.talepTutari.text = ItemsViewModel.ct_bakiye
        holder.ibanNo.text = ItemsViewModel.ct_iban
       holder.talepDurumu.text = ItemsViewModel.ct_red_sebep


    }



    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val talepTarihi: TextView = itemView.findViewById(R.id.talepTarihi)
        val talepTutari: TextView = itemView.findViewById(R.id.talepTutari)
        val ibanNo: TextView = itemView.findViewById(R.id.ibanNo)
        val talepDurumu: TextView = itemView.findViewById(R.id.talepDurumu)
    }


}