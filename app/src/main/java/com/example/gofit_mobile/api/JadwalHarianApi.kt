package com.example.gofit_mobile.api

class JadwalHarianApi {
    companion object{
//        val BASE_URL ="http://192.168.88.187/GoFit_200710780/GoFit_Web/public/api/"
        val BASE_URL ="https://gofitaja.my.id/api/"

        val GET_ALL_URL = BASE_URL + "IndexJadwalHarian"
        val GET_BY_ID_URL = BASE_URL + "IndexJadwalHarian/"
        val ADD_URL = BASE_URL + "IndexJadwalHarian"
        val UPDATE_URL = BASE_URL + "IndexJadwalHarian/"
        val DELETE_URL = BASE_URL + "IndexJadwalHarian/"
    }
}