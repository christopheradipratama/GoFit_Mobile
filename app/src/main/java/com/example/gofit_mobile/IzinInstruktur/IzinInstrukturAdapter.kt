package com.example.gofit_mobile.IzinInstruktur

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.R
import com.example.gofit_mobile.model.IzinInstrukturModel
import com.shashank.sony.fancytoastlib.FancyToast
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class IzinInstrukturAdapter(private var izinInstrukturList: List<IzinInstrukturModel>, context: Context) :
    RecyclerView.Adapter<IzinInstrukturAdapter.ViewHolder>(){

    private var filteredizinInstrukturList: MutableList<IzinInstrukturModel>
    private val context: Context

    init{
        filteredizinInstrukturList = ArrayList(izinInstrukturList)
        this.context=context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_adapter_izin_instruktur, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredizinInstrukturList.size
    }

    fun setIzinInstrukturList(izinInstrukturList: Array<IzinInstrukturModel>){
        this.izinInstrukturList = izinInstrukturList.toList()
        filteredizinInstrukturList = izinInstrukturList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val izinInstruktur = filteredizinInstrukturList[position]
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

//        holder.tvIdIzinInstruktur.text = izinInstruktur.ID_IZIN_INSTRUKTUR.toString()
//        holder.tvNamaInstruktur.text = izinInstruktur.ID_INSTRUKTUR.toString()
        holder.tvTanggalIzinInstruktur.text = izinInstruktur.TANGGAL_IZIN_INSTRUKTUR.format(formatter)
        holder.tvKeteranganIzin.text = izinInstruktur.KETERANGAN_IZIN
        holder.tvTanggalMelakukanIzin.text = izinInstruktur.TANGGAL_MELAKUKAN_IZIN
        holder.tvStatusIzin.text = "${izinInstruktur.STATUS_IZIN} pada ${izinInstruktur.TANGGAL_KONFIRMASI_IZIN}"
        if(holder.tvStatusIzin.text == "null pada null"){
            holder.tvStatusIzin.text = "Belum Dikonfirmasi"
        }
//        holder.tvTanggalKonfirmasiIzin.text = izinInstruktur.TANGGAL_KONFIRMASI_IZIN

        holder.cvIzinInstruktur.setOnClickListener{
            FancyToast.makeText(context, izinInstruktur.STATUS_IZIN, FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show()
        }
    }

//    override fun getFilter(): Filter {
//        return object : Filter(){
//            override fun performFiltering(charSequence: CharSequence): FilterResults {
//                val charSequenceString = charSequence.toString()
//                val filteredd: MutableList<IzinInstrukturModel> = mutableListOf()
//                val filtered : MutableList<IzinInstrukturModel> = java.util.ArrayList()
//                if(charSequenceString.isEmpty()){
//                    filtered.addAll(izinInstrukturList)
//                }else{
//                    for(izinInstruktur in izinInstrukturList){
//                        val idString = izinInstruktur.ID_IZIN_INSTRUKTUR.toString()
//
////                        if(izinInstruktur.ID_IZIN_INSTRUKTUR.lowercase(Locale.getDefault())
////                                .contains(charSequenceString.lowercase(Locale.getDefault()))
////                        ) filteredd.add(izinInstruktur)
//
//                        if (idString.lowercase(Locale.getDefault()).contains(charSequenceString.lowercase(Locale.getDefault()))) {
//                            filteredd.add(izinInstruktur)
//                        }
//                    }
//                }
//                val filterResults = FilterResults()
//                filterResults.values = filtered
//                return filterResults
//            }
//
//            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
//                filteredizinInstrukturList.clear()
//                filteredizinInstrukturList.addAll((filterResults.values as List<IzinInstrukturModel>))
//                notifyDataSetChanged()
//            }
//        }
//    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

//        var tvIdIzinInstruktur: TextView
//        var tvNamaInstruktur: TextView
        var tvTanggalIzinInstruktur: TextView
        var tvTanggalMelakukanIzin: TextView
        var tvStatusIzin: TextView
        var tvKeteranganIzin: TextView
//        var tvTanggalKonfirmasiIzin: TextView
        var cvIzinInstruktur: CardView

        init{
//            tvIdIzinInstruktur = itemView.findViewById(R.id.tv_IdIzinInstruktur)
//            tvNamaInstruktur = itemView.findViewById(R.id.tv_NamaInstruktur)
            tvTanggalIzinInstruktur = itemView.findViewById(R.id.tvTanggalIzinInstruktur)
            tvKeteranganIzin = itemView.findViewById(R.id.tvKeteranganIzin)
            tvTanggalMelakukanIzin = itemView.findViewById(R.id.tvTanggalMelakukanIzin)
            tvStatusIzin = itemView.findViewById(R.id.tvStatusIzin)
//            tvTanggalKonfirmasiIzin = itemView.findViewById(R.id.tv_TanggalKonfirmasiIzin)
            cvIzinInstruktur = itemView.findViewById(R.id.cv_IzinInstruktur)
        }
    }
}