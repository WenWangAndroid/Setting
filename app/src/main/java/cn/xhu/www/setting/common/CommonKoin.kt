package cn.xhu.www.setting.common

import org.koin.dsl.module

val commonModule = module {
    single { SharedPreferencesManager(get()) }
}