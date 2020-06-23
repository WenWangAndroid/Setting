package cn.xhu.www.setting.ui.wallpaper

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import cn.xhu.www.setting.R
import cn.xhu.www.setting.base.BaseFragment
import cn.xhu.www.setting.databinding.WallpaperMainFragmentBinding
import cn.xhu.www.setting.ui.adapter.WallpaperAdapter
import cn.xhu.www.setting.utils.hasPermissions
import cn.xhu.www.setting.utils.rx.async
import cn.xhu.www.setting.utils.rx.bindProgressBar
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.koin.androidx.viewmodel.ext.android.viewModel

class WallpaperMainFragment : BaseFragment() {
    private lateinit var binding: WallpaperMainFragmentBinding;
    private val wallpaperViewModel: WallpaperViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WallpaperMainFragmentBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@WallpaperMainFragment.viewLifecycleOwner
                viewModel = wallpaperViewModel
            }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wallpaperAdapter = WallpaperAdapter().apply {
            itemClick = { uri -> setLiveWallpaper(uri) }
        }
        binding.recyclerView.adapter = wallpaperAdapter
        wallpaperViewModel.items.observe(this.viewLifecycleOwner, Observer<List<Uri>> { list ->
            wallpaperAdapter.items = list
        })
        requestStoragePermission()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.wallpaper_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.video -> {

            }
            R.id.image -> {

            }
            R.id.storage -> {

            }
            R.id.transparent_wallpaper -> {
                requestCameraPermission()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun requestCameraPermission() {
        val permission = Manifest.permission.CAMERA
        if (requireActivity().hasPermissions(permission)) {
//            findNavController().navigate(R.id.action_permissions_to_wallpaper_main)
        } else {
            requestPermissions(arrayOf(permission), CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    private fun requestStoragePermission() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (requireActivity().hasPermissions(permission)) {
            wallpaperViewModel.getVideos()
        } else {
            requestPermissions(arrayOf(permission), STORAGE_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                    wallpaperViewModel.getVideos()
                } else {

                }
            }

            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
            }
        }
    }

    private fun setLiveWallpaper(uri: Uri) {
        context?.run {
            saveLiveWallpaper(uri)
                .async()
                .bindProgressBar(requireActivity())
                .subscribeBy {
                    clearOwnWallpaper()
                    startActivity(getLiveWallpaperIntent())
                }
        }
    }

    companion object {
        const val CAMERA_PERMISSION_REQUEST_CODE = 10
        const val STORAGE_PERMISSION_REQUEST_CODE = 11
    }
}
