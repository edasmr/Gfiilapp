package com.genius.gfiilapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekimMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cekim_main)


        val cekimMiktar: EditText = findViewById(R.id.cekimMiktar)
        val adSoyad: EditText = findViewById(R.id.adSoyad)
        val bankaAdi: EditText = findViewById(R.id.bankaAdi)
        val ibanNo: EditText = findViewById(R.id.ibanNo)
        val hesapNo: EditText = findViewById(R.id.hesapNo)
        val refKazanc: EditText = findViewById(R.id.refKazanc)
        val aciklama: EditText = findViewById(R.id.aciklama)
        val ödemeTalebi: Button = findViewById(R.id.ödemeTalebi)
        val anaBakiyeAktar: Button = findViewById(R.id.anaBakiyeAktar)
        val cekimTalebiVer: Button = findViewById(R.id.cekimTalebiVer)

        val prefences =applicationContext.getSharedPreferences("com.genius.gfiilapp", Context.MODE_PRIVATE)
        val uKadi = prefences.getString("u_kadi", "DEFAULT_VALUE").orEmpty()
        println(uKadi)

        anaBakiyeAktar.setOnClickListener {
            val intent = Intent(this@CekimMainActivity,OdemeActivity::class.java)

            startActivity(intent)
        }

        ödemeTalebi.setOnClickListener {


            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            builder.addFormDataPart("u_kadi", uKadi)
            builder.addFormDataPart("ct_bakiye", cekimMiktar.text.toString())
            builder.addFormDataPart("ct_adsoyad", adSoyad.text.toString())
            builder.addFormDataPart("ct_bankaadi", bankaAdi.text.toString())
            builder.addFormDataPart("ct_iban", ibanNo.text.toString())
            builder.addFormDataPart("ct_hesapno", hesapNo.text.toString())
            builder.addFormDataPart("ref_istek", refKazanc.text.toString())
            builder.addFormDataPart("ct_not", aciklama.text.toString())


            ApiUtils.usersDAOInterface()
                .cekimTalebiVer(builder.build())
                .enqueue(object :
                    Callback<CRUDResponse> {
                    override fun onResponse(
                        call: Call<CRUDResponse>,
                        response: Response<CRUDResponse>
                    ) {

                        if (response.body()?.result_code == 1) {
                            Toast.makeText(
                                this@CekimMainActivity,
                                "BAŞARILIoldu",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.d("seda", "basarızzz giris")
                            println(response.body()?.response)
                           // Toast.makeText(applicationContext, response.body()?.response, Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<CRUDResponse>, t: Throwable) {
                        println(t.localizedMessage.toString())
                    }

                })
        }

    }
}