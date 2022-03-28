package com.genius.gfiilapp

class ApiUtils {
    companion object {
        private const val BASE_URL = "https://gfiilapp.com/"

        fun usersDAOInterface(): UsersDAOInterface {
            return RetrofitClient.getClient(BASE_URL).create(UsersDAOInterface::class.java)
        }
    }

}