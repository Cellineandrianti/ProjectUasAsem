import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_store.*

class StoreActivity : AppCompatActivity() {

    private var coins = 0 // Jumlah koin yang dimiliki oleh anak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        // Mendapatkan jumlah koin dari database atau preferensi
        // Misalnya, Anda dapat mengambil jumlah koin dari Firebase Database
        // atau menyimpannya dalam SharedPreferences

        // Mengatur tampilan jumlah koin awal
        coinsTextView.text = "Koin: $coins"

        // Menambahkan aksi klik pada tombol untuk membeli item
        buyItemButton.setOnClickListener {
            // Implementasikan logika untuk memproses pembelian item
            // Misalnya, mengurangkan jumlah koin dan menyimpannya kembali ke database
            if (canPurchaseItem()) {
                performPurchase()
            } else {
                // Tampilkan pesan jika koin tidak mencukupi
                showToast("Koin tidak mencukupi")
            }
        }
    }

    private fun canPurchaseItem(): Boolean {
        // Implementasikan logika untuk memeriksa apakah anak dapat membeli item
        // Misalnya, periksa apakah jumlah koin mencukupi
        return coins >= getItemPrice()
    }

    private fun getItemPrice(): Int {
        // Implementasikan logika untuk mendapatkan harga item
        // Misalnya, harga item dapat bervariasi berdasarkan jenis item
        return 10 // Contoh: Harga item adalah 10 koin
    }

    private fun performPurchase() {
        // Implementasikan logika untuk melakukan pembelian
        // Misalnya, kurangkan jumlah koin, simpan pembelian ke database, dan perbarui tampilan

        // Mengurangkan jumlah koin
        coins -= getItemPrice()

        // Menyimpan jumlah koin yang baru ke database atau preferensi
        // Misalnya, simpan jumlah koin ke Firebase Database atau SharedPreferences

        // Menampilkan jumlah koin yang diperbarui
        coinsTextView.text = "Koin: $coins"

        // Tampilkan pesan pembelian berhasil
        showToast("Item telah berhasil dibeli")
    }

    private fun showToast(message: String) {
        // Implementasikan logika untuk menampilkan pesan toast
        // Misalnya, menggunakan Toast.makeText()
    }
}
