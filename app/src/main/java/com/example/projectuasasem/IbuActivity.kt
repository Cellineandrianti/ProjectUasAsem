import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import com.example.projectuasasem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_ibu.*

class IbuActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ibu)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Mendapatkan informasi pengguna yang masuk
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            // Memuat data anak-anak ibu dari database
            loadChildrenData(userId)
        }

        // Tombol untuk menambahkan anak
        tambahAnakButton.setOnClickListener {
            // Mendapatkan jenis kelamin yang dipilih
            val jenisKelaminId = jenisKelaminRadioGroup.checkedRadioButtonId
            val jenisKelaminRadioButton = findViewById<RadioButton>(jenisKelaminId)
            val jenisKelamin = jenisKelaminRadioButton.text.toString()

            // Mendapatkan umur yang dipilih
            val selectedUmur = umurSpinner.selectedItem.toString()

            if (userId != null) {
                // Menambahkan anak ke database
                addChildToDatabase(userId, jenisKelamin, selectedUmur)
            }
        }

        // Tombol untuk mengatur jadwal anak
        aturJadwalButton.setOnClickListener {
            // Tombol untuk mengatur jadwal anak
            aturJadwalButton.setOnClickListener {
                // Simpan jadwal mandi, makan, dan tidur ke Firebase Database di sini

                // Contoh pengaturan waktu mandi, makan, dan tidur
                val mandiTime = // Waktu mandi yang telah diatur oleh ibu (dalam timestamp)
                val makanTime = // Waktu makan yang telah diatur oleh ibu (dalam timestamp)
                val tidurTime = // Waktu tidur yang telah diatur oleh ibu (dalam timestamp)

                val jadwalAnakRef = database.child("jadwal_anak").child(userId)

                // Simpan jadwal mandi
                val mandiData = HashMap<String, Any>()
                mandiData["kegiatan"] = "mandi"
                mandiData["waktu"] = mandiTime
                jadwalAnakRef.child("mandi").setValue(mandiData)

                // Simpan jadwal makan
                val makanData = HashMap<String, Any>()
                makanData["kegiatan"] = "makan"
                makanData["waktu"] = makanTime
                jadwalAnakRef.child("makan").setValue(makanData)

                // Simpan jadwal tidur
                val tidurData = HashMap<String, Any>()
                tidurData["kegiatan"] = "tidur"
                tidurData["waktu"] = tidurTime
                jadwalAnakRef.child("tidur").setValue(tidurData)

                // Anda perlu mengganti pengaturan waktu (mandiTime, makanTime, tidurTime) sesuai dengan pengaturan yang diinginkan oleh ibu.
            }
        }

        // Inisialisasi Spinner untuk umur
        val umurOptions = ArrayList<String>()
        for (i in 1..12) {
            umurOptions.add("$i tahun")
        }

        val umurAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, umurOptions)
        umurAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        umurSpinner.adapter = umurAdapter
    }

    private fun loadChildrenData(userId: String) {
        // Mengambil data anak-anak dari database
        val anakRef = database.child("anak").child(userId)

        anakRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Data anak ditemukan, implementasikan logika untuk menampilkan data anak-anak di sini
                } else {
                    // Tidak ada data anak, mungkin ibu perlu menambahkan anak terlebih dahulu
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle kesalahan jika ada
                Toast.makeText(this@IbuActivity, "Terjadi kesalahan saat memuat data anak.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addChildToDatabase(userId: String, jenisKelamin: String, umur: String) {
        // Implementasikan logika untuk menambahkan data anak ke dalam database
        // Pastikan untuk memeriksa kembali bahwa userId tidak null
        val anakRef = database.child("anak").child(userId)
        val anakId = anakRef.push().key // Membuat ID anak baru

        if (anakId != null) {
            val anakData = HashMap<String, Any>()
            anakData["jenisKelamin"] = jenisKelamin
            anakData["umur"] = umur

            // Simpan data anak di bawah ID anak yang baru dibuat
            anakRef.child(anakId).setValue(anakData)
        }
    }
}
