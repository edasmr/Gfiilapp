package com.genius.gfiilapp
    import android.app.Dialog
    import android.content.Intent
    import android.os.Build
    import android.os.Bundle
    import android.text.*
    import android.view.LayoutInflater
    import android.view.View
    import android.view.Window
    import android.widget.*
    import androidx.appcompat.app.AlertDialog
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.content.ContentProviderCompat.requireContext
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response
    import java.util.*


class SignUpActivity : AppCompatActivity() {
    private lateinit var clicktex:TextView
    private lateinit var phonenumber:EditText
    private lateinit var username:EditText
    private lateinit var checkbox:CheckBox
    var items = arrayOf("Kadın", "Erkek")
    private lateinit var autoCompleteTxt: AutoCompleteTextView
    var adapterItems: ArrayAdapter<String>? = null


    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        clicktex=findViewById(R.id.clicktext)
        username=findViewById(R.id.username)
        checkbox = findViewById(R.id.checkbox)
        val email:EditText =findViewById(R.id.email)
        val password:EditText =findViewById(R.id.password)
      // val cinsiyet:EditText = findViewById(R.id.cinsiyet)
         phonenumber=findViewById(R.id.phonenumber)
        val referanceno:EditText =findViewById(R.id.referanceno)
        val btnKayit:Button =findViewById(R.id.btnKayit)
        val girisBtn:Button =findViewById(R.id.girisBtn)

        val tc = intent.getStringExtra("tc").orEmpty()
        val ad = intent.getStringExtra("ad").orEmpty()
        val soyad = intent.getStringExtra("soyad").orEmpty()
        val dyili = intent.getStringExtra("dyili").orEmpty()

        autoCompleteTxt = findViewById(R.id.auto_complete_txt)
        adapterItems = ArrayAdapter(this, R.layout.list_item, items)
        autoCompleteTxt.setAdapter(adapterItems)
        autoCompleteTxt.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position).toString()
          //  Toast.makeText(applicationContext, "Item: $item", Toast.LENGTH_SHORT).show()
        })


        clicktex.setOnClickListener(View.OnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.alert_dialog_sozlesme)
            dialog.show()
            val body = dialog.findViewById<TextView>(R.id.body)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                body.setText(Html.fromHtml(getString(R.string.sozlesme), Html.FROM_HTML_MODE_COMPACT));
            } else {
                body.setText(Html.fromHtml(getString(R.string.sozlesme)));
            }

        })

        girisBtn.setOnClickListener {
            val intent = Intent(this@SignUpActivity,LoginActivity::class.java)

            startActivity(intent)
        }

        btnKayit.setOnClickListener {
            if(!checkbox.isChecked){
                Toast.makeText(this, "Lütfen sözleşmeyi kabul edin."
                    ,Toast.LENGTH_SHORT).show()
            } else {

                ApiUtils.usersDAOInterface().kayitOl(
                    username.text.toString(),
                    email.text.toString(),
                    password.text.toString(),
                   autoCompleteTxt.toString(),
                  // cinsiyet.text.toString(),
                    phonenumber.text.toString(),
                    referanceno.text.toString(),
                    tc,
                    ad,
                    soyad,
                    dyili
                ).enqueue(object :
                    Callback<CRUDResponse> {
                    override fun onResponse(
                        call: Call<CRUDResponse>,
                        response: Response<CRUDResponse>

                    ) {
                        println(response.body()?.status)
                        if (response.body()?.status == "ok") {
                            Toast.makeText(
                                this@SignUpActivity,
                                "BAŞARILI GİRİŞ",
                                Toast.LENGTH_SHORT
                            ).show()


                            val intent =
                                Intent(this@SignUpActivity, SmsDogrulamaActivity::class.java)
                            intent.putExtra("telno", phonenumber.text.toString())
                            intent.putExtra("kadi", username.text.toString())
                            startActivity(intent)

                        }
                        if (response.body()?.status == "exist") {
                            Toast.makeText(
                                this@SignUpActivity,
                                "BÖYLE BİRİ VAR ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (response.body()?.status == "error") {
                            Toast.makeText(
                                this@SignUpActivity,
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
        }


    }

}
