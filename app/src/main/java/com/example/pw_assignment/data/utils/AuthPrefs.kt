package com.example.pw_assignment.data.utils

import android.content.Context

object AuthPrefs {
    private const val PREF_NAME = "auth_pref"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_SCHOOL_ID = "school_id"
    private const val KEY_STUDENT_ID = "student_id"

    /**
     * Save login state and user credentials
     */
    fun setLoggedIn(context: Context, isLoggedIn: Boolean, schoolId: String = "", studentId: String = "") {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            if (isLoggedIn) {
                putString(KEY_SCHOOL_ID, schoolId)
                putString(KEY_STUDENT_ID, studentId)
            }
            apply()
        }
    }

    /**
     * Check if user is logged in
     */
    fun isLoggedIn(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    /**
     * Get stored School ID
     */
    fun getSchoolId(context: Context): String? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_SCHOOL_ID, null)
    }

    /**
     * Get stored Student ID
     */
    fun getStudentId(context: Context): String? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_STUDENT_ID, null)
    }

    /**
     * Clear all authentication data
     */
    fun logout(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}

