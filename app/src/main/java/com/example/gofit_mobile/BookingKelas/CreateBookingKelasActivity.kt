package com.example.gofit_mobile.BookingKelas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gofit_mobile.R
import com.example.gofit_mobile.api.BookingKelasApi
import com.example.gofit_mobile.api.JadwalHarianApi
import com.example.gofit_mobile.databinding.ActivityListBookingKelasBinding
import com.example.gofit_mobile.model.BookingKelasListModel
import com.example.gofit_mobile.model.BookingKelasModel
import com.example.gofit_mobile.model.JadwalHarianModel
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class CreateBookingKelasActivity: AppCompatActivity() {
    private lateinit var binding: ActivityListBookingKelasBinding
    private var queue: RequestQueue? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBookingKelasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        val id = sharedPreferences.getString("id", null)
        queue = Volley.newRequestQueue(this)

        allData()
    }

    private fun allData() {
        binding.srListBookingKelas.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, JadwalHarianApi.GET_ALL_URL, Response.Listener { response ->
                var jo = JSONObject(response.toString())
                var schedule = arrayListOf<JadwalHarianModel>()
                var id : Int = jo.getJSONArray("data").length()

                for(i in 0 until id) {
                    var data = JadwalHarianModel(
                        jo.getJSONArray("data").getJSONObject(i).getString("TANGGAL_JADWAL_HARIAN"),
                        jo.getJSONArray("data").getJSONObject(i).getString("NAMA_INSTRUKTUR"),
                        jo.getJSONArray("data").getJSONObject(i).getString("NAMA_KELAS"),
                        jo.getJSONArray("data").getJSONObject(i).getInt("ID_KELAS"),
                        jo.getJSONArray("data").getJSONObject(i).getString("KETERANGAN_JADWAL_HARIAN"),
                        jo.getJSONArray("data").getJSONObject(i).getString("HARI_JADWAL"),
                        jo.getJSONArray("data").getJSONObject(i).getDouble("TARIF"),
                    )
                    schedule.add(data)
                }
                var data_array: Array<JadwalHarianModel> = schedule.toTypedArray()

                val layoutManager = LinearLayoutManager(this)
                val adapter : CreateBookingKelasAdapter = CreateBookingKelasAdapter(schedule,this)
                val rvPermission : RecyclerView = findViewById(R.id.rv_JadwalHarian)

                rvPermission.layoutManager = layoutManager
                rvPermission.setHasFixedSize(true)
                rvPermission.adapter = adapter

                binding.srListBookingKelas.isRefreshing = false

                if (!data_array.isEmpty()) {

                }else {

                }

            }, Response.ErrorListener { error ->
                binding.srListBookingKelas.isRefreshing = true
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)

                } catch (e: Exception){

                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Authorization"] = "Bearer " + sharedPreferences.getString("token",null);
                return headers
            }
        }
        queue!!.add(stringRequest)
    }




    fun bookingClass(id_member: String, id_kelas: Int, tanggal: String){
        val booking = BookingKelasListModel(
            id_member,
            id_kelas,
            tanggal,
        )

        val stringRequest: StringRequest =
            object : StringRequest(Request.Method.POST, BookingKelasApi.BOOKINGKELAS, Response.Listener { response ->
                val gson = Gson()
                var booking_data = gson.fromJson(response, BookingKelasListModel::class.java)

                var resJO = JSONObject(response.toString())
                val userobj = resJO.getJSONObject("data")

                if(booking_data!= null) {

//                    val intent = Intent(this@AddBookingKelas, HomeActivity::class.java)
//                    sharedPreferences.edit()
//                        .putString("booking",null)
//                        .apply()
//                    startActivity(intent)
                    FancyToast.makeText(this,"Berhasil melakukan booking kelas",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show()
                    val intent = Intent(this@CreateBookingKelasActivity, BookingKelasActivity::class.java)
                    startActivity(intent)
                }
                else {
                    FancyToast.makeText(this,"Tidak Berhasil melakukan booking kelass",
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,false).show()
                }
                return@Listener
            }, Response.ErrorListener { error ->
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(
                        this,
                        errors.getString("message"),
                        FancyToast.LENGTH_SHORT, FancyToast.INFO, false
                    ).show()
                }catch (e: java.lang.Exception) {
                    FancyToast.makeText(this, e.message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                }
            }) {
                @kotlin.jvm.Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    headers["Authorization"] = "Bearer " + sharedPreferences.getString("token",null);
                    return headers
                }

                @kotlin.jvm.Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(booking)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8;"
                }
            }
        queue!!.add(stringRequest)
    }
}