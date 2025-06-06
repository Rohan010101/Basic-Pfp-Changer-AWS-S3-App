package com.example.aws_profile_picture_app

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Pfp() {
    Log.d("pfp","pfp started")

    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<String?>(null) }
    var uploadInProgress by remember { mutableStateOf(false) }



    // Activity Launcher to pick Image
    Log.d("pfp","val launcher will start")
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {

            uploadInProgress = true


            Log.d("pfp","uri?.let")

            Log.d("pfp","uri = $uri")


            // Upload Image
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    Log.d("pfp", "CoroutineScope launched")

                    Log.d("pfp", "uploadProfilePicture will start")
                    uploadProfilePicture(context, uri)
                    Log.d("pfp", "uploadProfilePicture finished")

                    Log.d("pfp", "loadProfilePictureUrl will start")
                    selectedImageUri = loadProfilePictureUrl()
                    Log.d("pfp", "loadProfilePictureUrl finished")

                    Log.d("pfp", "CoroutineScope finished")
                } catch (e: Exception) {
                    Log.e("Upload", "Error: ${e.message}")
                } finally {
                    uploadInProgress = false
                }


            }

//            selectedImageUri = it
//            uploadInProgress = true
//            uploadPfpToServer(context, it) {
//                uploadInProgress = false
//            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            if (selectedImageUri == null) {
                Log.d("pfp","selectedImageUri == null")
                // EMPTY DEFAULT CIRCLE
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            } else {
                Log.d("pfp","selectedImageUri != null")
                selectedImageUri?.let {
                        Log.d("pfp","selectedImageUri != null")
                        Image(
                            painter = rememberAsyncImagePainter(model = it),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.LightGray, CircleShape)
                        )
                    }
                }

//            if (imageUri == null) {
//                // EMPTY DEFAULT CIRCLE
//                Box(
//                    modifier = Modifier
//                        .size(150.dp)
//                        .clip(CircleShape)
//                        .background(Color.LightGray)
//                )
//            } else {
//                Image(
//                    painter = rememberAsyncImagePainter(imageUri),
//                    contentDescription = "Selected Image",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.size(150.dp).clip(CircleShape)
//                )
//            }




//            AsyncImage(
//                model = selectedImageUri ?: imageUrl,
//                contentDescription =  "Profile Picture",
//                modifier = Modifier
//                    .size(150.dp)
//                    .clip(CircleShape)
//                    .background(Color.LightGray),
//                contentScale = ContentScale.Crop
//            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    Log.d("Pfp", "Button Clicked")
                    launcher.launch("image/*") },
                enabled = !uploadInProgress
            ) {
                Text( if (uploadInProgress) "Uploading..." else "Change Profile Picture")
//                Text( "Change Profile Picture")
            }
        }
    }
}



//@SuppressLint("Recycle")
//suspend fun uploadImage(uri: Uri, context: Context) {
//    val inputStream = context.contentResolver.openInputStream(uri)
//    val bytes = inputStream?.readBytes() ?: return
//    val requestBody = bytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
//    val part = MultipartBody.Part.createFormData(
//        name = "file",
//        filename = "pfp.jpg",
//        body = requestBody
//    )
//
//    val response = service.uploadProfilePicture(part)
//    if (response.isSuccessful) {
//        Log.d("Upload","Success")
//    } else {
//        Log.e("Upload", "Failed: ${response.code()}")
//    }
//}