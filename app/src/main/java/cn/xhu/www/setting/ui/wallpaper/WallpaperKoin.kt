package cn.xhu.www.setting.ui.wallpaper

import cn.xhu.www.setting.data.WallpaperRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val wallpaperModule = module {
    single { WallpaperRepository() }
    viewModel { WallpaperViewModel(get()) }
}