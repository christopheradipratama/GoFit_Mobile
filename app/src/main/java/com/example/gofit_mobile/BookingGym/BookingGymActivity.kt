package com.example.gofit_mobile.BookingGym

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gofit_mobile.HomeActivity
import com.example.gofit_mobile.JadwalHarian.JadwalHarianAdapter
import com.example.gofit_mobile.R
import com.example.gofit_mobile.api.BookingGymApi
import com.example.gofit_mobile.api.JadwalHarianApi
import com.example.gofit_mobile.databinding.ActivityBookingGymBinding
import com.example.gofit_mobile.databinding.ActivityJadwalHarianBinding
import com.example.gofit_mobile.model.BookingGymModel
import com.example.gofit_mobile.model.JadwalHarianModel
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.nio.charset.StandardCharsets

class BookingGymActivity: AppCompatActivity() {
    private lateinit var binding: ActivityBookingGymBinding
    private lateinit var sharedPreferences: SharedPreferences

    private var srBookingGym: SwipeRefreshLayout? =null
    private var adapter: BookingGymAdapter? = null
    private var queue: RequestQueue? = null

    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_jadwal_harian)
        binding = ActivityBookingGymBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        val id = sharedPreferences.getString("id",null)

        getSupportActionBar()?.hide()


        queue =  Volley.newRequestQueue(this)

        srBookingGym = findViewById(R.id.sr_BookingGym)

        srBookingGym?.setOnRefreshListener (SwipeRefreshLayout.OnRefreshListener {
            if (id != null) {
                allBookingGym(id)
            }
        })

        if (id != null) {
            allBookingGym(id)
        }

        binding.btnCreateBookingGym.setOnClickListener {
            val move = Intent(this@BookingGymActivity, CreateBookingGymActivity::class.java)
            startActivity(move)
        }
    }

    private fun allBookingGym(id: String){

        binding.srBookingGym.isRefreshing = true

        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, BookingGymApi.GET_ALL_URL + id, Response.Listener { response ->
                var jo = JSONObject(response.toString())
                var schedule = arrayListOf<BookingGymModel>()
                var id : Int = jo.getJSONArray("data").length()

                for(i in 0 until id) {
                    var data = BookingGymModel(
                        jo.getJSONArray("data").getJSONObject(i).getString("KODE_BOOKING_GYM"),
                        jo.getJSONArray("data").getJSONObject(i).getString("ID_MEMBER"),
                        jo.getJSONArray("data").getJSONObject(i).getString("TANGGAL_BOOKING_GYM"),
                        jo.getJSONArray("data").getJSONObject(i).getString("TANGGAL_MELAKUKAN_BOOKING"),
                        jo.getJSONArray("data").getJSONObject(i).getString("SLOT_WAKTU_GYM"),
                        jo.getJSONArray("data").getJSONObject(i).getString("STATUS_PRESENSI_GYM"),
                        jo.getJSONArray("data").getJSONObject(i).getString("WAKTU_PRESENSI_GYM"),
                    )
                    schedule.add(data)
                }
                var data_array: Array<BookingGymModel> = schedule.toTypedArray()

                val layoutManager = LinearLayoutManager(this)
                val adapter : BookingGymAdapter = BookingGymAdapter(schedule,this)
                val rvPermission : RecyclerView = findViewById(R.id.rv_BookingGym)

                rvPermission.layoutManager = layoutManager
                rvPermission.setHasFixedSize(true)
                rvPermission.adapter = adapter

                binding.srBookingGym.isRefreshing = false

                if (!data_array.isEmpty()) {
                    FancyToast.makeText(this@BookingGymActivity, "Berhasil Mendapatkan Data!", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show()
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
                binding.srBookingGym.isRefreshing = true
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
//                    Toast.makeText(this@JanjiTemuActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
//                    MotionToast.darkToast(
//                        context as Activity,"Notification Display!",
//                        errors.getString("message"),
//                        MotionToastStyle.INFO,
//                        MotionToast.GRAVITY_BOTTOM,
//                        MotionToast.LONG_DURATION,
//                        ResourcesCompat.getFont(context as Activity, www.sanju.motiontoast.R.font.helvetica_regular))
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

    fun cancelBookingGym(id: String) {
//        binding.srBooking.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, BookingGymApi.DELETE_URL + id, Response.Listener { response ->
                var jo = JSONObject(response.toString())
//                var history = arrayListOf<HistoryBookingClass>()

                if (jo.getJSONObject("data") != null) {
                    Toast.makeText(this@BookingGymActivity, "Berhasil Dihapus!", Toast.LENGTH_SHORT).show()
//                    MotionToast.darkToast(
//                        this, "Notification Booking!",
//                        "Succesfully cancel booking gym",
//                        MotionToastStyle.SUCCESS,
//                        MotionToast.GRAVITY_BOTTOM,
//                        MotionToast.LONG_DURATION,
//                        ResourcesCompat.getFont(
//                            this,
//                            www.sanju.motiontoast.R.font.helvetica_regular
//                        )
//                    )
                    val intent = Intent(this@BookingGymActivity, BookingGymActivity::class.java)
                    startActivity(intent)
                }else {
                    MotionToast.darkToast(
                        this, "Notification Booking!",
                        "Failed cancel booking gym",
                        MotionToastStyle.SUCCESS,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(
                            this,
                            www.sanju.motiontoast.R.font.helvetica_regular
                        )
                    )
                }

            }, Response.ErrorListener { error ->
                binding.srBookingGym.isRefreshing = true
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
//                    Toast.makeText(this@JanjiTemuActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
                    MotionToast.darkToast(
                        this,"Notification Booking!",
                        errors.getString("message"),
                        MotionToastStyle.INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
                    binding.srBookingGym.isRefreshing = false
                } catch (e: Exception){
//                    Toast.makeText(this@JanjiTemuActivity, e.message, Toast.LENGTH_SHORT).show()
                    MotionToast.darkToast(
                        this,"Notification Booking!",
                        e.message.toString(),
                        MotionToastStyle.INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
                    binding.srBookingGym.isRefreshing = false
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



//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(resultCode, resultCode, data)
//        if(requestCode == LAUNCH_ADD_ACTIVITY && resultCode == AppCompatActivity.RESULT_OK) allBookingGym(id)
//    }
}