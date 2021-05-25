package kz.aknur.newchildcare.common.preferences

import android.content.SharedPreferences

class UserSession(private val prefs: SharedPreferences) {

    companion object {
        const val KEY_ID = "KEY_ID"
        const val KEY_EMAIL = "KEY_EMAIL"
        const val KEY_PHONE = "KEY_PHONE"
        const val KEY_NAME = "KEY_NAME"
        const val KEY_GENDER = "KEY_GENDER"
        const val KEY_CITY_ID = "KEY_CITY_ID"
        const val KEY_ADDRESS = "KEY_ADDRESS"
        const val KEY_POLYCLINIC_ID = "KEY_POLYCLINIC_ID"
        const val KEY_IS_PREGNANT = "KEY_IS_PREGNANT"
        const val KEY_PREGNANCY_WEEK_COUNT = "KEY_PREGNANCY_WEEK_COUNT"
        const val KEY_IS_AUTHORIZE = "KEY_IS_AUTHORIZE"
        const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"
    }

    fun getAccessToken(): String? = prefs.getString(KEY_ACCESS_TOKEN, null)
    fun setAccessToken(access_token: String?) {
        prefs.edit().putString(KEY_ACCESS_TOKEN, access_token).apply()
    }

    fun getIsAuthorize(): Boolean? = prefs.getBoolean(KEY_IS_AUTHORIZE, false)
    fun setIsAuthorize(isAuthorize: Boolean?) {
        if (isAuthorize != null) {
            prefs.edit().putBoolean(KEY_IS_AUTHORIZE, isAuthorize).apply()
        }
    }

    fun getId(): Int? = prefs.getInt(KEY_ID, 0)
    fun setId(id: Int?) {
        prefs.edit().putString(KEY_ID, id.toString()).apply()
    }


    fun getEmail(): String? = prefs.getString(KEY_EMAIL, null)
    fun setEmail(email: String?) {
        prefs.edit().putString(KEY_EMAIL, email).apply()
    }

    fun getPhone(): String? = prefs.getString(KEY_PHONE, null)
    fun setPhone(phone: String?) {
        prefs.edit().putString(KEY_PHONE, phone).apply()
    }

    fun getName(): String? = prefs.getString(KEY_NAME, null)
    fun setName(name: String?) {
        prefs.edit().putString(KEY_NAME, name).apply()
    }

    fun getGender(): String? = prefs.getString(KEY_GENDER, null)
    fun setGender(gender: String?) {
        prefs.edit().putString(KEY_GENDER, gender).apply()
    }

    fun getCityId(): Int? = prefs.getInt(KEY_CITY_ID, 0)
    fun setCityId(cityId: Int?) {
        if (cityId != null) {
            prefs.edit().putInt(KEY_CITY_ID, cityId.toInt()).apply()
        }
    }

    fun getAddress(): String? = prefs.getString(KEY_ADDRESS, null)
    fun setAddress(address: String?) {
        prefs.edit().putString(KEY_ADDRESS, address.toString()).apply()
    }

    fun getPolyclinicId(): Int? = prefs.getInt(KEY_POLYCLINIC_ID, 0)
    fun setPolyclinicId(polyclinicId: Int?) {
        prefs.edit().putString(KEY_POLYCLINIC_ID, polyclinicId.toString()).apply()
    }

    fun getIsPregnant(): Boolean? = prefs.getBoolean(KEY_IS_PREGNANT, false)
    fun setIsPregnant(isPregnant: Boolean?) {
        prefs.edit().putString(KEY_IS_PREGNANT, isPregnant.toString()).apply()
    }

    fun getPregnancyWeekCount(): Int? = prefs.getInt(KEY_PREGNANCY_WEEK_COUNT, 0)
    fun setPregnancyWeekCount(PregnancyWeekCount: Int?) {
        prefs.edit().putString(KEY_PREGNANCY_WEEK_COUNT, PregnancyWeekCount.toString()).apply()
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

}