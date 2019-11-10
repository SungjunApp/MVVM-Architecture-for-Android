package com.sjsoft.app.ui.upload

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sjsoft.app.R
import com.sjsoft.app.di.Injectable
import com.sjsoft.app.ui.BaseFragment
import com.sjsoft.app.ui.holders.MarginInfo
import com.sjsoft.app.ui.viewer.ImageViewerFragment
import com.sjsoft.app.util.*
import kotlinx.android.synthetic.main.fragment_upload.*
import javax.inject.Inject

class UploadFragment : BaseFragment(), Injectable {
    override val titleResource: Int
        get() = R.string.title_upload_a_pic

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    val viewModel: UploadViewModel by viewModels {
        viewModelFactory
    }

    internal var gridLayoutManager: GridLayoutManager? = null
    var GRID_COUNT: Int = 3

    val adapter: UploadAdapter by lazy {
        UploadAdapter(
            context!!,
            MarginInfo(
                0
                , 3.toPx()
                , 3.toPx()
            )
        ) { s, view ->
            addFragmentToActivity(ImageViewerFragment.getInstance(s, view.transitionName), view)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    val constraintSet = ConstraintSet()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.setPaddingRelative(
            3.toPx()
            , 10.toPx()
            , 3.toPx()
            , 50.toPx()
        )

        recyclerView.setShadowViewController(v_shadow)

        viewModel.uploadUI.observe(this, Observer {
            makeLoading(it is UploadViewModel.UploadUI.Loading)
            when (it) {
                is UploadViewModel.UploadUI.Complete -> {
                    showToast(it.message)
                    viewModel.getS3Images()
                }
                is UploadViewModel.UploadUI.Error -> {
                    showToast(it.message)
                }
            }
        })

        viewModel.listUI.observe(this, Observer {
            when (it) {
                is UploadViewModel.ListUI.LoadingShown -> v_loading.show()
                is UploadViewModel.ListUI.LoadingHidden -> v_loading.hide()
                is UploadViewModel.ListUI.Data -> {
                    v_loading.hide()
                    adapter.submitList(it.list)
                    v_empty.visibility = if (it.list.isNotEmpty()) GONE else VISIBLE
                }
            }
        })

        viewModel.loadMoreUI.observe(this, Observer {
            constraintSet.setupLoadMore(v_content_box, bt_more, it)
        })

        viewModel.buttonUI.observe(this, Observer {
            bt_upload.isEnabled = it
        })

        et_title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.updateTitle(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        bt_upload.setSafeOnClickListener {
            setupExternalStoragePermission()
        }

        bt_more.setSafeOnClickListener {
            viewModel.getS3Images()
        }

        gridLayoutManager = GridLayoutManager(context, GRID_COUNT)
        recyclerView!!.layoutManager = gridLayoutManager
        gridLayoutManager?.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }

        recyclerView.adapter = adapter

        viewModel.getS3Images()
    }

    fun callMediaPicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/* video/*"
        startActivityForResult(intent, REQ_MEDIA_PICKER)
    }

    private val REQ_MEDIA_PICKER = 1314
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_MEDIA_PICKER -> {
                    data?.also {
                        extractImage(it)
                    }
                }
            }
        }
    }

    fun extractImage(data: Intent) {
        val selectedImage = data.data
        val filePathColumn =
            arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media.MIME_TYPE)
        if (selectedImage != null) {
            var cursor: Cursor? = null
            try {
                cursor = context!!.contentResolver.query(
                    selectedImage,
                    filePathColumn, null, null, null
                )
                if (cursor != null) {
                    cursor.moveToFirst()

                    val filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]))
                    val mimeType = cursor.getString(cursor.getColumnIndex(filePathColumn[1]))
                    viewModel.uploadImage(filePath, mimeType)
                    Log.e(
                        "HomeFragment",
                        "HomeFragment.picturePath: $filePath, mimeType: $mimeType"
                    )
                    //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
            }
        }
    }

    private fun setupExternalStoragePermission() {
        activity?.also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    callMediaPicker()
                } else {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        reqStorage
                    )
                }
            }
        }

    }

    private val reqStorage = 1729
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == reqStorage) {
            if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                callMediaPicker()
            } else {
                activity?.also {
                    val showRationale =
                        ActivityCompat.shouldShowRequestPermissionRationale(it, permissions[0])
                    if (!showRationale) {
                        createDialogForStoragePermission()
                    }
                }

            }
        }
    }

    private fun createDialogForStoragePermission() {
        val builder = context?.let { it1 -> AlertDialog.Builder(it1) }
        if (builder != null) {
            builder.setMessage(R.string.storage_permission_for_uploading)
            builder.setPositiveButton(android.R.string.ok) { _, _ ->
                startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:${activity?.packageName}"))
                )
            }
            builder.setNegativeButton(android.R.string.cancel) { _, _ ->
            }
            builder.show()
        }
    }

}