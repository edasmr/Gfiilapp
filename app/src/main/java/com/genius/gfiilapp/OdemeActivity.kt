package com.genius.gfiilapp

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OdemeActivity : AppCompatActivity() {

    private lateinit var recyclerviewOdeme: RecyclerView

    private var list = ArrayList<TamamlananOdemeler>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_odeme)

        val bakiye: TextView =findViewById(R.id.bakiyee)
        val anaBakiyeAktar:Button = findViewById(R.id.anaBakiyeAktar)
        val cekimTalebiVer:Button = findViewById(R.id.cekimTalebiVer)
        val anaBakiyeAktarBtn:Button = findViewById(R.id.anaBakiyeAktarBtn)
        recyclerviewOdeme=findViewById(R.id.recyclerviewOdeme)
        val prefences =applicationContext.getSharedPreferences("com.genius.gfiilapp", Context.MODE_PRIVATE)
        val uKadi = prefences.getString("u_kadi", "DEFAULT_VALUE").orEmpty()
        println(uKadi)

        cekimTalebiVer.setOnClickListener {

            val intent = Intent(this@OdemeActivity,CekimMainActivity::class.java)

            startActivity(intent)

        }
      anaBakiyeAktarBtn.setOnClickListener {
          ApiUtils.usersDAOInterface().anaBakiyeAktar(uKadi,bakiye.text.toString(),"ana bakiye",)
              .enqueue(object : // gonderecegımız seylerı yaz
                  Callback<CRUDResponse> {
                  override fun onResponse(
                      call: Call<CRUDResponse>,
                      response: Response<CRUDResponse>
                  ) {

                      if (response.body()?.result_code == 1) {

                      println("dogru")
                          Toast.makeText(
                              this@OdemeActivity,
                              " Puanınız Ana Bakiyeye Aktarıldı.",
                              Toast.LENGTH_SHORT
                          ).show()

                          anaBakiyeAktarBtn.visibility =View.GONE

                      } else {
                          Log.d("12345", "basarızzz oldu")

                      }
                  }

                  override fun onFailure(call: Call<CRUDResponse>, t: Throwable) {
                      println(t.localizedMessage.toString())
                  }

              })
      }
        ApiUtils.usersDAOInterface().profileDetay(uKadi)
            .enqueue(object : // gonderecegımız seylerı yaz
                Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {

                    if (response.body()?.u_kadi != null) {

                        bakiye.text = response.body()?.u_bakiye + "tl"


                    } else {
                        Log.d("12345", "basarızzz oldu")

                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    println(t.localizedMessage.toString())
                }

            })

        ApiUtils.usersDAOInterface().tamamlananOdemeler(uKadi).enqueue(object :
            Callback<List<TamamlananOdemeler>> {
            override fun onResponse(
                call: Call<List<TamamlananOdemeler>>,
                response: Response<List<TamamlananOdemeler>>
            ) {
                response.body()?.let { list.addAll(it) }
                recyclerviewOdeme.layoutManager = LinearLayoutManager(applicationContext)

                val adapter = applicationContext?.let { OdemeAdapter(it,list) } // veri geldikten sonra adapteri set edicez
                println(list)
                recyclerviewOdeme.adapter = adapter
            }

            override fun onFailure(call: Call<List<TamamlananOdemeler>>, t: Throwable) {
                println(t.localizedMessage.toString())

            }


        })

    }
}