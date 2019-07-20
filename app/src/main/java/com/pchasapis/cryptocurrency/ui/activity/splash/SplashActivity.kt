package com.pchasapis.cryptocurrency.ui.activity.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.pchasapis.cryptocurrency.R
import com.pchasapis.cryptocurrency.ui.activity.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_DELAY: Long = 3000
    }

    private var mDelayHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initLayout()
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }
    }

    public override fun onDestroy() {
        mDelayHandler?.removeCallbacks(mRunnable)
        super.onDestroy()
    }

    private fun initLayout() {
        mDelayHandler = Handler()
        mDelayHandler?.postDelayed(mRunnable, SPLASH_DELAY)
    }

}
