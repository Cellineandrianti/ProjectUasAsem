import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_schedule_setting.*

class ScheduleSettingActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var selectedChildId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_setting)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Mendapatkan informasi pengguna yang masuk
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            // Memuat data anak-anak ibu dari database
            loadChildrenData(userId)
        }

        // Tombol untuk menyimpan jadwal
        val saveButton = findViewById<Button>(R.id.saveScheduleButton)
        saveButton.setOnClickListener {
            // Mendapatkan jadwal yang diatur oleh ibu
            val makanJam = makanJamEditText.text.toString()
            val mandiJam = mandiJamEditText.text.toString()
            val tidurJam = tidurJamEditText.text.toString()

            // Simpan jadwal ke database
            if (selectedChildId != null) {
                saveScheduleToDatabase(userId, selectedChildId!!, makanJam, mandiJam, tidurJam)
            }
        }

        // Tombol untuk memilih anak
        val selectChildButton = findViewById<Button>(R.id.selectChildButton)
        selectChildButton.setOnClickListener {
            // Implementasikan logika untuk memilih anak
            // Misalnya, tampilkan daftar anak dan biarkan ibu memilih salah satu
        }
    }

    private fun loadChildrenData(userId: String) {
        // Mengambil data anak-anak dari database
        val anakRef = database.child("anak").child(userId)

        anakRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Data anak ditemukan, implementasikan logika untuk menampilkan daftar anak-anak
                } else {
                    // Tidak ada data anak, mungkin ibu perlu menambahkan anak terlebih dahulu
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle kesalahan jika ada
                Toast.makeText(this@ScheduleSettingActivity, "Terjadi kesalahan saat memuat data anak.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveScheduleToDatabase(userId: String, childId: String, makanJam: String, mandiJam: String, tidurJam: String) {
        // Implementasikan logika untuk menyimpan jadwal ke dalam database
        // Pastikan untuk memeriksa kembali bahwa userId dan childId tidak null
        val jadwalAnakRef = database.child("jadwal_anak").child(userId).child(childId)

        val jadwalData = HashMap<String, Any>()
        jadwalData["makan"] = makanJam
        jadwalData["mandi"] = mandiJam
        jadwalData["tidur"] = tidurJam

        jadwalAnakRef.setValue(jadwalData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Jadwal berhasil disimpan.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Gagal menyimpan jadwal.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
