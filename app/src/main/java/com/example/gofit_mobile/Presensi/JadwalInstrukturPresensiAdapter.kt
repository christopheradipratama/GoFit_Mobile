package com.example.gofit_mobile.Presensi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.R
import com.example.gofit_mobile.model.JadwalInstrukturModel

class JadwalInstrukturPresensiAdapter(private var instruktur: List<JadwalInstrukturModel>, context: Context):
    RecyclerView.Adapter<JadwalInstrukturPresensiAdapter.ViewHolder>() {

    private val context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_adapter_jadwal_presensi_instruktur, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = instruktur[position]
        val preferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        holder.tvKelas.text = data.NAMA_KELAS
        holder.tvInstruktur.text = data.NAMA_INSTRUKTUR
        holder.tvTanggal.text = data.TANGGAL_JADWAL_HARIAN
        holder.tvHari.text = data.HARI_KELAS
        holder.tvKeterangan.text = data.KETERANGAN_JADWAL_HARIAN
        holder.tvJam.text = "${data.JAM_MULAI} - ${data.JAM_SELESAI}"
        if(holder.tvJam.text == "null - null"){
            holder.tvJam.text = "Kelas Belum Dimulai"
        }else if(holder.tvJam.text == "${data.JAM_MULAI} - null"){
            holder.tvJam.text = "${data.JAM_MULAI} - Selesai"
        }

        holder.cvSchedule.setOnClickListener {
            if (context is JadwalInstrukturPresensiActivity){
                val intent = Intent(context, BookingKelasPresensiActivity::class.java)
                preferences.edit()
                    .putString("TANGGAL_JADWAL_HARIAN",data.TANGGAL_JADWAL_HARIAN)
                    .apply()
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return instruktur.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tvKelas: TextView
        var tvInstruktur: TextView
        var tvKeterangan: TextView
        var tvHari: TextView
        var tvTanggal: TextView
        var tvJam: TextView
        var cvSchedule: CardView

        init {
            tvKelas = view.findViewById(R.id.tv_NamaKelas)
            tvInstruktur = view.findViewById(R.id.tv_NamaInstruktur)
            tvKeterangan = view.findViewById(R.id.tv_KeteranganJadwalHarian)
            tvHari = view.findViewById(R.id.tv_HariJadwal)
            tvTanggal = view.findViewById(R.id.tv_TanggalJadwalHarian)
            tvJam = view.findViewById(R.id.tv_Jam)
            cvSchedule = view.findViewById(R.id.cv_JadwalPresensiInstruktur)
        }
    }
}