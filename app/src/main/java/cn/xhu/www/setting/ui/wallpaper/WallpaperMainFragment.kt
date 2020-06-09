package cn.xhu.www.setting.ui.wallpaper

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import cn.xhu.www.setting.R
import cn.xhu.www.setting.base.BaseFragment
import cn.xhu.www.setting.databinding.WallpaperMainFragmentBinding
import cn.xhu.www.setting.ui.adapter.WallpaperAdapter
import cn.xhu.www.setting.utils.hasPermissions
import cn.xhu.www.setting.utils.logInfo
import cn.xhu.www.setting.widget.RecyclerViewItemDecoration
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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            requestPermissions(
                arrayOf(permission),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun requestStoragePermission() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (requireActivity().hasPermissions(permission)) {
            wallpaperViewModel.getImages().subscribe() { list ->
                logInfo("-------${list.size}")
                val wallpaperAdapter = WallpaperAdapter().apply { items = list }
                binding.recyclerView.apply {
                    layoutManager = GridLayoutManager(context,2)
                    addItemDecoration(RecyclerViewItemDecoration(context))
                    adapter = wallpaperAdapter
                }
            }
        } else {
            requestPermissions(
                arrayOf(permission),
                STORAGE_PERMISSION_REQUEST_CODE
            )
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
//                    findNavController().navigate(PermissionsFragmentDirections.actionPermissionsToWallpaperMain())
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

    companion object {
        const val CAMERA_PERMISSION_REQUEST_CODE = 10
        const val STORAGE_PERMISSION_REQUEST_CODE = 11
    }
}
