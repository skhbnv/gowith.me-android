package com.example.gowithme.ui.create_new_event.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.gowithme.MainActivity
import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentCreateNewEventBinding
import com.example.gowithme.ui.create_new_event.adapter.ImagesRecyclerAdapter
import com.example.gowithme.ui.create_new_event.viewmodel.CreateEventUI
import com.example.gowithme.ui.create_new_event.viewmodel.CreateNewEventViewModel
import com.example.gowithme.ui.create_new_event.viewmodel.InputTypes
import com.example.gowithme.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_new_event.*
import kotlinx.android.synthetic.main.item_textview.view.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
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
        // Вызываем диалог перед back navigation
        setupBackNavigation()

        // Загружаем возможные категории ивента
        createNewEventViewModel.getCategories()

        // Накидываем литенеры на кнопки
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
                createNewEventViewModel.isFree = isChecked
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


    /**
     * Спрашиваем хочет ли пользователь покинуть экран,
     * так как введуные данные будут потеряны
     */
    private fun setupBackNavigation() {
        with(activity as MainActivity) {
            toolbar.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
            onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showAlert(
                        context = context,
                        title = getString(R.string.alert_title_are_you_sure_to_leave),
                        message = getString(R.string.alert_message_are_you_sure_to_leave),
                        ok = { findNavController().navigateUp() },
                        cancel = {}
                    )
                }
            })

        }
    }


    /**
     * ----------------------------------------------------- Start LiveData Observing -----------------------------------------------------
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeCreateEventUI()

        observeCheckedCategories()

        createNewEventViewModel.addressText.observe(viewLifecycleOwner, Observer {
            Log.d("taaag", "addressText observe $it")
            binding.addressInput.setText(it)
        })
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

    private fun validationError(inputType: InputTypes) {
        when(inputType) {
            InputTypes.TITLE -> titleInput.error = getString(R.string.error_text_empty_input_field)
            InputTypes.DESCRIPTION -> descriptionInput.error = getString(R.string.error_text_empty_input_field)
            InputTypes.CATEGORY -> getString(R.string.error_text_empty_input_field).showToast(context)
            InputTypes.ADDRESS -> addressInput.error = getString(R.string.error_text_empty_input_field)
            InputTypes.START -> startDateInput.error = getString(R.string.error_text_empty_input_field)
            InputTypes.END -> endDateInput.error = getString(R.string.error_text_empty_input_field)
            InputTypes.PRICE -> priceInput.error = getString(R.string.error_text_empty_input_field)
        }
    }

     //----------------------------------------------------- End LiveData Observing -----------------------------------------------------


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            CAMERA_REQUEST_CODE -> {
                val imageFile = File(currentPhotoPath)
                createNewEventViewModel.uploadImage(imageFile)
                Log.d("taaag", "dispatchTakePictureIntent ${imageFile.toUri()}")

            }
            GALLERY_REQUEST_CODE -> {
                data?.data?.let { uri ->
                    val parcelFileDescriptor =
                        requireContext().contentResolver.openFileDescriptor(uri, "r", null) ?: return
                    val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                    val file = File(requireContext().cacheDir, requireContext().contentResolver.getFileName(uri))
                    val outputStream = FileOutputStream(file)
                    inputStream.copyTo(outputStream)
                    inputStream.close()
                    outputStream.close()
                    createNewEventViewModel.uploadImage(file)
                }
            }
        }
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

    companion object {
        private const val CAMERA_REQUEST_CODE = 0
        private const val GALLERY_REQUEST_CODE = 1
        private const val CAMERA_PERM_CODE = 101
    }
}