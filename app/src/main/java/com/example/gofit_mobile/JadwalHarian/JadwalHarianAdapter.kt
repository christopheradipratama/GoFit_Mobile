package com.example.gofit_mobile.JadwalHarian

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.R
import com.example.gofit_mobile.model.JadwalHarianModel
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class JadwalHarianAdapter (private var jadwalHarianList: List<JadwalHarianModel>, context: Context) :
    RecyclerView.Adapter<JadwalHarianAdapter.ViewHolder>(){

    private var filteredjadwalHarianList: MutableList<JadwalHarianModel>
    private val context: Context

    init{
        filteredjadwalHarianList = ArrayList(jadwalHarianList)
        this.context=context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_adapter_jadwal_harian, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredjadwalHarianList.size
    }

    fun setJadwalHarianList(jadwalHarianList: Array<JadwalHarianModel>){
        this.jadwalHarianList = jadwalHarianList.toList()
        filteredjadwalHarianList = jadwalHarianList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val JadwalHarian = jadwalHarianList[position]
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        holder.tvTanggalJadwalHarian.text = JadwalHarian.TANGGAL_JADWAL_HARIAN
        holder.tvNamaKelas.text = JadwalHarian.NAMA_KELAS
        holder.tvNamaInstruktur.text = JadwalHarian.NAMA_INSTRUKTUR
        holder.tvKeteranganJadwalHarian.text = JadwalHarian.KETERANGAN_JADWAL_HARIAN
        holder.tvTarif.text = "Rp."+JadwalHarian.TARIF.toString()+"0"
        holder.tvHariJadwal.text = JadwalHarian.HARI_JADWAL

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var tvNamaKelas: TextView
        var tvNamaInstruktur: TextView
        var tvKeteranganJadwalHarian: TextView
        var tvTarif: TextView
        var tvHariJadwal: TextView
        var tvTanggalJadwalHarian: TextView
        var cvJadwalHarian: CardView

        init{
            tvNamaKelas = itemView.findViewById(R.id.tv_NamaKelas)
            tvNamaInstruktur = itemView.findViewById(R.id.tv_NamaInstruktur)
            tvKeteranganJadwalHarian = itemView.findViewById(R.id.tv_KeteranganJadwalHarian)
            tvTarif = itemView.findViewById(R.id.tv_Tarif)
            tvHariJadwal = itemView.findViewById(R.id.tv_HariJadwal)
            tvTanggalJadwalHarian = itemView.findViewById(R.id.tv_TanggalJadwalHarian)
            cvJadwalHarian = itemView.findViewById(R.id.cv_JadwalHarian)
        }
    }
}