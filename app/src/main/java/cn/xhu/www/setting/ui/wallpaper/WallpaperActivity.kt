package cn.xhu.www.setting.ui.wallpaper

import android.os.Bundle
import cn.xhu.www.setting.base.BaseActivity
import cn.xhu.www.setting.databinding.WallpaperActivityBinding

class WallpaperActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewDataBinding(WallpaperActivityBinding::inflate)
    }
}
