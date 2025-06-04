package com.example.safeshare.ui.screens.main.upload

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.safeshare.R
import com.example.safeshare.utils.annotations.HorizontalScreenPreview
import com.example.safeshare.utils.annotations.VerticalScreenPreview

@Composable
fun UploadScreen() {
    val context = LocalContext.current
    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                Log.d("Upload Screen","Uri : $it")
            }
        }
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        IconButton(
            onClick = {
                // TODO: upload files
                filePicker.launch(arrayOf("image/*", "video/*", "application/pdf"))
            },
            modifier = Modifier.defaultMinSize(
                minHeight = dimensionResource(R.dimen.min_clickable_size),
                minWidth = dimensionResource(R.dimen.min_clickable_size)
            )
        ) {
            Icon(
                Icons.Outlined.CloudUpload, contentDescription = "Upload files",
                modifier = Modifier.size(120.dp)
            )
        }
        Text(text = "Select file to upload store it securely !")
    }
}

@Composable
@VerticalScreenPreview
fun UploadScreenVertical() {
    UploadScreen()
}

@Composable
@HorizontalScreenPreview
fun UploadScreenHorizontal() {
    UploadScreen()
}
