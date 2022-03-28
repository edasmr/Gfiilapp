package com.genius.gfiilapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class TaskFragment : Fragment() {

    private lateinit var recyclerview: RecyclerView

    private var list = ArrayList<TamamlananGörevler>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerview=view.findViewById(R.id.recyclerview1)

        val prefences =
            requireActivity().getSharedPreferences("com.genius.gfiilapp", Context.MODE_PRIVATE)
        val uKadi = prefences.getString("u_kadi", "DEFAULT_VALUE").orEmpty()
        println(uKadi)
        println("eda")


        ApiUtils.usersDAOInterface().tamamlananGorevler(uKadi).enqueue(object :
            Callback<List<TamamlananGörevler>> {
            override fun onResponse(
                call: Call<List<TamamlananGörevler>>,
                response: Response<List<TamamlananGörevler>>
            ) {
                response.body()?.let { list.addAll(it) }
                recyclerview.layoutManager = LinearLayoutManager(context)

                val adapter = context?.let { TamamlananGörevlerAdapter(it,list) } // veri geldikten sonra adapteri set edicez

               recyclerview.adapter = adapter
            }

            override fun onFailure(call: Call<List<TamamlananGörevler>>, t: Throwable) {

            }


        })


        super.onViewCreated(view, savedInstanceState)
    }


}