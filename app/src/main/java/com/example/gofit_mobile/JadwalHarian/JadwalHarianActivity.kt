package com.example.gofit_mobile.JadwalHarian

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gofit_mobile.R
import com.example.gofit_mobile.api.IzinInstrukturApi
import com.example.gofit_mobile.api.JadwalHarianApi
import com.example.gofit_mobile.databinding.ActivityJadwalHarianBinding
import com.example.gofit_mobile.model.JadwalHarianModel
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class JadwalHarianActivity: AppCompatActivity() {
    private lateinit var binding: ActivityJadwalHarianBinding

    private var srJadwalHarian: SwipeRefreshLayout? =null
    private var adapter: JadwalHarianAdapter? = null
    private var queue: RequestQueue? = null

    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_jadwal_harian)
        binding = ActivityJadwalHarianBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        getSupportActionBar()?.hide()


        queue =  Volley.newRequestQueue(this)

        srJadwalHarian = findViewById(R.id.sr_JadwalHarian)

        srJadwalHarian?.setOnRefreshListener (SwipeRefreshLayout.OnRefreshListener { allJadwalHarian() })

//        val rvJadwalHarian = findViewById<RecyclerView>(R.id.rv_JadwalHarian)
//        adapter = JadwalHarianAdapter(ArrayList(), this)
//        rvJadwalHarian.layoutManager = LinearLayoutManager(this)
//        rvJadwalHarian.adapter = adapter
        allJadwalHarian()
    }

    private fun allJadwalHarian(){
//        srJadwalHarian!!.isRefreshing = true
//        val stringRequest: StringRequest = object :
//            StringRequest(Method.GET, JadwalHarianApi.GET_ALL_URL, Response.Listener { response ->
//                val gson = Gson()
//                val jsonObject = JSONObject(response)
//                val jsonData = jsonObject.getJSONArray("data")
//                val jadwalHarianModel : Array<JadwalHarianModel> = gson.fromJson(jsonData.toString(),Array<JadwalHarianModel>::class.java)
//
//                adapter!!.setJadwalHarianList(jadwalHarianModel)
//                srJadwalHarian!!.isRefreshing = false
//
//                if(jadwalHarianModel.isEmpty())
//                    Toast.makeText(this@JadwalHarianActivity, "Data Kosong!", Toast.LENGTH_SHORT ).show()
//                else
//                    Toast.makeText(this@JadwalHarianActivity, "Data Berhasil Diambil!", Toast.LENGTH_SHORT).show()
//            }, Response.ErrorListener { error ->
//                srJadwalHarian!!.isRefreshing = false
//                try{
//                    val responseBody =
//                        String(error.networkResponse.data, StandardCharsets.UTF_8)
//                    val errors = JSONObject(responseBody)
//                    Toast.makeText(
//                        this@JadwalHarianActivity,
//                        errors.getString("message"),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }catch (e: Exception){
//                    Toast.makeText(this@JadwalHarianActivity, e.message, Toast.LENGTH_SHORT).show()
//                }
//            }){
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Accept"] = "application/json"
//                return headers
//            }
//        }
//        queue!!.add(stringRequest)

        binding.srJadwalHarian.isRefreshing = true
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
                val adapter : JadwalHarianAdapter = JadwalHarianAdapter(schedule,this)
                val rvPermission : RecyclerView = findViewById(R.id.rv_JadwalHarian)

                rvPermission.layoutManager = layoutManager
                rvPermission.setHasFixedSize(true)
                rvPermission.adapter = adapter

                binding.srJadwalHarian.isRefreshing = false

                if (!data_array.isEmpty()) {
                    FancyToast.makeText(this@JadwalHarianActivity, "Berhasil Mendapatkan Data!", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show()
                }else {
//                    MotionToast.darkToast(
//                        context as Activity, "Notification Display!",
//                        "Data not found",
//                        MotionToastStyle.SUCCESS,
//                        MotionToast.GRAVITY_BOTTOM,
//                        MotionToast.LONG_DURATION,
//                        ResourcesCompat.getFont(
//                            context as Activity,
//                            www.sanju.motiontoast.R.font.helvetica_regular
//                        )
//                    )
                }

            }, Response.ErrorListener { error ->
                binding.srJadwalHarian.isRefreshing = true
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@JadwalHarianActivity, errors.getString("message"),
                        Toast.LENGTH_LONG
                    ).show();
                } catch (e: Exception){
//                    Toast.makeText(this@JanjiTemuActivity, e.message, Toast.LENGTH_SHORT).show()
//                    MotionToast.darkToast(
//                        context as Activity,"Notification Display!",
//                        e.message.toString(),
//                        MotionToastStyle.INFO,
//                        MotionToast.GRAVITY_BOTTOM,
//                        MotionToast.LONG_DURATION,
//                        ResourcesCompat.getFont(context as Activity, www.sanju.motiontoast.R.font.helvetica_regular))
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
//                headers["Authorization"] = "Bearer " + sharedPreferences.getString("token",null);
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(resultCode, resultCode, data)
        if(requestCode == LAUNCH_ADD_ACTIVITY && resultCode == AppCompatActivity.RESULT_OK) allJadwalHarian()
    }

}