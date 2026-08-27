package dcc.uchile.com.example.petlove.data.remote

import dcc.uchile.com.example.petlove.data.model.Pet
import dcc.uchile.com.example.petlove.data.model.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // =========================
    // PETS
    // =========================

    @GET("pets")
    suspend fun getPets(): List<Pet>

    @GET("pets/{id}")
    suspend fun getPet(
        @Path("id") id: Int
    ): Pet

    @POST("pets")
    suspend fun createPet(
        @Body pet: Pet
    ): Pet

    @PUT("pets/{id}")
    suspend fun updatePet(
        @Path("id") id: Int,
        @Body pet: Pet
    ): Pet

    @DELETE("pets/{id}")
    suspend fun deletePet(
        @Path("id") id: Int
    ): Response<Unit>


    // =========================
    // PRODUCTS
    // =========================

    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: Int
    ): Product

    @POST("products")
    suspend fun createProduct(
        @Body product: Product
    ): Product

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body product: Product
    ): Product

    @DELETE("products/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Int
    ): Response<Unit>
}