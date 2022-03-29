package com.genius.gfiilapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val manager:FragmentManager
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       val username:EditText =findViewById(R.id.username)
       val password:EditText =findViewById(R.id.password)
       val loginButon:Button =findViewById(R.id.loginbuton)
       val uyeolBtn:Button =findViewById(R.id.uyeOlBtn)


        loginButon.setOnClickListener {
            ApiUtils.usersDAOInterface().girisYap(username.text.toString(),password.text.toString()).enqueue(object :
                Callback<CRUDResponse>{
                override fun onResponse(
                    call: Call<CRUDResponse>,
                    response: Response<CRUDResponse>
                ) {
                    println(response.body()?.result_code)
                    if(response.body()?.result_code==1) {
                        Toast.makeText(
                            this@LoginActivity,
                            "BAŞARILI GİRİŞ",
                            Toast.LENGTH_SHORT
                        ).show()

                      //  val PREFS_FILENAME = "com.genius.gfiilapp"
                        val prefences = getSharedPreferences("com.genius.gfiilapp", Context.MODE_PRIVATE)
                        val editor = prefences.edit()
                        val keyname="u_kadi"
                        editor.putString(keyname,username.text.toString())
                        editor.apply()
                        val bundle = Bundle()

                        bundle.putString("birinci",username.text.toString())
                        //sharedPreference burda key tutcan key,değeri sisteme bi tane integer yada string kullanıcı adınıı tutcaz bunun ıcın veritabanı acmaya gerek yok tek degerlık vreıtabanı gıbı dusun 3 4 satır kodu var.  key , username.text.toString() kullanıcı adını veritabanını gondermıs oluyoruz .

                        val intent = Intent(this@LoginActivity,MainActivity::class.java)

                        startActivity(intent)

                    }
                    else{
                        Toast.makeText(
                            this@LoginActivity,
                            "BAŞARISIZ GİRİŞ",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

                override fun onFailure(call: Call<CRUDResponse>, t: Throwable) {
                    println(t.localizedMessage.toString())
                }

            })

        }

        uyeolBtn.setOnClickListener {

            val intent = Intent(this@LoginActivity,SignInActivity::class.java)

            startActivity(intent)

        }

    }

    //fun calistir(view)
}