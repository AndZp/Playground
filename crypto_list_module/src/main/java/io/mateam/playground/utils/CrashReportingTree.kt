package io.mateam.playground.utils

import android.util.Log
import com.crashlytics.android.Crashlytics
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {

  companion object {
    private const val CRASHLYTICS_KEY_PRIORITY = "priority"
    private const val CRASHLYTICS_KEY_TAG = "tag"
    private const val CRASHLYTICS_KEY_MESSAGE = "message"

  }

  override fun log(
    priority: Int,
    tag: String?,
    message: String,
    t: Throwable?
  ) {
    if (priority == Log.VERBOSE || priority == Log.DEBUG) {
      return
    }

    val throwable = t ?: Exception(message)

    // Crashlytics
    Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority)
    Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag)
    Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message)
    Crashlytics.logException(throwable)
  }
}