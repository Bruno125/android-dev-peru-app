package peru.android.dev.androidutils

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

inline fun <reified T: AppCompatActivity> AppCompatActivity.start(extras: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    extras?.let { intent.putExtras(it) }
    startActivity(intent)
}