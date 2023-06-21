package com.example.gofit_mobile.PresensiInstruktur

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.gofit_mobile.JadwalHarian.JadwalHarianActivity
import com.example.gofit_mobile.R

class PresensiInstrukturFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jadwal_harian, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val btnJadwalHarian: Button = view.findViewById(R.id.btnJadwalHarian)

        btnJadwalHarian.setOnClickListener(View.OnClickListener {
            val move = Intent(this@PresensiInstrukturFragment.context, JadwalHarianActivity::class.java)
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