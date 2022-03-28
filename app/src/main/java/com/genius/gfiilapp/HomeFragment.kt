package com.genius.gfiilapp

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class
HomeFragment : Fragment() {
     private lateinit var recyclerview:RecyclerView
    private lateinit var listener:IPaketler
    private lateinit var  cardView: CardView
    private var list = ArrayList<Gorevler>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerview=view.findViewById(R.id.recyclerview)



        listener = object : IPaketler{
            override fun buttonClick(position: Int) {

                val file: File = File("a.jpg")
                val requestFile: RequestBody = RequestBody.create(
                    MediaType.parse("application/octet-stream"),
                    file
                )

                // MultipartBody.Part is used to send also the actual file name

                // MultipartBody.Part is used to send also the actual file name
                val body = MultipartBody.Part.createFormData("a.jpg", "a.jpg", requestFile)

                // add another part within the multipart request

                // add another part within the multipart request


                ApiUtils.usersDAOInterface().gorevTamamla(list.get(position).id, list.get(position).url, true, body).enqueue(object :
                    Callback<String> {
                    override fun onResponse(
                        call: Call<String>,
                        response: Response<String>
                    ) {

                        var gorevUrl:String = ""
                        response.body()?.let { gorevUrl = it; }

                        Log.d("minnos_eda", gorevUrl)

                        val dialog = Dialog(requireActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen)
                        dialog.setContentView(R.layout.vebview_layout)
                        dialog.show()


                        val myWebView: WebView = dialog.findViewById(R.id.webView)
                        myWebView.webViewClient = object : WebViewClient() {

                            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                                if (url != null) {
                                    view?.loadUrl (url)
                                }
                                return true
                            }
                        }
                        myWebView.loadUrl(gorevUrl)
                        myWebView.settings.allowContentAccess=true
                        myWebView.settings.domStorageEnabled=true
                        myWebView.settings.useWideViewPort=true
                        myWebView.settings.setAppCacheEnabled(true)
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("minnos_eda", "hata")

                    }


                })

            }

        }

        ApiUtils.usersDAOInterface().getGorevler().enqueue(object :
            Callback<List<Gorevler>> {
            override fun onResponse(
                call: Call<List<Gorevler>>,
                response: Response<List<Gorevler>>
            ) {
                response.body()?.let { list.addAll(it) }
                recyclerview.layoutManager = LinearLayoutManager(context)

                val adapter = context?.let { GorevlerAdapter(it,list,listener) } // veri geldikten sonra adapteri set edicez

                recyclerview.adapter = adapter
            }

            override fun onFailure(call: Call<List<Gorevler>>, t: Throwable) {

            }


        })


        super.onViewCreated(view, savedInstanceState)
    }
}