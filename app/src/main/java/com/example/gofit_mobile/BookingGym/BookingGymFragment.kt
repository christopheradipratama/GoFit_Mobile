package com.example.gofit_mobile.BookingGym

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.gofit_mobile.JadwalHarian.JadwalHarianActivity
import com.example.gofit_mobile.R

class BookingGymFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_booking_gym, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val btnBookingGym: Button = view.findViewById(R.id.btnBookingGym)

        btnBookingGym.setOnClickListener(View.OnClickListener {
            val move = Intent(this@BookingGymFragment.context, BookingGymActivity::class.java)
            startActivity(move)
        })

//        btnGlide.setOnClickListener(View.OnClickListener {
//            val url = "https://picsum.photos/300"
//
//            Glide.with(this)
//                .load(url)
//                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(imageGlide)
//        })
    }
}