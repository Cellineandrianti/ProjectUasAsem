import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projectuasasem.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Memeriksa apakah pengguna sudah masuk (logged in)
        if (auth.currentUser != null) {
            // Pengguna sudah masuk, arahkan ke layar ibu atau anak sesuai kondisi
            // Misalnya, jika pengguna adalah ibu, arahkan ke IbuActivity
            // Jika pengguna adalah anak, arahkan ke AnakActivity
            val intent = Intent(this, IbuActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Proses autentikasi dengan Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Autentikasi berhasil
                        val user = auth.currentUser
                        // Arahkan ke layar ibu atau anak sesuai kondisi
                        // Misalnya, jika pengguna adalah ibu, arahkan ke IbuActivity
                        // Jika pengguna adalah anak, arahkan ke AnakActivity
                        val intent = Intent(this, IbuActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Autentikasi gagal, tampilkan pesan kesalahan
                        Toast.makeText(this, "Login gagal. Silakan cek email dan password.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Tombol untuk pindah ke halaman pendaftaran jika diperlukan
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}