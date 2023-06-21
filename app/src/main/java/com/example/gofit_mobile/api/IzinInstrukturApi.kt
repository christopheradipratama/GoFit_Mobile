package com.example.gofit_mobile.api

class IzinInstrukturApi {
    companion object{
//        val BASE_URL ="http://192.168.88.187/GoFit_200710780/GoFit_Web/public/api/"
        val BASE_URL ="https://gofitaja.my.id/api/"

        val GET_ALL_URL = BASE_URL + "IndexIzinInstruktur/"
        val GET_BY_ID_URL = BASE_URL + "izinInstruktur/"
        val ADD_URL = BASE_URL + "AddIzinInstruktur"
        val UPDATE_URL = BASE_URL + "izinInstruktur/"
        val DELETE_URL = BASE_URL + "izinInstruktur/"
        val GETDATAJADWAL = BASE_URL + "IndexJadwalHarianInstruktur/"
    }
}