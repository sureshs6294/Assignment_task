package task.myapplication.Activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import task.myapplication.Fragment.Fragment1
import task.myapplication.R

class Activity_main:AppCompatActivity() {
    private val TAG_FRAGMENT = "Activity_main"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val conMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        if (netInfo == null) run {
           // toast("No internet connection")
        }
        else{
            loadFragment(Fragment1())
        }

    }
    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(TAG_FRAGMENT)
        transaction.commit()
    }

}