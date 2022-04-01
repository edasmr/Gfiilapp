package com.genius.gfiilapp

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException


class SettingFragment : Fragment() {
    private lateinit var profile_image:ImageView
    lateinit var bitmapImage:Bitmap
    lateinit var imageUri:Uri

    var items = arrayOf("Evet", "Hayır")
    private lateinit var autoCompleteTxt: AutoCompleteTextView
    var adapterItems: ArrayAdapter<String>? = null

    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profile_image = view.findViewById(R.id.profil_image)
        val sehir: EditText = view.findViewById(R.id.sehir)
        val yas: EditText = view.findViewById(R.id.yas)
        val meslek: EditText = view.findViewById(R.id.meslek)
        val instagram: EditText = view.findViewById(R.id.instagram)
        val adres: EditText = view.findViewById(R.id.adres)
     //   val smsBildirimi: EditText = view.findViewById(R.id.smsBildirimi)
        val kaydet: Button = view.findViewById(R.id.kaydet)
        val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        autoCompleteTxt = view.findViewById(R.id.auto_complete_txt)
        adapterItems = ArrayAdapter(requireActivity(), R.layout.list_item, items)
        autoCompleteTxt.setAdapter(adapterItems)
        autoCompleteTxt.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position).toString()
          //  Toast.makeText(activity, "Item: $item", Toast.LENGTH_SHORT).show()
        })
        val prefences =
            requireActivity().getSharedPreferences("com.genius.gfiilapp", Context.MODE_PRIVATE)
        val uKadi = prefences.getString("u_kadi", "DEFAULT_VALUE").orEmpty()
        println(uKadi)

       profile_image.setOnClickListener {

           val photoPickerIntent = Intent(Intent.ACTION_PICK)
           photoPickerIntent.type = "image/*"
           startActivityForResult(photoPickerIntent, 1000)

       }




        kaydet.setOnClickListener {
         //   val file=File(getPath(imageUri))
        //    if(!file.exists())
        //        Log.i("test", "file exists")
        //    val fbody: RequestBody = RequestBody.create(
        //        MediaType.parse("image/*"),
          //      file
         //   )

            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        //    builder.addPart(fbody)
            builder.addFormDataPart("u_kadi", uKadi)
            builder.addFormDataPart("u_sehir", sehir.text.toString())
            builder.addFormDataPart("u_yas", yas.text.toString())
            builder.addFormDataPart("u_meslek", meslek.text.toString())
            builder.addFormDataPart("u_instagram", instagram.text.toString())
            builder.addFormDataPart("u_site", adres.text.toString())
            builder.addFormDataPart("sms_tercih", autoCompleteTxt.text.toString())


            ApiUtils.usersDAOInterface()
                .saveProfile(builder.build())
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
                        } else {
                            Toast.makeText(activity, response.body()?.response, Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<CRUDResponse>, t: Throwable) {
                        println(t.localizedMessage.toString())
                    }

                })
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                        //super method removed

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === RESULT_OK) when (requestCode) {
            1000 -> {
                imageUri = Uri.parse(data?.data.toString())

                try {
                     bitmapImage =
                        MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                    profile_image.setImageBitmap(bitmapImage)
                } catch (e: IOException) {
                    Log.i("TAG", "Some exception $e")
                }
            }
        }

    }

    fun getPath(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor =
            requireActivity().getContentResolver().query(uri, projection, null, null, null) ?: return null
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s: String = cursor.getString(column_index)
        cursor.close()
        return s
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

}