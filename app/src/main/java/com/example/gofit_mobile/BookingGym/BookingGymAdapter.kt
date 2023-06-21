package com.example.gofit_mobile.BookingGym

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.JadwalHarian.JadwalHarianAdapter
import com.example.gofit_mobile.R
import com.example.gofit_mobile.model.BookingGymModel
import com.example.gofit_mobile.model.JadwalHarianModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers.data
import java.time.format.DateTimeFormatter

class BookingGymAdapter(private var bookingGymList: List<BookingGymModel>, context: Context) :
    RecyclerView.Adapter<BookingGymAdapter.ViewHolder>() {

    private var filteredbookingGymList: MutableList<BookingGymModel>
    private val context: Context

    init{
        filteredbookingGymList = ArrayList(bookingGymList)
        this.context=context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_adapter_booking_gym, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredbookingGymList.size
    }

    fun setBookingGymList(bookingGymList: Array<BookingGymModel>){
        this.bookingGymList = bookingGymList.toList()
        filteredbookingGymList = bookingGymList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val BookingGym = bookingGymList[position]

        holder.tvKodeBookingGym.text = BookingGym.KODE_BOOKING_GYM
        holder.tvTanggalBookingGym.text = BookingGym.TANGGAL_BOOKING_GYM
        holder.tvTanggalMelakukanBooking.text = BookingGym.TANGGAL_MELAKUKAN_BOOKING
        holder.tvSlotWaktuGym.text = BookingGym.SLOT_WAKTU_GYM
        holder.tvStatusPresensiGym.text = BookingGym.STATUS_PRESENSI_GYM
        holder.tvWaktuPresensiGym.text = BookingGym.WAKTU_PRESENSI_GYM

        holder.ibCancel.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin membatalkan booking gym ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Iya"){ _, _ ->
                    if (context is BookingGymActivity){
                        context.cancelBookingGym(BookingGym.KODE_BOOKING_GYM)
                    }
                }
                .show()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var tvKodeBookingGym: TextView
        var tvTanggalBookingGym: TextView
        var tvTanggalMelakukanBooking: TextView
        var tvSlotWaktuGym: TextView
        var tvStatusPresensiGym: TextView
        var tvWaktuPresensiGym: TextView
        var ibCancel: ImageButton
        var cvBookingGym: CardView

        init{
            tvKodeBookingGym = itemView.findViewById(R.id.tv_KodeBookingKelas)
//            tvIdMember = itemView.findViewById(R.id.tv_Id)
            tvTanggalBookingGym = itemView.findViewById(R.id.tv_TanggalBookingGym)
            tvTanggalMelakukanBooking = itemView.findViewById(R.id.tv_TanggalMelakukanBooking)
            tvSlotWaktuGym = itemView.findViewById(R.id.tv_SlotWaktuGym)
            tvStatusPresensiGym = itemView.findViewById(R.id.tv_StatusPresensiGym)
            tvWaktuPresensiGym = itemView.findViewById(R.id.tv_WaktuPresensiGym)
            ibCancel = itemView.findViewById(R.id.btnCancel)
            cvBookingGym = itemView.findViewById(R.id.cv_BookingGym)
        }
    }
}