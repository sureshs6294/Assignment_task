package task.myapplication.Fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import task.myapplication.Adapter.DataAdapter
import task.myapplication.Model.Item
import task.myapplication.Presenter.GetdataContract
import task.myapplication.Presenter.Presenter
import task.myapplication.R
import task.myapplication.Utils.PaginationScrollListener
import java.util.ArrayList

class Fragment1 : Fragment(), GetdataContract.View {

    private var mPresenter: Presenter? = null
    internal var recyclerView: RecyclerView? = null
    internal var linearLayoutManager: LinearLayoutManager? = null
    internal var dataAdapter: DataAdapter? = null
    var progressDialog: ProgressDialog? = null
    var n = 1
    private val list = ArrayList<Item>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_main, container, false)
        list.clear()

        mPresenter = Presenter(this)
        mPresenter?.getDataFromURL(activity, "", n)
        recyclerView = view.findViewById(R.id.recycler) as RecyclerView

        progressDialog = ProgressDialog(activity,
                R.style.AppTheme_Dark_Dialog)
        progressDialog?.isIndeterminate = true
        progressDialog?.setCancelable(true)
        progressDialog?.setMessage("Loading...")
        progressDialog?.show()

        return view
    }

    override fun onGetDataSuccess(message: String, list: List<Item>) {
        dataAdapter = DataAdapter(activity, list)
        dataAdapter!!.setLoadMoreListener(object : DataAdapter.OnLoadMoreListener {
            override fun onLoadMore() {
               // Toast.makeText(activity, "hi", Toast.LENGTH_LONG).show()
                n++
                recyclerView!!.post({
                    mPresenter?.getDataFromURL(activity, "", n)
                })
            }
        })

        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.setLayoutManager(LinearLayoutManager(activity));
        recyclerView?.setAdapter(dataAdapter)
        dataAdapter?.notifyDataChanged()
        progressDialog?.dismiss()

    }

    override fun onGetDataFailure(message: String) {
        Log.d("Status", message)
        progressDialog?.dismiss()
    }
}