package com.example.gofit_mobile.IzinInstruktur

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
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
import com.example.gofit_mobile.model.IzinInstrukturModel
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class IzinInstrukturActivity: AppCompatActivity() {
    private lateinit var SharedPreferences: SharedPreferences
    private var srIzinInstruktur: SwipeRefreshLayout? =null
    private var adapter: IzinInstrukturAdapter? = null
    private var svIzinInstruktur: SearchView? =null
    private var layoutLoading: LinearLayout? =null
    private var queue: RequestQueue? = null

    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_izin_instruktur)

        supportActionBar?.hide()

        queue =  Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srIzinInstruktur = findViewById(R.id.sr_izinInstruktur)

        SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        val id = SharedPreferences.getString("id", null)

        srIzinInstruktur?.setOnRefreshListener (SwipeRefreshLayout.OnRefreshListener {
            allIzinInstruktur(id)
        })

        val fabAdd = findViewById<Button>(R.id.btnAdd)
        fabAdd.setOnClickListener{
            val i = Intent(this@IzinInstrukturActivity, AddIzinInstruktur::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }

//        val rvIzin = findViewById<RecyclerView>(R.id.rv_izinInstruktur)
//        adapter = IzinInstrukturAdapter(ArrayList(), this)
//        rvIzin.layoutManager = LinearLayoutManager(this)
//        rvIzin.adapter = adapter
        allIzinInstruktur(id)
    }

    private fun allIzinInstruktur(id: String?){
        srIzinInstruktur!!.isRefreshing = true

        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, IzinInstrukturApi.GET_ALL_URL + id, Response.Listener{ response ->
                //   val gson = Gson()
                //   var mahasiswa : Array<Mahasiswa> = gson.fromJson(response, Array<Mahasiswa>::class.java)

//                var jo = JSONObject(response.toString())
//                var donaturArray = arrayListOf<Donatur>()
                var jo = JSONObject(response.toString())
                var instrukturArray = arrayListOf<IzinInstrukturModel>()
                var id : Int = jo.getJSONArray("data").length()

                for(i in 0 until id) {
                    var data = IzinInstrukturModel(
//                        jo.getJSONArray("data").getJSONObject(i).getInt("ID_IZIN"),
                        jo.getJSONArray("data").getJSONObject(i).getString("ID_INSTRUKTUR"),
                        jo.getJSONArray("data").getJSONObject(i).getString("NAMA_INSTRUKTUR_PENGGANTI"),
                        jo.getJSONArray("data").getJSONObject(i).getString("TANGGAL_IZIN_INSTRUKTUR"),
                        jo.getJSONArray("data").getJSONObject(i).getString("KETERANGAN_IZIN"),
                        jo.getJSONArray("data").getJSONObject(i).getString("TANGGAL_MELAKUKAN_IZIN"),
                        jo.getJSONArray("data").getJSONObject(i).getString("STATUS_IZIN"),
                        jo.getJSONArray("data").getJSONObject(i).getString("TANGGAL_KONFIRMASI_IZIN"),
                    )
                    instrukturArray.add(data)
                }

                var dataArray: Array<IzinInstrukturModel> = instrukturArray.toTypedArray()


                val layoutManager = LinearLayoutManager(this)
                val adapter : IzinInstrukturAdapter = IzinInstrukturAdapter(instrukturArray,this)
                val rvPermission : RecyclerView = findViewById(R.id.rv_izinInstruktur)

                rvPermission.layoutManager = layoutManager
                rvPermission.setHasFixedSize(true)
                rvPermission.adapter = adapter


//                izinAdapter!!.setIzinInstrukturList(dataArray)
//                izinAdapter!!.filter.filter(!!.query)

                srIzinInstruktur!!.isRefreshing = false

                if(!dataArray.isEmpty())
                //                 Toast.makeText(this@AddDonaturActivity, "Data berhasil diambil", Toast.LENGTH_SHORT).show()
                    FancyToast.makeText(this@IzinInstrukturActivity, "Berhasil Mendapatkan Data!", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show()

                else
                    FancyToast.makeText(this@IzinInstrukturActivity, "Data Tidak Ditemukan", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show()

            }, Response.ErrorListener{ error ->
                srIzinInstruktur!!.isRefreshing = true

                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(
                        this,
                        errors.getString("message"),
                        FancyToast.LENGTH_SHORT, FancyToast.INFO, false
                    ).show()
                }catch(e: Exception){
                    FancyToast.makeText(this, e.message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>{
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer " + SharedPreferences.getString("token",null);
                headers["Accept"] = "application/json"
                return headers
            }
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8;"
            }
        }
        queue!!.add(stringRequest)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(resultCode, resultCode, data)
//        if(requestCode == LAUNCH_ADD_ACTIVITY && resultCode == AppCompatActivity.RESULT_OK) allIzinInstruktur(id)
//    }

    private fun setLoading(isLoading: Boolean){
        if(isLoading){
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.INVISIBLE
        }
    }
}