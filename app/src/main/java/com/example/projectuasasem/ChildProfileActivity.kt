import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projectuasasem.R

class ChildProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.child_profile_activity)

        // Mendapatkan data anak dari Intent
        val namaAnak = intent.getStringExtra("namaAnak")
        val umurAnak = intent.getStringExtra("umurAnak")
        val karakterAnakId = intent.getIntExtra("karakterAnakId", R.drawable.default_character)

        // Inisialisasi tampilan
        val karakterAnakImageView = findViewById<ImageView>(R.id.karakterAnakImageView)
        val namaAnakTextView = findViewById<TextView>(R.id.namaAnakTextView)
        val umurAnakTextView = findViewById<TextView>(R.id.umurAnakTextView)
        val backButton = findViewById<Button>(R.id.backButton)

        // Mengatur tampilan sesuai data anak
        karakterAnakImageView.setImageResource(karakterAnakId)
        namaAnakTextView.text = "Nama Anak: $namaAnak"
        umurAnakTextView.text = "Umur Anak: $umurAnak"

        // Mengatur tombol kembali
        backButton.setOnClickListener {
            finish()
        }
    }
}
