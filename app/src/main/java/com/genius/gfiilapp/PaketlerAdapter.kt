package com.genius.gfiilapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PaketlerAdapter(private val context: Context, private val mList: ArrayList<Paketler>, private val listener:IPaketler) : RecyclerView.Adapter<PaketlerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.paketler_item, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        if(ItemsViewModel.id ==11) {
            Glide.with(context).load(R.drawable.standartpaket).into(holder.imageView)
        }else if(ItemsViewModel.id ==12){
            Glide.with(context).load(R.drawable.silverpaket).into(holder.imageView)
        }else if(ItemsViewModel.id ==13){
            Glide.with(context).load(R.drawable.businesspaket).into(holder.imageView)
        }else if(ItemsViewModel.id ==14){
            Glide.with(context).load(R.drawable.premiumpaket).into(holder.imageView)
        }else {
            Glide.with(context).load(R.drawable.vippaket).into(holder.imageView)

        }

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.ad
        holder.textView2.text = ItemsViewModel.fiyat
        holder.textView3.text = ItemsViewModel.l_1
        holder.textView4.text = ItemsViewModel.l_2
        holder.textView5.text = ItemsViewModel.l_3
        holder.textView6.text = ItemsViewModel.l_4

        holder.bakiyeButon.setOnClickListener {
            listener.buttonClick(position)
        }

    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val textView2: TextView = itemView.findViewById(R.id.textView2)
        val textView3: TextView = itemView.findViewById(R.id.textView3)
        val textView4: TextView = itemView.findViewById(R.id.textView4)
        val textView5: TextView = itemView.findViewById(R.id.textView5)
        val textView6: TextView = itemView.findViewById(R.id.textView6)
        val bakiyeButon: Button = itemView.findViewById(R.id.bakiyeButon)

    }


}