package com.example.instantmechanic.data.remote

import com.example.instantmechanic.data.model.AuthResponse
import com.example.instantmechanic.data.model.LoginRequest
import com.example.instantmechanic.data.model.Mechanic
import com.example.instantmechanic.data.model.PasswordResetRequest
import com.example.instantmechanic.data.model.ServiceRequest
import com.example.instantmechanic.data.model.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MechanicApi {

    @GET("api/mechanics")
    suspend fun getMechanics(): List<Mechanic>

    @GET("api/mechanics/{id}")
    suspend fun getMechanicById(
        @Path("id") id: Long
    ): Mechanic

    @GET("api/mechanics/search")
    suspend fun searchMechanics(
        @Query("name") name: String
    ): List<Mechanic>

    @GET("api/mechanics/filter")
    suspend fun filterByService(
        @Query("service") service: String
    ): List<Mechanic>

    @GET("api/service-requests")
    suspend fun getServiceRequests(): List<ServiceRequest>

    @POST("api/service-requests")
    suspend fun createServiceRequest(
        @Body request: ServiceRequest
    ): ServiceRequest

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    @POST("api/auth/signup")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): AuthResponse

    @POST("api/auth/forgot-password")
    suspend fun forgotPassword(
        @Query("emailOrPhone") emailOrPhone: String
    ): AuthResponse

    @POST("api/auth/reset-password")
    suspend fun resetPassword(
        @Body request: PasswordResetRequest
    ): AuthResponse
}