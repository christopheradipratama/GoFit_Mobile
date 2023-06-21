package com.example.gofit_mobile.Presensi

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.gofit_mobile.R
import com.example.gofit_mobile.model.BookingKelasPresensiModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONObject

class BookingKelasPresensiAdapter(private var historys: List<BookingKelasPresensiModel>, context: Context):
    RecyclerView.Adapter<BookingKelasPresensiAdapter.ViewHolder>() {

    private val context: Context

    companion object{
        private val STATUS = arrayOf(
            "Hadir",
            "Tidak Hadir",
        )
    }

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingKelasPresensiAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_adapter_jadwal_presensi_member, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingKelasPresensiAdapter.ViewHolder, position: Int) {
        val data = historys[position]
        val preferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)

        setExposedDropDownMenu(holder)

        holder.tvKodeBooking.text = "${data.KODE_BOOKING_KELAS} - ${data.NAMA_KELAS}"
        holder.tvTanggalBook.text = "ID Member: ${data.TANGGAL_JADWAL_HARIAN}"
        holder.tvTanggalMelakukan.text = "Nama Member: ${data.TANGGAL_MELAKUKAN_BOOKING}"
        holder.tvStatusBooking.text = "${data.STATUS_PRESENSI_KELAS} - ${data.WAKTU_PRESENSI_KELAS}"
        if(holder.tvStatusBooking.text == "null - null"){
            holder.tvStatusBooking.text = "Belum dikonfirmasi/Tidak Hadir"
        }

        holder.iconCheck.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin konfirmasi presensi member ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Iya"){ _, _ ->
                    if (context is BookingKelasPresensiActivity){
                        context.update(data.KODE_BOOKING_KELAS,holder.edStatus.text.toString())
                    }
                }
                .show()
        }
    }

    override fun getItemCount(): Int {
        return historys.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tvKodeBooking: TextView
        var tvTanggalBook: TextView
        var tvTanggalMelakukan: TextView
        var tvStatusBooking: TextView
        var iconCheck: ImageButton
        var cvBook: CardView
        var edStatus: AutoCompleteTextView

        init {
            tvKodeBooking = view.findViewById(R.id.tv_KodeBooking)
            tvTanggalBook = view.findViewById(R.id.tv_TanggalBookingKelas)
            tvTanggalMelakukan = view.findViewById(R.id.tv_TanggalMelakukanBooking)
            tvStatusBooking = view.findViewById(R.id.tv_konfirmasi)
            iconCheck = view.findViewById(R.id.btnCheck)
            cvBook = view.findViewById(R.id.cv_JadwalPresensiMember)
            edStatus = view.findViewById(R.id.et_status)
        }

    }

    fun setExposedDropDownMenu(holder: BookingKelasPresensiAdapter.ViewHolder){
        val adapterslot: ArrayAdapter<String> = ArrayAdapter<String>(context as BookingKelasPresensiActivity,
            R.layout.item_adapter_list_dropdown, BookingKelasPresensiAdapter.STATUS
        )
        holder.edStatus.setAdapter(adapterslot)
    }
}