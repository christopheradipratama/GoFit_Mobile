package com.example.gofit_mobile.api

class BookingGymApi {
    companion object{
//        val BASE_URL ="http://192.168.88.187/GoFit_200710780/GoFit_Web/public/api/"
        val BASE_URL ="https://gofitaja.my.id/api/"

        val GET_ALL_URL = BASE_URL + "IndexBookingGym/"
        val GET_BY_ID_URL = BASE_URL + "IndexBookingGym/"
        val STORE_DATA = BASE_URL + "CreateBookingGym"
        val UPDATE_URL = BASE_URL + "IndexBookingGym/"
        val DELETE_URL = BASE_URL + "CancelBookingGym/"
    }
}