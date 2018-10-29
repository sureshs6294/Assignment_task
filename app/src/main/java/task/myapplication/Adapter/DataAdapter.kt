package task.myapplication.Adapter

import android.app.PendingIntent.getActivity
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import task.myapplication.Model.Item
import task.myapplication.R
import android.os.Bundle
import android.util.Log
import task.myapplication.Fragment.Fragment2
import task.myapplication.Model.Datas


class DataAdapter(val applicationContext: FragmentActivity?, var list: List<Item>) :RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    internal var loadMoreListener: OnLoadMoreListener? = null
    internal var isLoading = false
    internal var isMoreDataAvailable = true
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context)
                .inflate(R.layout.data_list, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setMoreDataAvailable(moreDataAvailable: Boolean) {
        isMoreDataAvailable = moreDataAvailable
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if (p1 >= itemCount - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            Log.d("more_size", ">" + list!!.size)
            // activity!!.toast("more")
            isLoading = true
            loadMoreListener!!.onLoadMore()
        }

        val album = list!![p1]
        p0.name.setText(album.login)
        var img_name: String? = null
        var name: String? = null
        img_name = album.avatarUrl
        name=album.login
        if (applicationContext != null) {
            Glide.with(applicationContext).load(img_name).into(p0.image)
        }
        p0.card.setOnClickListener({
            val args = Bundle()
            args.putString("image", img_name)
            args.putString("name",name)
            val fragobj = Fragment2()
            fragobj.setArguments(args)
            Log.d("LOGTAG", img_name)
            Log.d("LOGTAG", "clicked")
            val fragmentManager = applicationContext?.getSupportFragmentManager()
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.frame_container, fragobj)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        })
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var name: TextView
        var image: ImageView
        var card: CardView


        init {
            name = view.findViewById<View>(R.id.name) as TextView
            image = view.findViewById<View>(R.id.avatar) as ImageView
            card=view.findViewById<View>(R.id.card_view)as CardView
        }
    }
    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    fun setLoadMoreListener(loadMoreListener: OnLoadMoreListener) {
        this.loadMoreListener = loadMoreListener
    }

    fun notifyDataChanged() {
        notifyDataSetChanged()
        isLoading = false
    }




}