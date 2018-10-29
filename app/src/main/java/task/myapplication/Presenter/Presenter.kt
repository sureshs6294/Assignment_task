package task.myapplication.Presenter

import android.support.v4.app.FragmentActivity
import task.myapplication.Model.Item

class Presenter(val mGetDataViews: GetdataContract.View):GetdataContract.Presenter,GetdataContract.onGetDataListener{
    var mGetDataView: GetdataContract.View ?=mGetDataViews
    var mIntractor: Intractor ?=null

    fun Presenter(mGetDataView: GetdataContract.View) {
        this.mGetDataView = mGetDataView
        mIntractor = Intractor(this)
    }

    override fun onSuccess(message: String, list: List<Item>) {
        mGetDataView?.onGetDataSuccess(message, list)
    }

    override fun onFailure(message: String) {
        mGetDataView?.onGetDataFailure(message)
    }

    override fun getDataFromURL(context: FragmentActivity?, url: String,id:Int) {
        mIntractor = Intractor(this)
        if (context != null) {
           // mIntractor?.initRetrofitCall(context, url,id)
            mIntractor?.loadMore(id)
        }
    }
}