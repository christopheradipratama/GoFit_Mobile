package com.example.gofit_mobile.api

class UserApi {
    companion object{
//        val BASE_URL ="http://192.168.88.187/GoFit_200710780/GoFit_Web/public/api/"
        val BASE_URL ="https://gofitaja.my.id/api/"

        val GET_ALL_URL = BASE_URL + "profil/"
        val GET_BY_ID_URL = BASE_URL + "profil/"
        val ADD_URL = BASE_URL + "register"
        val UPDATE_URL = BASE_URL + "profil/"
        val DELETE_URL = BASE_URL + "profil/"

        val LOGIN_URL = BASE_URL + "login"
        val RESET_PASSWORD_URL = BASE_URL + "gantiPassword"
        val LOGOUT_URL = BASE_URL + "logout"
    }
}