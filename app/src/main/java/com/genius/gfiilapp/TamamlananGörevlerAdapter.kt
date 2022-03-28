package com.genius.gfiilapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TamamlananGörevlerAdapter (private val context: Context, private val mList: ArrayList<TamamlananGörevler>) : RecyclerView.Adapter<TamamlananGörevlerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tamamlanan_gorev, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
       //holder.imageView.setImageResource(position)

        // sets the text to the textview from our itemHolder class
        holder.görevTarihi.text = ItemsViewModel.tarih
        holder.görevKazancı.text = ItemsViewModel.kazanc
        holder.görevAdı.text = ItemsViewModel.baslik
        if(ItemsViewModel.durum == 1)
            holder.görevDurumu.text = "Onaylandı"
        else
            holder.görevDurumu.text="Onay Bekliyor"


        //Sen bi harikasın iğrennc duruyı

    }



    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.logo)
        val görevTarihi: TextView = itemView.findViewById(R.id.gorev_tarihi)
        val görevKazancı: TextView = itemView.findViewById(R.id.gorev_kazanci)
        val görevAdı: TextView = itemView.findViewById(R.id.gorev_adi)
        val görevDurumu: TextView = itemView.findViewById(R.id.gorev_durumu)
    }


}