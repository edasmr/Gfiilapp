package com.genius.gfiilapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SmsDogrulamaActivity : AppCompatActivity() {
    private lateinit var smsDogrulama:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sms_dogrula)

         smsDogrulama=findViewById(R.id.smsDogrulama)
        val onaylaButon:Button = findViewById(R.id.onaylaButon)
        val tekrarGonderButon:Button = findViewById(R.id.tekrarGonderButon)
        val vazgecButon:Button = findViewById(R.id.vazgecButon)
        val telno = intent.getStringExtra("telno").orEmpty()
        val kadi = intent.getStringExtra("kadi").orEmpty()


        onaylaButon.setOnClickListener {


            ApiUtils.usersDAOInterface().smsDogrula(
                kadi, smsDogrulama.text.toString(), telno
            ).enqueue(object :
                Callback<CRUDResponse> {
                override fun onResponse(call: Call<CRUDResponse>,
                                        response: Response<CRUDResponse>
                ){
                    if(response.body()?.result_code == 1) {
                        val intent = Intent(this@SmsDogrulamaActivity,LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@SmsDogrulamaActivity,
                            "Doğrulama Kodu Hatalı!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<CRUDResponse>, t: Throwable) {
                    println(t.localizedMessage.toString())
                }

            })


        }

        tekrarGonderButon.setOnClickListener {

            ApiUtils.usersDAOInterface().tekrarSmsGonder(
                kadi, telno
            ).enqueue(object :
                Callback<CRUDResponse> {
                override fun onResponse(call: Call<CRUDResponse>,
                                        response: Response<CRUDResponse>
                ){
                    if(response.body()?.status == "ok") {
                        Toast.makeText(
                            this@SmsDogrulamaActivity,
                            "tekrar sms kodu gönderildi",
                            Toast.LENGTH_SHORT
                        ).show()




                    }
                    if(response.body()?.status=="error") {
                        Toast.makeText(
                            this@SmsDogrulamaActivity,
                            "sms kodu gonderilemedi",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<CRUDResponse>, t: Throwable) {
                    println(t.localizedMessage.toString())
                }

            })



        }

    }
}