package com.genius.gfiilapp

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import java.net.ConnectException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import java.util.*


class SignInActivity : AppCompatActivity() {
    private lateinit var devambtn:Button
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Uyarı!")
        alertDialog.setMessage("Lütfen Büyük ve Türkçe karakter ile yazın. \nör: OKANCAN COŞAR")
        alertDialog.show()
        devambtn = findViewById(R.id.devambtn)
    }

    fun controlTCKN(view: View?) {
        val tcknArea = findViewById<View>(R.id.tcknArea) as EditText
        val tckn = tcknArea.text.toString().trim { it <= ' ' }
        val nameArea = findViewById<View>(R.id.nameArea) as EditText
       // nameArea.filters = arrayOf<InputFilter>(InputFilter.AllCaps())
        val name = nameArea.text.toString().trim { it <= ' ' }.uppercase(Locale("tr_TR"))
        val surnameArea = findViewById<View>(R.id.surnameArea) as EditText
        val surname = surnameArea.text.toString().trim { it <= ' ' }.uppercase(Locale("tr_TR"))
        val birthDateArea = findViewById<View>(R.id.yil) as EditText
        val year = birthDateArea.text.toString().trim { it <= ' ' }

        if (name.isEmpty() || surname.isEmpty() || tckn.isEmpty()) {
            Toast.makeText(
                this@SignInActivity,
                "Lütfen gerekli alanları doldurunuz",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            controlTCKNService().execute(tckn, name, surname, year)
        }

    }


    private inner class controlTCKNService :
        AsyncTask<String?, Void?, Void?>() {
        private var resultText: String? = null
        private var result = false
        private val progressDialog = AlertDialog.Builder(this@SignInActivity)
        private val builder = AlertDialog.Builder(this@SignInActivity)
        var alert: AlertDialog? = null
        var progressAlert: AlertDialog? = null
        override fun onPreExecute() {
            progressAlert=progressDialog.setMessage("Kontrol ediliyor...").create()
            progressAlert?.show()
            
        }

        override fun onPostExecute(unused: Void?) {

            alert = builder.setMessage(resultText)
                .setCancelable(true)
                .setTitle("Sonuç")
                .setPositiveButton(
                    "Tamam"
                ) { dialog, id ->
                    progressAlert?.dismiss()
                }.create()
            alert!!.show()
        }

        override fun doInBackground(vararg p0: String?): Void? {

            val request = SoapObject(NAMESPACE, METHOD_NAME)
            request.addProperty("TCKimlikNo", p0[0])
            request.addProperty("Ad", p0[1])
            request.addProperty("Soyad", p0[2])
            request.addProperty("DogumYili", p0[3])
            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)
            val androidHttpTransport = HttpTransportSE(URL)
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope)
                val response = envelope.bodyIn as SoapObject
                result = java.lang.Boolean.parseBoolean(response.getProperty(0).toString())
                if(result==true){
                    devambtn.setOnClickListener {
                        val intent = Intent(this@SignInActivity,SignUpActivity::class.java)
                         intent.putExtra("tc", p0[0])
                         intent.putExtra("ad", p0[1])
                         intent.putExtra("soyad", p0[2])
                         intent.putExtra("dyili", p0[3])
                        startActivity(intent)
                    }

                }
                resultText = if (result) {
                    "TC Kimlik Numarası Geçerli"
                } else {
                    "TC Kimlik Numarası Geçersiz"
                }
            } catch (e: ClassCastException) {
                result = false
                resultText = "TC Kimlik Numarası Geçersiz"
            } catch (e: ConnectException) {
                result = false
                resultText =
                    "İnternet bağlantınız kesilmiş ya da TCKN Servisi devre dışı kalmış olabilir."
            } catch (e: UnknownHostException) {
                result = false
                resultText =
                    "İnternet bağlantınız kesilmiş ya da TCKN Servisi devre dışı kalmış olabilir."
            } catch (e: UnknownServiceException) {
                result = false
                resultText =
                    "İnternet bağlantınız kesilmiş ya da TCKN Servisi devre dışı kalmış olabilir."
            } catch (e: Exception) {
                result = false
                resultText =
                    "İnternet bağlantınız kesilmiş ya da TCKN Servisi devre dışı kalmış olabilir."
            }
            return null
        }
    }

    companion object {
        private const val NAMESPACE = "http://tckimlik.nvi.gov.tr/WS"
        private const val URL = "https://tckimlik.nvi.gov.tr/Service/KPSPublic.asmx?WSDL"
        private const val SOAP_ACTION = "http://tckimlik.nvi.gov.tr/WS/TCKimlikNoDogrula"
        private const val METHOD_NAME = "TCKimlikNoDogrula"
    }
}