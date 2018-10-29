package task.myapplication.Presenter

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import task.myapplication.Adapter.DataAdapter
import task.myapplication.Model.DataResponse
import task.myapplication.Model.Datas
import task.myapplication.Model.Item
import java.util.ArrayList

class Intractor(val mOnGetDatalistener:GetdataContract.onGetDataListener) : GetdataContract.Interactor {

    var mOnGetDatalisteners: GetdataContract.onGetDataListener? = mOnGetDatalistener
    private var allcountry: ArrayList<Item> = ArrayList<Item>()
    internal var allCountriesData: MutableList<String> = ArrayList()
    internal var dataAdapter: DataAdapter?=null
    internal var isLoading = false
    internal var isLastPage = false
     val PAGE_START = 1
     val TOTAL_PAGES = 5
     var currentPage = PAGE_START
    internal var apiInterface: DataResponse? = null

    override fun initRetrofitCall(context: Context, url: String, id: Int) {
        Log.d("test",">"+id)

        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val request = retrofit.create<DataResponse>(DataResponse::class.java!!)
        val call = request.get_data_list(id)
        call.enqueue(object : retrofit2.Callback<Datas> {
            override fun onResponse(call: retrofit2.Call<Datas>, response: retrofit2.Response<Datas>) {
                val jsonResponse = response.body()
                allcountry = jsonResponse!!.items as ArrayList<Item>
                for (i in allcountry.indices) {
                    allCountriesData.add(allcountry[i].toString())
                }
                Log.d("Data", "Refreshed")
                Log.d("List_size",">"+ allcountry.size )
                mOnGetDatalisteners?.onSuccess("List Size: " + allCountriesData.size, allcountry)
            }

            override fun onFailure(call: retrofit2.Call<Datas>, t: Throwable) {
                Log.v("Error", t.message)
                mOnGetDatalisteners?.onFailure(t.message!!)
            }
        })
    }

    fun loadMore(index: Int) {
        //   toast("loadmore")
        Log.d("Load_more_INdex",">"+index)
        //add loading progress view
        allcountry.add(Item())
        dataAdapter?.notifyItemInserted(allcountry.size-1)

        apiInterface = Apiclient.getClient()!!.create(DataResponse::class.java)
        val call = apiInterface!!.get_data_list(index)
        call.enqueue(object : Callback<Datas> {
            override fun onResponse(call: Call<Datas>, response: Response<Datas>) {
                if (response.isSuccessful) {
                    //remove loading view
                    allcountry.removeAt(allcountry.size - 1)
                    val jsonResponse = response.body()

                    allcountry = jsonResponse!!.items as ArrayList<Item>

                    Log.d("data_size",">"+allcountry.size)
                    if (true) {
                        for (i in allcountry.indices) {
                            allCountriesData.add(allcountry[i].toString())
                        }
                        if (allCountriesData.size > 0) {
                            //add loaded data
                            mOnGetDatalisteners?.onSuccess("List Size: " + allCountriesData.size, allcountry)
                        }
                        else {//result size 0 means there is no more data available at server
                            dataAdapter!!.setMoreDataAvailable(false)
                            //telling adapter to stop calling load more as no more server data available
                            //  Toast.makeText(context, "No More News Available", Toast.LENGTH_LONG).show()
                        }
                        dataAdapter?.notifyDataChanged()
                    }
                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                } else {
                    Log.e(ContentValues.TAG, " Load More Response Error " + response.code().toString())
                }
            }

            override fun onFailure(call: Call<Datas>, t: Throwable) {
                Log.v("Error", t.message)
                mOnGetDatalisteners?.onFailure(t.message!!)
            }
        })
    }

}