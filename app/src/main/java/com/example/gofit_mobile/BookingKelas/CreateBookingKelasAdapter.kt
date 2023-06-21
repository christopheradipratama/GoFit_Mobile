package com.example.gofit_mobile.BookingKelas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.R
import com.example.gofit_mobile.model.JadwalHarianModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CreateBookingKelasAdapter(private var historys: List<JadwalHarianModel>, context: Context):
    RecyclerView.Adapter<CreateBookingKelasAdapter.ViewHolder>() {
    private val context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_adapter_list_booking_kelas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = historys[position]
        val preferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)

        holder.tvTanggalJadwalHarian.text = data.TANGGAL_JADWAL_HARIAN
        holder.tvNamaKelas.text = data.NAMA_KELAS
        holder.tvNamaInstruktur.text = data.NAMA_INSTRUKTUR
        holder.tvKeteranganJadwalHarian.text = data.KETERANGAN_JADWAL_HARIAN
        holder.tvTarif.text = "Rp."+data.TARIF.toString()+"0"
        holder.tvHariJadwal.text = data.HARI_JADWAL

        holder.cvJadwalHarian.setOnClickListener {
            if(holder.tvKeteranganJadwalHarian.text == "Libur") {
                Toast.makeText(context,"Kelas ditiadakan", Toast.LENGTH_SHORT).show()
            }else {
                if(!(preferences.getString("booking",null).isNullOrEmpty())){
                    val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
                    materialAlertDialogBuilder.setTitle("Konfirmasi")
                        .setMessage("Apakah anda yakin ingin booking kelas ini?")
                        .setNegativeButton("Batal", null)
                        .setPositiveButton("Iya"){ _, _ ->
                            if (context is CreateBookingKelasActivity){
                                context.getSharedPreferences("session",Context.MODE_PRIVATE).getString("id",null)
                                    ?.let { it1 -> context.bookingClass(it1,data.ID_KELAS,data.TANGGAL_JADWAL_HARIAN) }
                            }
                        }
                        .show()
                }else if((preferences.getString("status",null).isNullOrEmpty())){
                    Toast.makeText(context,"Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show()
                }else{
//                    Toast.makeText(context,"Silahkan pilih menu booking", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return historys.size
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