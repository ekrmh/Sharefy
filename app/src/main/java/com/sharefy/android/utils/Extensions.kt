import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.sharefy.android.component.popup.Popup
import com.sharefy.android.component.popup.PopupModel
import retrofit2.Retrofit

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(
        owner
    ) {
        it?.let(observer)
    }
}

fun Context.showPopup(model: PopupModel) {
    Popup(this, model).show()
}

fun Context?.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

inline fun <reified T> Retrofit.createService(): T = create(T::class.java)


