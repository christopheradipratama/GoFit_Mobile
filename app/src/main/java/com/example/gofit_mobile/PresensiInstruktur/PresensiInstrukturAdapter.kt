package com.example.gofit_mobile.PresensiInstruktur

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.R
import com.example.gofit_mobile.model.PresensiInstrukturModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PresensiInstrukturAdapter(private var instructors: List<PresensiInstrukturModel>, context: Context): RecyclerView.Adapter<PresensiInstrukturAdapter.ViewHolder>() {
    private val context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresensiInstrukturAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_adapter_presensi_instruktur, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PresensiInstrukturAdapter.ViewHolder, position: Int) {
        val data = instructors[position]
        holder.tvKelas.text = data.NAMA_KELAS
        holder.tvInstruktur.text = data.NAMA_INSTRUKTUR
        holder.tvTanggal.text = data.TANGGAL_JADWAL_HARIAN
        holder.tvHari.text = data.HARI_JADWAL
        holder.tvKeterangan.text = data.KETERANGAN_JADWAL_HARIAN

        holder.btnStart.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin update jam mulai kelas?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Iya"){ _, _ ->
                    if (context is PresensiInstrukturActivity){
                        context.store(data.ID_INSTRUKTUR, data.TANGGAL_JADWAL_HARIAN)
                    }
                }
                .show()
        }
    }

    override fun getItemCount(): Int {
        return instructors.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tvKelas: TextView
        var tvInstruktur: TextView
        var tvKeterangan: TextView
        var tvHari: TextView
        var tvTanggal: TextView
        var cvSchedule: CardView
        var btnStart: ImageButton


        init {
            tvKelas = view.findViewById(R.id.tv_Kelas)
            tvInstruktur = view.findViewById(R.id.tv_Instruktur)
            tvKeterangan = view.findViewById(R.id.tv_Keterangan)
            tvHari = view.findViewById(R.id.tv_Hari)
            tvTanggal = view.findViewById(R.id.tv_TanggalKelas)
            cvSchedule = view.findViewById(R.id.cv_PresensiInstruktur)
            btnStart = view.findViewById(R.id.btnStart)
        }

    }
}