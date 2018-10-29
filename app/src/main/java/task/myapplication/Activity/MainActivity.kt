package task.myapplication.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import task.myapplication.Adapter.DataAdapter
import task.myapplication.Model.Item
import task.myapplication.Presenter.GetdataContract
import task.myapplication.Presenter.Presenter
import task.myapplication.R

class MainActivity : AppCompatActivity(),GetdataContract.View {

    private var mPresenter: Presenter? = null
    internal var recyclerView: RecyclerView ?=null
    internal var linearLayoutManager: LinearLayoutManager ?=null
    internal var dataAdapter: DataAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = Presenter(this)
       // mPresenter?.getDataFromURL(applicationContext, "")
        recyclerView = findViewById(R.id.recycler) as RecyclerView
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(linearLayoutManager)
    }

    override fun onGetDataSuccess(message: String, list: List<Item>) {
       // dataAdapter = DataAdapter(applicationContext, list)
        recyclerView?.setAdapter(dataAdapter)
    }

    override fun onGetDataFailure(message: String) {
        Log.d("Status", message)
    }

}
