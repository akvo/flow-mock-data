package org.akvo.flow.mock

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.akvo.flow.mock.util.Base32
import org.akvo.flow.mock.util.Data
import org.akvo.flow.mock.util.FileUtils.copyFile
import org.akvo.flow.mock.util.FileUtils.copyInputStreamToFile
import org.akvo.flow.mock.util.FileUtils.createFile
import org.akvo.flow.mock.util.FileUtils.obtainFolder
import org.akvo.flow.mock.util.FileUtils.readZipEntry
import org.akvo.flow.mock.util.FileUtils.writeToZipFile
import timber.log.Timber
import java.io.File
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private companion object {
        const val STORAGE_PERMISSION_REQUEST = 1
        const val NUMBER_OF_FORM_SUBMISSION_COPIES = 1
    }

    private var job = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bootstrap_button.setOnClickListener {
            generateBootstrapFiles()
        }

        data_button.setOnClickListener {
            generateDataFiles()
        }

        if (!writePermissionGranted()) {
            requestWritePermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            STORAGE_PERMISSION_REQUEST -> {
                if ((grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this,
                        "Write permission needed, app will not work without it",
                        Toast.LENGTH_LONG).show()
                    finish()
                }
                return
            }
        }
    }

    private fun requestWritePermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_REQUEST)
    }

    private fun writePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun generateBootstrapFiles() {
        val input = resources.openRawResource(R.raw.bootstrap)
        val file = createFile("akvoflow/inbox", "bootstrap.zip")
        copyInputStreamToFile(input, file)
        Toast.makeText(this,
            "Done! Launch the flow app to import the bootstrap file",
            Toast.LENGTH_LONG).show()
    }

    private fun generateDataFiles() {
        //example: /storage/emulated/0/akvoflow/inbox/published/files/16faeb8f-4d16-4981-b771-e743add054b5.zip
        uiScope.launch {
            when (createDummyFiles()) {
                0 -> Toast.makeText(applicationContext,
                    "Done! Zip file is in the inbox folder",
                    Toast.LENGTH_LONG)
                    .show()
                else -> Toast.makeText(applicationContext,
                    "Add at least one instance zip file into: akvoflow/inbox/published/files to copy",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun createDummyFiles(): Int {
        return withContext(Dispatchers.IO) {
            val destinationDataFolder: File? = obtainFolder("akvoflow/inbox/published/files")
            val mediaFolder: File? = obtainFolder("akvoflow/inbox/published/media")
            val file = getInstanceFileToCopy(destinationDataFolder)
            if (file != null) {
                val gson = Gson()
                val data = gson.fromJson(readZipEntry(file), Data::class.java)
                Timber.d("${data.dataPointId}, ${data.uuid}")
                for (i in 1..NUMBER_OF_FORM_SUBMISSION_COPIES) {
                    data.uuid = UUID.randomUUID().toString()
                    for (r in data.responses) {
                        if (r.answerType == "IMAGE") {
                            val split = r.value.split("/")
                            val last: String = split.last()
                            val fileName: String = last.substring(0, last.indexOf("\""))
                            Timber.d("Filename: $fileName")
                            val randomName = UUID.randomUUID().toString() + ".jpg"
                            copyFile(File(mediaFolder, fileName), File(mediaFolder, randomName))
                            r.value = r.value.replace(fileName, randomName)
                        }
                    }
                    writeToZipFile(destinationDataFolder,
                        gson.toJson(data, Data::class.java),
                        data.uuid + ".zip")
                }
                0
            } else {
                1
            }
        }
    }

    fun datapointId(): String? {
        val base32Id: String = Base32().base32Uuid()
        // Put dashes between the 4-5 and 8-9 positions to increase readability
        return base32Id.substring(0, 4) + "-" + base32Id.substring(4, 8) + "-" + base32Id
            .substring(8)
    }

    private fun getInstanceFileToCopy(destinationDataFolder: File?): File? {
        return if (destinationDataFolder != null && destinationDataFolder.exists()) {
            destinationDataFolder.listFiles()?.let { it[0] }
        } else {
            null
        }
    }
}
