package com.sjsoft.app.ui.main

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sjsoft.app.R
import com.sjsoft.app.di.Injectable
import com.sjsoft.app.ui.BaseFragment
import com.sjsoft.app.ui.gallery.GalleryFragment
import com.sjsoft.app.util.addFragmentToActivity
import com.sjsoft.app.util.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_menu.*
import javax.inject.Inject

class MenuFragment : BaseFragment(), Injectable {
    override val titleResource: Int
        get() = R.string.title_main

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: MenuViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bt_gallery.setSafeOnClickListener {
            addFragmentToActivity(GalleryFragment())
        }

        bt_upload.setSafeOnClickListener {
            //callImagePicker()
            callVideoPicker()

            //addFragmentToActivity(UploadFragment())
        }
    }

    fun callVideoPicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/* video/*"
        startActivityForResult(intent, REQ_MEDIA_PICKER)
    }

    private val REQ_MEDIA_PICKER = 1314
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
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
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        if (selectedImage != null) {
            var cursor: Cursor? = null
            try {
                cursor = context!!.contentResolver.query(
                    selectedImage,
                    filePathColumn, null, null, null
                )
                if (cursor != null) {
                    cursor.moveToFirst()

                    val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                    val picturePath = cursor.getString(columnIndex)
                    Log.e("MenuFragment", "MenuFragment.picturePath: $picturePath")
                    //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
            }
        }
    }
}