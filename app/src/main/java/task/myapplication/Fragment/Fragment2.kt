package task.myapplication.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main2.view.*
import task.myapplication.R

class Fragment2:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getActivity()?.getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        getActivity()?.getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val view = inflater.inflate(R.layout.activity_main2, container, false)
        val strtext = arguments!!.getString("name")
        val image_text=arguments!!.getString("image")
        Log.d("name",">"+strtext)
        Log.d("image",">"+image_text)
        if(image_text!=null) {
            Glide.with(activity!!.applicationContext).load(image_text).into(view.view_image)
        }
        user_collapsing_toolbar?.title="Welcome"
        return view
    }
}