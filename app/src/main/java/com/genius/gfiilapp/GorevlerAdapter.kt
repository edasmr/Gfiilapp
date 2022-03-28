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

class GorevlerAdapter(private val context:Context, private val mList: ArrayList<Gorevler>,private val listener:IPaketler) : RecyclerView.Adapter<GorevlerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gorevler_item, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        val imgUrl:String = "https://gfiilapp.com/images/" + ItemsViewModel.img.replace("\\", "");
        Glide.with(context).load(imgUrl).into(holder.imageView)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.text1
        holder.textView2.text = ItemsViewModel.text2


        holder.cardView.setOnClickListener {
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
        val cardView: CardView = itemView.findViewById(R.id.cardView1)
    }


}