package com.genius.gfiilapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfilFragment : Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val kuladi: TextView = view.findViewById(R.id.kuladi)
        val eposta: EditText = view.findViewById(R.id.eposta)
        val telno: EditText = view.findViewById(R.id.telno)
        val cinsiyet: TextView = view.findViewById(R.id.cinsiyet)
        val bakiye: TextView = view.findViewById(R.id.bakiye)
        val anaBakiye: TextView = view.findViewById(R.id.anaBakiye)
        val ödemeTalebi: Button = view.findViewById(R.id.ödemeTalebi)
        val guncelle: Button = view.findViewById(R.id.guncelle)
        //shared preferences veri alma kodu copy paste

        val prefences =
            requireActivity().getSharedPreferences("com.genius.gfiilapp", Context.MODE_PRIVATE)
        val uKadi = prefences.getString("u_kadi", "DEFAULT_VALUE").orEmpty()
        println(uKadi)


        ApiUtils.usersDAOInterface().profileDetay(uKadi)
            .enqueue(object : // gonderecegımız seylerı yaz
                Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {

                    if (response.body()?.u_kadi != null) {
                        kuladi.text = response.body()?.u_kadi
                        eposta.setText(response.body()?.u_mail)
                        telno.setText(response.body()?.u_telefon_no)
                        cinsiyet.text = response.body()?.u_cinsiyet
                        bakiye.text = response.body()?.u_bakiye
                        anaBakiye.text = response.body()?.ana_bakiye

                    } else {
                        Log.d("12345", "basarızzz giris")

                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    println(t.localizedMessage.toString())
                }

            })
        guncelle.setOnClickListener {
            ApiUtils.usersDAOInterface()
                .updateProfile(uKadi, eposta.text.toString(), telno.text.toString())
                .enqueue(object :
                    Callback<CRUDResponse> {
                    override fun onResponse(
                        call: Call<CRUDResponse>,
                        response: Response<CRUDResponse>
                    ) {

                        if (response.body()?.result_code == 1) {
                            Toast.makeText(
                                activity,
                                "BAŞARILIoldu",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<CRUDResponse>, t: Throwable) {
                        println(t.localizedMessage.toString())
                    }

                })
        }

        ödemeTalebi.setOnClickListener {
            val intent = Intent (getActivity(), OdemeActivity::class.java)
            getActivity()?.startActivity(intent)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

}

