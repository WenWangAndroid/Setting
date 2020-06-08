package cn.xhu.www.setting.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import cn.xhu.www.setting.utils.logInfo

abstract class BaseFragment : Fragment() {
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


}