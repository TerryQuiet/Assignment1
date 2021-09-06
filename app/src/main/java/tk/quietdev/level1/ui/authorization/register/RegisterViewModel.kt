package tk.quietdev.level1.ui.authorization.register


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tk.quietdev.level1.R
import tk.quietdev.level1.api.ShppApi
import tk.quietdev.level1.models.shppApi.AuthUser
import tk.quietdev.level1.models.shppApi.RegisterResponse
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
     @ApplicationContext private val androidContext: Context,
    private val api: ShppApi,
) : ViewModel() {

    val isErrorShown = MutableLiveData(false)
    val regResponce = MutableLiveData(RegisterResponse.Status.NULL)
    var errorMessage = ""

    fun regUser(email: String, passwd: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                regResponce.value = RegisterResponse.Status.ONGOING
            }
            val response = api.createEmployee(AuthUser(email = email, password = passwd))
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    regResponce.value = RegisterResponse.Status.OK
                }
            } else {
                val unit = try {
                    val moshi = Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                        .adapter(RegisterResponse::class.java)
                    val body = response.errorBody()?.string()
                    body?.let {
                        Log.d("WTF", "regUser: $body")
                        moshi.fromJson(body)?.message?.let {
                            errorMessage = it
                        }
                    }
                } catch (e: Exception) {
                    errorMessage = androidContext.getString(R.string.show_unknown_error)
                }
                withContext(Dispatchers.Main) {
                    regResponce.value = RegisterResponse.Status.BAD
                }
            }
        }
    }


}