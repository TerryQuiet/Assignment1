package tk.quietdev.level1.utils

import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

object CalendarUtil {
    private val today = MaterialDatePicker.todayInUtcMilliseconds()
    private val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    fun getConstrains(): CalendarConstraints.Builder {
        calendar.timeInMillis = today
        calendar[Calendar.MONTH] = Calendar.JANUARY
        calendar[Calendar.YEAR] = 1920
        val jan1920 = calendar.timeInMillis
        calendar.timeInMillis = today
        calendar[Calendar.YEAR] = calendar[Calendar.YEAR] - 16
        val age16 = calendar.timeInMillis

        return CalendarConstraints.Builder()
            .setStart(jan1920)
            .setEnd(age16)
            .setOpenAt(age16)
            .setValidator(DateValidatorPointBackward.before(age16))
    }


}