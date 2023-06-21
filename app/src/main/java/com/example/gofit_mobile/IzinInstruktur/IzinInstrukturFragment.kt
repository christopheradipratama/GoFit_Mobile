package com.example.gofit_mobile.IzinInstruktur

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.gofit_mobile.R

class IzinInstrukturFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_izin_instruktur, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAdd: Button = view.findViewById(R.id.btnAdd)


        btnAdd.setOnClickListener(View.OnClickListener {
            val move = Intent(this@IzinInstrukturFragment.context, IzinInstrukturActivity::class.java)
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