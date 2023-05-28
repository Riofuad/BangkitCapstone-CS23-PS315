package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.databinding.ActivityDetectResultBinding
import academy.bangkit.sifresh.utils.Helper
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import java.io.File

class DetectResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectResultBinding
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDetectResultData()
    }

    private fun setDetectResultData() {
        val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getSerializableExtra(EXTRA_PHOTO_RESULT, File::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent?.getSerializableExtra(EXTRA_PHOTO_RESULT)
        } as File

        val isBackCamera = intent?.getBooleanExtra(EXTRA_CAMERA_MODE, true) as Boolean

        binding.apply {
            myFile.let { file ->
                Helper.rotateFile(file, isBackCamera)
                getFile = file
//            binding.imgResult.setImageBitmap(BitmapFactory.decodeFile(file.path))
                Glide.with(imgResult)
                    .load(BitmapFactory.decodeFile(file.path))
                    .into(imgResult)
            }

            btnBack.setOnClickListener {
                finish()
            }

            btnRecheck.setOnClickListener {
                onBackPressed()
                val intent = Intent(this@DetectResultActivity, CameraActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_PHOTO_RESULT = "PHOTO_RESULT"
        const val EXTRA_CAMERA_MODE = "CAMERA_MODE"
    }
}