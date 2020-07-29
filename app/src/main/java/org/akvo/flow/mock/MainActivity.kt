package org.akvo.flow.mock

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.akvo.flow.mock.util.FileUtils.copyFile
import org.akvo.flow.mock.util.FileUtils.createFile

class MainActivity : AppCompatActivity() {

    private companion object {
        const val STORAGE_PERMISSION_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bootstrap_button.setOnClickListener {
            generateBootstrapFiles()
        }

        if (!writePermissionGranted()) {
            requestWritePermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            STORAGE_PERMISSION_REQUEST -> {
                if ((grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Write permission needed, app will not work without it", Toast.LENGTH_LONG).show()
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
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun generateBootstrapFiles() {
        val input = resources.openRawResource(R.raw.bootstrap)
        val file = createFile("akvoflow/inbox", "bootstrap.zip")
        copyFile(input, file)
        Toast.makeText(this, "Done! Launch the flow app to import the bootstrap file", Toast.LENGTH_LONG).show()
    }
}
