package com.genius.gfiilapp

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PacketFragment : Fragment() {

    private lateinit var recyclerview: RecyclerView
    private lateinit var listener:IPaketler
    private lateinit var cancelButon:ImageView
    private var list = ArrayList<Paketler>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_packet, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerview=view.findViewById(R.id.recyclerview2)

        recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)


        listener = object : IPaketler{
            override fun buttonClick(position: Int) {
                val dialog = Dialog(requireActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen)
                dialog.setContentView(R.layout.vebview_layout)
                dialog.show()
                cancelButon = dialog.findViewById(R.id.cancelBtn)
                cancelButon.setOnClickListener {
                    dialog.dismiss()
                }

                val myWebView: WebView = dialog.findViewById(R.id.webView)
                myWebView.webViewClient = object : WebViewClient() {

                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        if (url != null) {
                            view?.loadUrl (url)
                        }
                        return true
                    }
                }
                myWebView.loadUrl("https://g-fiilapp.hesapno.com/")
                myWebView.settings.allowContentAccess=true
                myWebView.settings.domStorageEnabled=true
                myWebView.settings.useWideViewPort=true
                myWebView.settings.setAppCacheEnabled(true)
            }

        }



        ApiUtils.usersDAOInterface().getPaketler().enqueue(object :
            Callback<List<Paketler>> {
            override fun onResponse(
                call: Call<List<Paketler>>,
                response: Response<List<Paketler>>
            ) {
                response.body()?.let { list.addAll(it) }

                val adapter = context?.let { PaketlerAdapter(it, list, listener) } // veri geldikten sonra adapteri set edicez

                recyclerview.adapter = adapter
            }

            override fun onFailure(call: Call<List<Paketler>>, t: Throwable) {

            }


        })


        super.onViewCreated(view, savedInstanceState)
    }

}