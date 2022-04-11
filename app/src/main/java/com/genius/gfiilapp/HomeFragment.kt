package com.genius.gfiilapp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class
HomeFragment : Fragment() {
     private lateinit var recyclerview:RecyclerView
    private lateinit var listener:IPaketler
    private lateinit var  cardView: CardView
    private lateinit var cancelButon:ImageView
    private var list = ArrayList<Gorevler>()

    private lateinit var whatsapp:ImageView
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
        whatsapp =view.findViewById(R.id.whatsapp)
       val num:String="+905322045809"
        val msg:String ="Merhabalar,gfiilapp uygulamasında bir konu hakkında desteğinize ihtiyacım var:"
        whatsapp.setOnClickListener {
          //  val wpurl = "https://com.whatsapp/+905334070316?text=Hi,Is any one Avaliable?"
         //   val intent = Intent(Intent.ACTION_VIEW)
//       //     intent.setData(Uri.parse(wpurl))
          //  startActivity(intent)
            val installed = isAppInstalled("com.whatsapp")
            if (installed) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(  Uri.parse("http://api.whatsapp.com/send?phone=" + num.toString() + "&text=" + msg))
                startActivity(intent)
            } else {
                Toast.makeText(context, "Whatsapp is not installed!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        recyclerview=view.findViewById(R.id.recyclerview)

        val prefences =
            requireActivity().getSharedPreferences("com.genius.gfiilapp", Context.MODE_PRIVATE)
        val uKadi = prefences.getString("u_kadi", "DEFAULT_VALUE").orEmpty()
        println(uKadi)

        listener = object : IPaketler{
            override fun buttonClick(position: Int) {
                val kanit:String = "966a9fe2-8a04-48b8-aa2d-5c80041b87c1"
                // add another part within the multipart request

                // add another part within the multipart request

                Log.i("asdasda", list.get(position).url)
                 ApiUtils.usersDAOInterface().gorevTamamla(uKadi, list.get(position).id, list.get(position).url, 0, kanit).enqueue(object :
                    Callback<CRUDResponse> {
                    override fun onResponse(
                        call: Call<CRUDResponse>,
                        response: Response<CRUDResponse>
                    ) {

                        var gorevUrl:String = ""
                        gorevUrl = list.get(position).url

                        Log.d("minnos_eda", gorevUrl)

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
                        myWebView.loadUrl(gorevUrl)
                        myWebView.settings.allowContentAccess=true
                        myWebView.settings.domStorageEnabled=true
                        myWebView.settings.useWideViewPort=true
                        myWebView.settings.javaScriptEnabled = true
                        myWebView.settings.setAppCacheEnabled(true)
                    }

                    override fun onFailure(call: Call<CRUDResponse>, t: Throwable) {
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
                response.body()?.let {
                    list = ArrayList<Gorevler>()
                    list.addAll(it) }
                recyclerview.layoutManager = LinearLayoutManager(context)

                val adapter = context?.let { GorevlerAdapter(it,list,listener) } // veri geldikten sonra adapteri set edicez

                recyclerview.adapter = adapter
            }

            override fun onFailure(call: Call<List<Gorevler>>, t: Throwable) {

            }


        })


        super.onViewCreated(view, savedInstanceState)
    }


    private fun isAppInstalled(s: String): Boolean {
        var packageManager: PackageManager = requireActivity().getPackageManager()
        var is_installed: Boolean
        try {
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES)
            is_installed = true
        } catch (e: PackageManager.NameNotFoundException) {
            is_installed = false
            e.printStackTrace()
        }
        return is_installed
    }


}