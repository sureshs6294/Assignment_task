package task.myapplication.Model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DataResponse {
    @GET("/search/users?q=followers:%3E=0&amp;per_page=30&amp;page=id")
    fun get_data_list(@Query("id")id:Int?): Call<Datas>
}