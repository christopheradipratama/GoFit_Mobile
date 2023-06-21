package com.example.gofit_mobile

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gofit_mobile.BookingGym.BookingGymFragment
import com.example.gofit_mobile.Instruktur.ProfileInstrukturFragment
import com.example.gofit_mobile.IzinInstruktur.IzinInstrukturFragment
import com.example.gofit_mobile.JadwalHarian.JadwalHarianFragment
import com.example.gofit_mobile.Member.ProfileMemberFragment
import com.example.gofit_mobile.PresensiInstruktur.PresensiInstrukturFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE)
        getSupportActionBar()?.hide()

        var role = sharedPreferences.getString("role",null)

        if(role == "Member"){
            setThatFragments(MemberHomeFragment())
            nav_view.setOnItemSelectedListener {
                when(it){
                    R.id.navigation_jadwal_harian ->{
                        setThatFragments(JadwalHarianFragment())
                    }
                    R.id.navigation_home ->{
                        setThatFragments(MemberHomeFragment())
                    }
                    R.id.navigation_profile ->{
                        setThatFragments(ProfileMemberFragment())
                    }
                }
                true
            }
        }

        if(role == "Manajer Operasional"){
            setThatFragments(ManajerOperasionalHomeFragment())
            nav_view.setOnItemSelectedListener {
                when(it){
                    R.id.navigation_home ->{
                        setThatFragments(ManajerOperasionalHomeFragment())
                    }
                    R.id.navigation_home ->{
                        setThatFragments(PresensiInstrukturFragment())
                    }
                }
                true
            }
        }

        if(role == "Instruktur"){
            setThatFragments(InstrukturHomeFragment())
            nav_view.setOnItemSelectedListener {
                when(it){
                    R.id.navigation_jadwal_harian ->{
                        setThatFragments(JadwalHarianFragment())
                    }
                    R.id.navigation_home ->{
                        setThatFragments(InstrukturHomeFragment())
                    }
                    R.id.navigation_profile ->{
                        setThatFragments(ProfileInstrukturFragment())
                    }
                }
                true
            }
        }
    }

    private fun setThatFragments(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layoutFragment,fragment)
            commit()
        }
    }

    fun getSharedPreferences(): SharedPreferences {
        return sharedPreferences
    }
}