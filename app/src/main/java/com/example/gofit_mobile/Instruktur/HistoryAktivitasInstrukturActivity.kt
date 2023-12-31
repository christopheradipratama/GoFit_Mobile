package com.example.gofit_mobile.Instruktur

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gofit_mobile.BookingKelas.BookingKelasAdapter
import com.example.gofit_mobile.R
import com.example.gofit_mobile.api.BookingKelasApi
import com.example.gofit_mobile.api.HistoryAktivitasInstrukturApi
import com.example.gofit_mobile.databinding.ActivityBookingKelasBinding
import com.example.gofit_mobile.databinding.ActivityHistoryAktivitasInstrukturBinding
import com.example.gofit_mobile.model.BookingKelasModel
import com.example.gofit_mobile.model.HistoryAktivitasInstrukturModel
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class HistoryAktivitasInstrukturActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityHistoryAktivitasInstrukturBinding
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryAktivitasInstrukturBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()
        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        val id = sharedPreferences.getString("id",null)
        queue = Volley.newRequestQueue(this)

        binding.srHistoryAktivitasInstruktur.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener{
            if (id != null) {
                allData(id)
            }
        })
        if (id != null) {
            allData(id)
        }
    }

    private fun allData(id: String) {
        binding.srHistoryAktivitasInstruktur.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, HistoryAktivitasInstrukturApi.GETDATAHISTORY + id, Response.Listener { response ->
                var jo = JSONObject(response.toString())
                var history = arrayListOf<HistoryAktivitasInstrukturModel>()
                var id : Int = jo.getJSONArray("data").length()

                for(i in 0 until id) {
                    var data = HistoryAktivitasInstrukturModel(
                        jo.getJSONArray("data").getJSONObject(i).getString("NAMA_KELAS"),
                        jo.getJSONArray("data").getJSONObject(i).getInt("TARIF"),
                        jo.getJSONArray("data").getJSONObject(i).getString("HARI_JADWAL"),
                        jo.getJSONArray("data").getJSONObject(i).getString("TANGGAL_JADWAL"),
                        jo.getJSONArray("data").getJSONObject(i).getString("WAKTU_JADWAL"),
                        jo.getJSONArray("data").getJSONObject(i).getString("NAMA_INSTRUKTUR"),
                        jo.getJSONArray("data").getJSONObject(i).getString("JAM_MULAI"),
                        jo.getJSONArray("data").getJSONObject(i).getString("JAM_SELESAI"),
                    )
                    history.add(data)
                }
                var data_array: Array<HistoryAktivitasInstrukturModel> = history.toTypedArray()

                val layoutManager = LinearLayoutManager(this)
                val adapter : HistoryAktivitasInstrukturAdapter = HistoryAktivitasInstrukturAdapter(history,this)
                val rvPermission : RecyclerView = findViewById(R.id.rv_HistoryAktivitasInstruktur)

                rvPermission.layoutManager = layoutManager
                rvPermission.setHasFixedSize(true)
                rvPermission.adapter = adapter

                binding.srHistoryAktivitasInstruktur.isRefreshing = false

                if (!data_array.isEmpty()) {
                    FancyToast.makeText(this@HistoryAktivitasInstrukturActivity, "Berhasil Mendapatkan Data!", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show()
                }else {
                    FancyToast.makeText(this@HistoryAktivitasInstrukturActivity, "Data Tidak Ditemukan", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show()
                }

            }, Response.ErrorListener { error ->
                binding.srHistoryAktivitasInstruktur.isRefreshing = true
                try {

                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(
                        this,
                        errors.getString("message"),
                        FancyToast.LENGTH_SHORT, FancyToast.INFO, false
                    ).show()

                    binding.srHistoryAktivitasInstruktur.isRefreshing = false
                } catch (e: Exception){
                    FancyToast.makeText(this, e.message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                    binding.srHistoryAktivitasInstruktur.isRefreshing = false
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
}