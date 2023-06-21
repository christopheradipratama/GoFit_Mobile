package com.example.gofit_mobile.Instruktur

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.R
import com.example.gofit_mobile.model.HistoryAktivitasInstrukturModel
import java.text.DecimalFormat
import java.util.*

class HistoryAktivitasInstrukturAdapter(private var historyAktivitasInstrukturList: List<HistoryAktivitasInstrukturModel>, context: Context):
    RecyclerView.Adapter<HistoryAktivitasInstrukturAdapter.ViewHolder>()  {
    private val context: Context


    init {
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.activity_history_aktivitas_instruktur_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = historyAktivitasInstrukturList[position]
        var  formatter: java.text.NumberFormat? = DecimalFormat("#,###")

        holder.tvNamaKelas.text = "${data.NAMA_KELAS}"
        holder.tvTarif.text = "Rp.${formatter!!.format(data.TARIF)}"
        holder.tvHariJadwal.text = "Hari : ${data.HARI_JADWAL}"
        holder.tvTanggalJadwal.text = "Tanggal Booking: ${data.TANGGAL_JADWAL}"
        holder.tvWaktuJadwal.text = "Tanggal Booking: ${data.WAKTU_JADWAL}"
        holder.tvNamaInstruktur.text = "Tanggal Booking: ${data.NAMA_INSTRUKTUR}"
        holder.tvJamMulai.text = "Kelas mulai ${data.JAM_MULAI} - selesai ${data.JAM_SELESAI}"
        if(holder.tvJamMulai.text == "Kelas mulai null - selesai null"){
            holder.tvJamMulai.text == "Kelas Belum Dimulai"
        }

//        holder.iconDel.setOnClickListener {
//            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
//            materialAlertDialogBuilder.setTitle("Konfirmasi")
//                .setMessage("Apakah anda yakin ingin membatalkan booking kelas ini?")
//                .setNegativeButton("Batal", null)
//                .setPositiveButton("Iya"){ _, _ ->
////                    if (context is BookingKelasActivity){
////                        context.cancelBooking(data.KODE_BOOKING_KELAS)
////                    }
//                }
//                .show()
//        }
    }

    override fun getItemCount(): Int {
        return historyAktivitasInstrukturList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tvNamaKelas: TextView
        var tvTarif: TextView
        var tvHariJadwal: TextView
        var tvTanggalJadwal: TextView
        var tvWaktuJadwal: TextView
        var tvNamaInstruktur: TextView
        var tvJamMulai: TextView
        var cvHistoryAktivitasInstruktur: CardView

        init {
            tvNamaKelas = view.findViewById(R.id.tv_NamaKelas)
            tvTarif = view.findViewById(R.id.tv_TarifKelas)
            tvHariJadwal = view.findViewById(R.id.tv_HariJadwal)
            tvTanggalJadwal = view.findViewById(R.id.tv_TanggalJadwal)
            tvWaktuJadwal = view.findViewById(R.id.tv_WaktuJadwal)
            tvNamaInstruktur = view.findViewById(R.id.tv_NamaInstruktur)
            tvJamMulai = view.findViewById(R.id.tv_JamMulai)
            cvHistoryAktivitasInstruktur = view.findViewById(R.id.cv_HistoryAktivitasInstruktur)
        }

    }

}