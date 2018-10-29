package task.myapplication.Presenter

import android.content.Context
import android.support.v4.app.FragmentActivity
import task.myapplication.Model.Item

interface GetdataContract {

    interface View {
        fun onGetDataSuccess(message: String, list: List<Item>)
        fun onGetDataFailure(message: String)
    }

    interface Presenter {
        fun getDataFromURL(context: FragmentActivity?, url: String,id:Int)
    }

    interface Interactor {
        fun initRetrofitCall(context: Context, url: String, id: Int)

    }

    interface onGetDataListener {
        fun onSuccess(message: String, list: List<Item>)
        fun onFailure(message: String)
    }
}