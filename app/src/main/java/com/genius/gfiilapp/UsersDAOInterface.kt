package com.genius.gfiilapp

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UsersDAOInterface {

    @POST("/giris.php")
    @FormUrlEncoded
    fun girisYap(
        @Field("u_kadi") uKadi: String,
        @Field("u_sifre") uSifre: String
    ): Call<CRUDResponse>

    @POST("/uRegister.php")
    @FormUrlEncoded
    fun kayitOl(

        @Field("u_kadi") uKadi: String,
        @Field("u_mail") uMail:String,
        @Field("u_sifre") uSifre: String,
        @Field("u_cinsiyet") uCinsiyet: String,
        @Field("u_telefon_no") uTelNo: String,
        @Field("u_refcode") uRefcode: String,
        @Field("tc_no") tcNo: String,
        @Field("adi") adi: String,
        @Field("soyadi") soyadi: String,
        @Field("dogum_tarihi") dogumTarihi: String

    ): Call<CRUDResponse>

    @POST("/mobileServices/profileDetay.php")
    @FormUrlEncoded
    fun profileDetay(

        @Field("u_kadi") uKadi: String
    ): Call<UserResponse>

    @POST("/mobileServices/updateProfile.php")
    @FormUrlEncoded
    fun updateProfile(

        @Field("u_kadi") uKadi: String,
        @Field("u_mail") uMail: String,
    @Field("u_telefon_no") uTelefon: String
    ): Call<CRUDResponse>

    @POST("/mobileServices/smsDogrula.php")
    @FormUrlEncoded
    fun smsDogrula(

        @Field("u_kadi") uKadi: String,
        @Field("smsCode") smsCode: String,
        @Field("u_telefon_no") uTelefon: String
    ): Call<CRUDResponse>

    @POST("/mobileServices/tekrarSmsGonder.php")
    @FormUrlEncoded
    fun tekrarSmsGonder(

        @Field("u_kadi") uKadi: String,
        @Field("u_telefon_no") uTelefon: String
    ): Call<CRUDResponse>

    @GET("/mobileServices/getGorevler.php")
    fun getGorevler(): Call<List<Gorevler>>

    @GET("/mobileServices/getPaketler.php")
    fun getPaketler(): Call<List<Paketler>>


    @POST("/mobileServices/gorevTamamla.php")
    @FormUrlEncoded
    fun gorevTamamla(
        @Field("u_kadi") u_kadi: String,
        @Field("gl_g_id") gl_g_id: Int,
        @Field("g_button_url") g_button_url: String,
        @Field("grv_tamamla") grv_tamamla: Int,
        @Field("gl_kanit") gl_kanit:String
    ): Call<CRUDResponse>

    @POST("/mobileServices/bilgiKaydet.php")
    fun saveProfile(
        @Body param:RequestBody
    ): Call<CRUDResponse>

    @POST("/mobileServices/tamamlananGorevler.php")
    @FormUrlEncoded
    fun tamamlananGorevler(

        @Field("u_kadi") u_kadi: String,
    ):  Call<List<TamamlananGörevler>>

    @POST("/mobileServices/tamamlananOdemeler.php")
    @FormUrlEncoded
    fun tamamlananOdemeler(

        @Field("u_kadi") u_kadi: String,
    ):  Call<List<TamamlananOdemeler>>


    @POST("/mobileServices/cekimTalebiVer.php")
    fun cekimTalebiVer(
        @Body param:RequestBody
    ): Call<CRUDResponse>

    @FormUrlEncoded
    @POST("/mobileServices/anaBakiyeAktar.php")
    fun anaBakiyeAktar(
        @Field("u_kadi") u_kadi: String,
        @Field("ct_bakiye") ct_bakiye: String,
        @Field("ct_iban") ct_iban: String

    ): Call<CRUDResponse>

    @POST("/mobileServices/gunlukKazanc.php")
    @FormUrlEncoded
    fun gunclukKazanc(

        @Field("u_kadi") uKadi: String
    ): Call<CRUDResponse>









}