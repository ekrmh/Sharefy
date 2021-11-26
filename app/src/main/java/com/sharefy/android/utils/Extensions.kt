import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.sharefy.android.base.validator.base.ValidateResult
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


fun MaterialButton.multipleValidations(
    buttonClickListener: () -> HashMap<TextInputLayout, ValidateResult>,
    onSuccessCallback: () -> Unit
) {
    this.setOnClickListener {
        val inputs = buttonClickListener.invoke()
        var canContinue = true
        inputs.forEach { entry: Map.Entry<TextInputLayout, ValidateResult> ->
            if (!entry.value.isSuccess) {
                canContinue = false
                entry.key.error = context.getString(entry.value.message)
            } else
                entry.key.error = null
        }

        if (!canContinue)
            return@setOnClickListener
        else
            onSuccessCallback.invoke()
    }

}