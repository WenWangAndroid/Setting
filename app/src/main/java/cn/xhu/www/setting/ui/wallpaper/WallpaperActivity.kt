package cn.xhu.www.setting.ui.wallpaper

import android.os.Bundle
import cn.xhu.www.setting.base.BaseActivity
import cn.xhu.www.setting.databinding.WallpaperActivityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class WallpaperActivity : BaseActivity() {
    private val wallpaperViewModel: WallpaperViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewDataBinding(WallpaperActivityBinding::inflate)
    }
}