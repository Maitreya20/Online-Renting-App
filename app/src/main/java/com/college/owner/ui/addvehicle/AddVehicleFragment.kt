package com.college.owner.ui.addvehicle

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.college.erentapp.R
import com.college.erentapp.databinding.FragmentAddVehicleBinding
import com.college.util.AppController
import com.college.util.Keys
import com.college.util.SharedPreference
import com.college.util.toast
import com.github.dhaval2404.imagepicker.ImagePicker
import net.gotev.uploadservice.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class AddVehicleFragment : Fragment() {

    private var _binding: FragmentAddVehicleBinding? = null
    var vehicle_name:String?=null
    var vehicle_description:String?=null
    var vehicle_rate:String?=null

    private var dialog: ProgressDialog? = null
    private var path : String = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddVehicleBinding.inflate(inflater, container, false)

        dialog = ProgressDialog(activity)
        dialog!!.setMessage("Please wait Loading...")
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)

        SharedPreference.initialize(activity)
        AppController.initialize(activity)

        binding.imgAddVehicle.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                    null
                }
        }


        binding.btnAddVehicle.setOnClickListener {
            vehicle_name=binding.edtVehicleName.text.toString().trim()
            vehicle_description=binding.edtVehicleDescription.text.toString().trim()
            vehicle_rate=binding.edtVehicleRent.text.toString().trim()

            if(path.equals("")){
                activity!!.toast("Please select photo")
            }else if (vehicle_name.isNullOrEmpty() && vehicle_description.isNullOrEmpty() && vehicle_rate.isNullOrEmpty()){
                Toast.makeText(activity,resources.getString(R.string.please_fill_details),
                    Toast.LENGTH_SHORT).show()
            }else if(!binding.spinnerType.selectedItem.toString().equals("Select Type")) {
                addProduct(vehicle_name!!, vehicle_description!!, vehicle_rate!!, binding.spinnerType.selectedItemPosition)
               /* Toast.makeText(activity,resources.getString(R.string.details_add_successfully),
                    Toast.LENGTH_SHORT).show()*/
            }else{
                activity!!.toast("Please select type.")
            }
        }

        return binding.root

    }

    private fun addProduct(vehicleName: String, vehicleDescription: String, vehicleRate: String, ptype: Int) {
        dialog!!.show()
        val image = UUID.randomUUID().toString()
        val config = UploadNotificationConfig()
        config.getCompleted().autoClear = true
        config.setTitleForAllStatuses("Uploading") //TAKE VIDEO NAME FROM VIDEO PATH

        config.setIconForAllStatuses(R.mipmap.ic_launcher)

        MultipartUploadRequest(activity, image, Keys.Owner.OWNER_ADD_PRODUCT)
            .addFileToUpload(path, "p_photo")
            .addParameter("o_id", SharedPreference.get("o_id"))
            .addParameter("p_type", ptype.toString())
            .addParameter("p_name", vehicleName)
            .addParameter("p_description", vehicleDescription)
            .addParameter("p_rent", vehicleRate)
            .setMaxRetries(1)
            .setNotificationConfig(config)
            .setDelegate(object : UploadStatusDelegate {
                override fun onProgress(context: Context?, uploadInfo: UploadInfo?) {
                    Log.i("tag", "on progress upload")
                }

                override fun onError(
                    context: Context?,
                    uploadInfo: UploadInfo?,
                    serverResponse: ServerResponse?,
                    exception: Exception
                ) {
                    Log.i("tag", "on error upload" + "error")
                    exception.printStackTrace()
                    dialog!!.dismiss()
                    // exception.printStackTrace();
                    activity!!.toast("Please try again later, poor internet connection")
                }

                override fun onCompleted(
                    context: Context?,
                    uploadInfo: UploadInfo?,
                    serverResponse: ServerResponse
                ) {
                    dialog!!.dismiss();
                    Log.i("tag", "on completed upload")
                    Log.i("tag", serverResponse.getBodyAsString())
                    try {
                        //   dialog.dismiss();
                        val jsonObject = JSONObject(serverResponse.getBodyAsString())
                        if (jsonObject.get("success") == "1") {
                            clearField()
                        }
                        activity!!.toast(jsonObject.optString("message"))

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onCancelled(context: Context?, uploadInfo: UploadInfo?) {
                    dialog!!.dismiss()
                    Log.i("tag", "onCancelled")
                }
            })
            .startUpload()

    }

    private fun clearField() {
        binding.edtVehicleName.setText("")
        binding.edtVehicleRent.setText("")
        binding.edtVehicleDescription.setText("")
        binding.imgAddVehicle.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_a_photo_24))
        path = ""
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                path = fileUri.path!!
                //path1 = ImagePicker.Companion.get
                //mProfileUri = fileUri
                binding.imgAddVehicle.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}