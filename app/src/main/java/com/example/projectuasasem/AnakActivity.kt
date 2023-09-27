import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AnakActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anak)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            // Memeriksa jadwal mandi dari Firebase Database
            checkMandiSchedule(userId)
        }
    }

    private fun checkMandiSchedule(userId: String) {
        val jadwalAnakRef = database.child("jadwal_anak").child(userId).child("mandi")

        jadwalAnakRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val jadwalMandi = dataSnapshot.getValue(JadwalAnak::class.java)
                    val currentTime = System.currentTimeMillis()

                    if (jadwalMandi != null && jadwalMandi.waktu <= currentTime) {
                        // Jadwal mandi sudah lewat, karakter anak menjadi kotor
                        // Ubah karakter anak menjadi kotor di sini
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle kesalahan jika ada
                Toast.makeText(this@AnakActivity, "Terjadi kesalahan saat memuat jadwal mandi.", Toast.LENGTH_SHORT).show()
            }
        })
    }

        // Mendapatkan referensi karakter anak (ImageView)
        val karakterAnakImageView = findViewById<ImageView>(R.id.karakterAnakImageView)

        // Mendapatkan data karakter anak dari Firebase Database
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val anakRef = database.child("anak").child(userId)

            anakRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Mendapatkan data karakter anak dari Firebase Database
                        val anak = dataSnapshot.getValue(Anak::class.java)

                        // Tampilkan karakter anak di ImageView
                        if (anak != null) {
                            // Misalnya, Anda dapat mengatur gambar karakter anak berdasarkan URL
                            // menggunakan Glide atau Picasso
                            // Glide.with(this@AnakActivity).load(anak.karakter).into(karakterAnakImageView)

                            // Jika Anda memiliki gambar karakter yang ada di aplikasi,
                            // Anda juga dapat mengaturnya langsung menggunakan setImageResource:
                            // karakterAnakImageView.setImageResource(anak.karakterResource)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle kesalahan jika ada
                    Toast.makeText(this@AnakActivity, "Terjadi kesalahan saat memuat data karakter anak.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
