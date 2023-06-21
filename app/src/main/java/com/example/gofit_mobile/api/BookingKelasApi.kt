package com.example.gofit_mobile.api

class BookingKelasApi {
    companion object{
//        val BASE_URL ="http://192.168.88.187/GoFit_200710780/GoFit_Web/public/api/"
        val BASE_URL ="https://gofitaja.my.id/api/"

        val BOOKINGKELAS = BASE_URL + "bookingKelas"
        val GETDATABOOKINGKELAS = BASE_URL + "IndexBookingKelas/"
        val CANCELBOOKINGKELAS = BASE_URL + "cancelBookingKelas/"
        val GETDATASCHEDULE = BASE_URL + "instruktur_jadwal_presensi/"
        val GETDATAMEMBER = BASE_URL + "presensi_member/"
        val UPDATETRANSACTION = BASE_URL + "update_transaksi_presensi"
    }
}