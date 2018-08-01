package org.akvo.flow.mock

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.akvo.flow.mock.util.FileUtils
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bootstrap_button.setOnClickListener {
            generateBootstrapFiles()
        }
    }

    private fun generateBootstrapFiles() {
        val input = resources.openRawResource(R.raw.bootstrap)
        val dir = File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "akvoflow/inbox")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = File(dir, "bootstrap.zip")
        FileUtils.copyFile(input, file)
        Toast.makeText(this, "Done! Launch the flow app to import the bootstrap file", Toast.LENGTH_LONG).show()
    }
}
