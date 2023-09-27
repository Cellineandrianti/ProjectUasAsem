import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_child_activity_detail.*

class ChildActivityDetailActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var selectedChildId: String? = null
    private lateinit var activityType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_activity_detail)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Mendapatkan informasi pengguna yang masuk
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        // Mendapatkan data yang dikirim dari aktivitas sebelumnya
        selectedChildId = intent.getStringExtra("childId")
        activityType = intent.getStringExtra("activityType")

        if (userId != null && selectedChildId != null) {
            // Memuat data kegiatan anak dari database dan menampilkannya
            loadChildActivityDetail(userId, selectedChildId!!, activityType)
        }
    }

    private fun loadChildActivityDetail(userId: String, childId: String, activityType: String) {
        // Mendapatkan referensi kegiatan anak dari database
        val activityRef = database.child("child_activities").child(userId).child(childId).child(activityType)

        activityRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Data kegiatan anak ditemukan, tampilkan detail kegiatan di sini
                    val activityDetail = dataSnapshot.getValue(String::class.java)
                    activityDetailTextView.text = activityDetail
                } else {
                    // Tidak ada data kegiatan, mungkin kegiatan belum diatur oleh ibu
                    activityDetailTextView.text = "Kegiatan belum diatur oleh ibu."
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle kesalahan jika ada
                activityDetailTextView.text = "Terjadi kesalahan saat memuat data kegiatan."
            }
        })
    }
}
