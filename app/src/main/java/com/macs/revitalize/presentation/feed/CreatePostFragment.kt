package com.macs.revitalize.presentation.feed

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.auth.User
import com.macs.revitalize.R
import kotlinx.coroutines.delay


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreatePostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreatePostFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_post, container, false)
    }

//    fun imagechoser(){
//        val i = Intent()
//        i.type = "image/*"
//        i.action = Intent.ACTION_GET_CONTENT
//
//        // pass the constant to compare it
//        // with the returned requestCode
//
//        // pass the constant to compare it
//        // with the returned requestCode
//        val intent = Intent()
//
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
//
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode === RESULT_OK) {
//
//            // compare the resultCode with the
//            // SELECT_PICTURE constant
//            if (requestCode === SELECT_PICTURE) {
//                // Get the url of the image from data
//                val selectedImageUri: Uri = attr.data.getData()
//                if (null != selectedImageUri) {
//                    // update the preview image in the layout
//                    IVPreviewImage.setImageURI(selectedImageUri)
//                }
//            }
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val storagePath = File(Environment.getExternalStorageDirectory().toString() + "/MyPhotos/")
//        storagePath.mkdirs()
//        val myImage =
//            File(storagePath, "/Users/pavithragunasekaran/Dalhousie/sem3/MC/git/macs-04/app/src/main/res/drawable/share_cart.png")
//        try {
//            val out = FileOutputStream(myImage)
//            out.write(myImage.readBytes())
//            println("Image uploaded")
////            outputImage.compress(Bitmap.CompressFormat.JPEG, 80, out)
//            out.flush()
//            out.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
        super.onViewCreated(view, savedInstanceState)
        val imageView=view.findViewById<ImageView>(R.id.IVPreviewImage)
        val selectImageFromGallery = view.findViewById<Button>(R.id.BSelectImage)
        selectImageFromGallery.setOnClickListener {
            val imgUri = Uri.parse("android.resource://com.macs.revitalize/drawable/habits")
            imageView.setImageURI(imgUri)
            UserPostFragment.data.add(FeedViewModel(R.drawable.habits, "Item"  ))
            println(UserPostFragment)
            FeedViewModel.c++
            println("updated count : "+ FeedViewModel.c)

        }

        val submitPost = view.findViewById<Button>(R.id.submitPost)
        submitPost.setOnClickListener{
            Toast.makeText(context,"Post submitted",Toast.LENGTH_SHORT).show();

        }
        val selectImageIntent = registerForActivityResult(ActivityResultContracts.GetContent())
        { uri ->
            imageView.setImageURI(uri)

//


        }
//        val selectImageButton = view.findViewById<Button>(R.id.BSelectImage)

//        selectImageButton.setOnClickListener{
//            selectImageIntent.launch("image/*")
////
////            Handler().postDelayed({
////
////            }, 2000)
//                val imgUri = Uri.parse("android.resource://com.macs.revitalize/drawable/ic_user_post")
//                imageView.setImageURI(imgUri)
//            val data = ArrayList<FeedViewModel>()
//            data.add(FeedViewModel(imageView.drawable.alpha, "Item"  ))
//            println(data)
//            FeedViewModel.c++
//            println("updated count : "+FeedViewModel.c)
//
//
//
//
//
//
//        }

//        var selectImageIntent1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                // There are no request codes
//                val data: Intent? = result.data
//
//                imageView.setImageResource(data.toString())
//
//
//
//            }
//        }






    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreatePostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreatePostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}