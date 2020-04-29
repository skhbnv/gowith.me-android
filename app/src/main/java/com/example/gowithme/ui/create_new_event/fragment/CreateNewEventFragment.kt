package com.example.gowithme.ui.create_new_event.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentCreateNewEventBinding
import com.example.gowithme.ui.create_new_event.adapter.ImagesRecyclerAdapter
import com.example.gowithme.ui.create_new_event.viewmodel.CreateEventUI
import com.example.gowithme.ui.create_new_event.viewmodel.CreateNewEventViewModel
import com.example.gowithme.ui.create_new_event.viewmodel.InputTypes
import com.example.gowithme.util.*
import kotlinx.android.synthetic.main.fragment_create_new_event.*
import kotlinx.android.synthetic.main.item_textview.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CreateNewEventFragment : Fragment() {


    private val createNewEventViewModel: CreateNewEventViewModel by sharedGraphViewModel(R.id.nav_create_new_event)
    private lateinit var binding: FragmentCreateNewEventBinding
    private val startCalendar by lazy { Calendar.getInstance() }
    private val endCalendar by lazy { Calendar.getInstance() }
    private val eventImagesAdapter by lazy {
        ImagesRecyclerAdapter {
            selectImageSource()
        }
    }
    private lateinit var currentPhotoPath: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_new_event, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = createNewEventViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createNewEventViewModel.getCategories()
        with(binding) {
            eventImages.adapter = eventImagesAdapter

            startDateInput.setOnClickListener {
                startCalendar.showDateTimePicker(root.context) {
                    startDateInput.setText(SimpleDateFormat.getDateTimeInstance().format(it.time))
                    createNewEventViewModel.startDate = it.time.toIsoFormat()
                }
            }
            endDateInput.setOnClickListener {
                endCalendar.showDateTimePicker(root.context) {
                    endDateInput.setText(SimpleDateFormat.getDateTimeInstance().format(it.time))
                    createNewEventViewModel.endDate = it.time.toIsoFormat()
                }
            }
            selectCategoriesButton.setOnClickListener {
                val direction = CreateNewEventFragmentDirections.actionCreateNewEventFragmentToCategorySelectionDialogFragment()
                findNavController().navigate(direction)
            }
            addressInput.setOnClickListener {
                val direction = CreateNewEventFragmentDirections.actionNavCreateNewEventToMapAddressFragment()
                findNavController().navigate(direction)
            }
            isFreeSwitch.setOnCheckedChangeListener { _, isChecked ->
                when(isChecked) {
                    true -> {
                        priceInput.setText("Бесплатно")
                        priceInput.isEnabled = false
                    }
                    false -> {
                        priceInput.setText("")
                        priceInput.isEnabled = true
                    }
                }
            }
            createEventButton.setOnClickListener {
                with(createNewEventViewModel) {
                    title = titleInput.text.toString()
                    description = descriptionInput.text.toString()
                    price = priceInput.text.toString()
                }
                createNewEventViewModel.createEvent()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeCreateEventUI()

        observeCheckedCategories()

        createNewEventViewModel.addressText.observe(viewLifecycleOwner, Observer {
            Log.d("taaag", "addressText observe $it")
            binding.addressInput.setText(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            CAMERA_REQUEST_CODE -> {
                val imageFile = File(currentPhotoPath)
                createNewEventViewModel.uploadImage(imageFile)
                Log.d("taaag", "dispatchTakePictureIntent ${imageFile.toUri()}")

            }
            GALLERY_REQUEST_CODE -> {
                data?.data?.let { selectedImage ->
                    Log.d("taaag", "selectedImage ${getPath(selectedImage)}")
                    val imageFile = File(getPath(selectedImage))
                    createNewEventViewModel.uploadImage(imageFile)
                }

            }
        }
    }

    private fun observeCheckedCategories() {
        createNewEventViewModel.checkedCategoriesLD.observe(viewLifecycleOwner, Observer {
            with(binding.categoriesList) {
                removeAllViews()
                it.forEach { category ->
                    inflate(R.layout.item_textview).apply {
                        textView.text = category.name
                    }.also {
                        addView(it)
                    }
                }
            }
        })
    }

    /**
     * ----------------------------------------------------- UI LiveData Observing -----------------------------------------------------
     */
    private fun observeCreateEventUI() {
        createNewEventViewModel.createEventUI.observe(viewLifecycleOwner, Observer {
            when(it) {
                is CreateEventUI.ValidationError -> {
                    Log.d("taaag", "ValidationError inputType ${it.inputType}")
                    validationError(it.inputType)
                }
                is CreateEventUI.EventImageUploaded -> {
                    eventImagesAdapter.addImageUri(it.imageFile.toUri())
                }
                is CreateEventUI.EventImageUploadError -> {
                    "Не удалось загрузить фото".showToast(context)
                }
            }
        })
    }

    /**
     * Функиция создает AlertDialog для выбора загрузки фото соз галереи или с камеры
     */
    private fun selectImageSource() {
        val options = arrayOf<CharSequence>(getString(R.string.take_photo_option), getString(R.string.choose_from_gallery_option), getString(R.string.cancel_option))
        val alertBuilder = AlertDialog.Builder(context).apply {
            setTitle(getString(R.string.add_photo_alter_title))
            setItems(options) { dialog, item ->
                when(options[item]) {
                    getString(R.string.take_photo_option) -> {
                        if (hasCameraPermissions()) {
                            dispatchTakePictureIntent()
                        }
                    }
                    getString(R.string.choose_from_gallery_option) -> {
                        val galleryIntent = Intent(Intent.ACTION_PICK,  MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
                    }
                    getString(R.string.cancel_option) -> {
                        dialog.dismiss()
                    }
                }
            }
        }
        alertBuilder.show()
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile = try {
                    createImageFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.gowithme.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
                }
            }
        }
    }

    fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().managedQuery(uri, projection, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun hasCameraPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERM_CODE)
            Log.d("taaag", "hasCameraPermissions FALSE")
            return false
        }
        Log.d("taaag", "hasCameraPermissions TRUE")
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("taaag", "onRequestPermissionsResult requestCode $requestCode")
        when (requestCode) {
            CAMERA_PERM_CODE -> {
                if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                    Log.d("taaag", "onRequestPermissionsResult CAMERA_PERM_CODE PERMISSION_GRANTED")
                    dispatchTakePictureIntent()
                } else {
                    getString(R.string.text_camera_permission_not_granted).showToast(context)
                }
            }
        }
    }

    private fun validationError(inputType: InputTypes) {
        when(inputType) {
            InputTypes.TITLE -> titleInput.error = "Invalid"
            InputTypes.DESCRIPTION -> descriptionInput.error = "Invalid"
            InputTypes.CATEGORY -> "Category is empty".showToast(context)
            InputTypes.ADDRESS -> addressInput.error = "Invalid"
            InputTypes.START -> startDateInput.error = "Invalid"
            InputTypes.END -> endDateInput.error = "Invalid"
            InputTypes.PRICE -> priceInput.error = "Invalid"
        }
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 0
        private const val GALLERY_REQUEST_CODE = 1
        private const val CAMERA_PERM_CODE = 101
    }
}