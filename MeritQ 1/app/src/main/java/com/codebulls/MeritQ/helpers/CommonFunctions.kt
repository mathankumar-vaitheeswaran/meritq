package com.codebulls.MeritQ.helpers

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Vibrator
import android.preference.PreferenceManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*

class CommonFunctionsHelper(private var appContext: Context) {

    val pref_Manager = PreferenceManager.getDefaultSharedPreferences(appContext)

    companion object {
        private val DUMMY = "Dummy"
    }

    fun beepOnce() {
        val audioMan = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (audioMan.ringerMode == AudioManager.RINGER_MODE_NORMAL) {
            val tg = ToneGenerator(AudioManager.STREAM_NOTIFICATION, (ToneGenerator.MAX_VOLUME * 0.85).toInt())
            tg.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
        }
    }

    fun getDateTime(): String {
        return java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
    }

    fun getLoginId(): String {
        return pref_Manager.getString("pref_logged_id", "")
    }

}