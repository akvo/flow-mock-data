package org.akvo.flow.mock

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.akvo.flow.mock.util.FileUtils.copyFile
import org.akvo.flow.mock.util.FileUtils.createFile

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
        val file = createFile("akvoflow/inbox", "bootstrap.zip")
        copyFile(input, file)
        Toast.makeText(this, "Done! Launch the flow app to import the bootstrap file", Toast.LENGTH_LONG).show()
    }
}
