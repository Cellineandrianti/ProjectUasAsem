import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectuasasem.R
import com.google.firebase.auth.FirebaseAuth

class SettingActivity : AppCompatActivity() {

    private lateinit var parentVerificationCheckBox: CheckBox
    private lateinit var newPasswordEditText: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        auth = FirebaseAuth.getInstance()

        parentVerificationCheckBox = findViewById(R.id.parentVerificationCheckBox)
        newPasswordEditText = findViewById(R.id.newPasswordEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val changePasswordButton = findViewById<Button>(R.id.changePasswordButton)

        // Mendapatkan status preferensi saat ini
        val isParentVerificationEnabled = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            .getBoolean("parent_verification", false)
        parentVerificationCheckBox.isChecked = isParentVerificationEnabled

        saveButton.setOnClickListener {
            // Simpan preferensi saat ini
            val editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit()
            editor.putBoolean("parent_verification", parentVerificationCheckBox.isChecked)
            editor.apply()

            // Tampilkan pesan bahwa preferensi telah disimpan
            if (parentVerificationCheckBox.isChecked) {
                showToast("Verifikasi oleh Orang Tua diaktifkan")
            } else {
                showToast("Verifikasi oleh Orang Tua dinonaktifkan")
            }
        }

        changePasswordButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString()

            // Ganti kata sandi pengguna
            changeUserPassword(newPassword)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun changeUserPassword(newPassword: String) {
        val user = auth.currentUser

        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Kata sandi berhasil diubah.")
                } else {
                    showToast("Gagal mengubah kata sandi.")
                }
            }
    }
}
