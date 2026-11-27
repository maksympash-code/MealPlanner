package ua.knu.maksym_pashchenko.mealplanner.data.local.converter

import androidx.room.TypeConverter
import java.time.LocalDate


class DateConverter {

    @TypeConverter
    fun localDateToLong(localDate: LocalDate): Long {
        return localDate.toEpochDay()
    }

    @TypeConverter
    fun longToLocalDate(date: Long): LocalDate {
        return LocalDate.ofEpochDay(date)
    }
}