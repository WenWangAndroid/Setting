package cn.xhu.www.setting.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import cn.xhu.www.setting.utils.logInfo

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logInfo("------onCreate")
    }

    override fun onResume() {
        super.onResume()
        logInfo("------onResume")
    }

    override fun onPause() {
        super.onPause()
        logInfo("------onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        logInfo("------onDestroy")
    }

    fun <T : ViewDataBinding> setContentViewDataBinding(inflate: (LayoutInflater) -> T): T =
        inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@BaseActivity
        }
}
