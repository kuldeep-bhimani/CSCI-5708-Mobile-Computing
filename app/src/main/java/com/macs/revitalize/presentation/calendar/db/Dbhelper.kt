package com.example.calenderappdemo.db

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class Dbhelper(context: Context?) :
    SQLiteOpenHelper(context, DbStructure.DB_NAME, null, DbStructure.DB_VERSION) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_EVENT_TABLE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL(DROP_EVENT_TABLE)
        onCreate(sqLiteDatabase)
    }

    fun Save_Event(
        event: String?,
        time: String?,
        date: String?,
        month: String?,
        year: String?,
        notify: String?,
        sqLiteDatabase: SQLiteDatabase
    ) {
        val contentValues = ContentValues()
        contentValues.put(DbStructure.EVENT, event)
        contentValues.put(DbStructure.TIME, time)
        contentValues.put(DbStructure.DATE, date)
        contentValues.put(DbStructure.MONTH, month)
        contentValues.put(DbStructure.YEAR, year)
        contentValues.put(DbStructure.Notify, notify)
        sqLiteDatabase.insert(DbStructure.EVENT_TABLE_NAME, null, contentValues)
    }

    fun Read_Event(date: String, sqLiteDatabase: SQLiteDatabase): Cursor {
        val projection = arrayOf(
            DbStructure.EVENT,
            DbStructure.TIME,
            DbStructure.DATE,
            DbStructure.MONTH,
            DbStructure.YEAR
        )
        val selection = DbStructure.DATE + "=?"
        val selearg = arrayOf(date)
        return sqLiteDatabase.query(
            DbStructure.EVENT_TABLE_NAME,
            projection,
            selection,
            selearg,
            null,
            null,
            null
        )
    }

    fun Read_IDEvent(
        date: String,
        event: String,
        time: String,
        sqLiteDatabase: SQLiteDatabase
    ): Cursor {
        val projection = arrayOf(DbStructure.ID, DbStructure.Notify)
        val selection =
            DbStructure.DATE + "=? " + " AND " + DbStructure.EVENT + " =?" + " AND " + DbStructure.TIME + " =?"
        val selearg = arrayOf(date, event, time)
        return sqLiteDatabase.query(
            DbStructure.EVENT_TABLE_NAME,
            projection,
            selection,
            selearg,
            null,
            null,
            null
        )
    }

    fun Read_Event_Supermonth(month: String, year: String, sqLiteDatabase: SQLiteDatabase): Cursor {
        val projection = arrayOf(
            DbStructure.EVENT,
            DbStructure.TIME,
            DbStructure.DATE,
            DbStructure.MONTH,
            DbStructure.YEAR
        )
        val selection = DbStructure.MONTH + "=? " + " AND " + DbStructure.YEAR + " =?"
        val selearg = arrayOf(month, year)
        return sqLiteDatabase.query(
            DbStructure.EVENT_TABLE_NAME,
            projection,
            selection,
            selearg,
            null,
            null,
            null
        )
    }

    fun Delete_Event(event: String, date: String, time: String, sqLiteDatabase: SQLiteDatabase) {
        val selection =
            DbStructure.EVENT + "=? " + " AND " + DbStructure.DATE + " =?" + " AND " + DbStructure.TIME + " =?"
        val selearg = arrayOf(event, date, time)
        sqLiteDatabase.delete(DbStructure.EVENT_TABLE_NAME, selection, selearg)
    }

    fun Update_Event(
        date: String,
        event: String,
        time: String,
        Notify: String?,
        sqLiteDatabase: SQLiteDatabase
    ) {
        val contentValues = ContentValues()
        contentValues.put(DbStructure.Notify, Notify)
        val selection =
            DbStructure.DATE + "=? " + " AND " + DbStructure.EVENT + " =?" + " AND " + DbStructure.TIME + " =?"
        val selearg = arrayOf(date, event, time)
        sqLiteDatabase.update(DbStructure.EVENT_TABLE_NAME, contentValues, selection, selearg)
    }

    companion object {
        var CREATE_EVENT_TABLE =
            ("CREATE TABLE " + DbStructure.EVENT_TABLE_NAME + "(" + "ID INTEGER PRIMARY KEY AUTOINCREMENT," + DbStructure.EVENT + " TEXT," + DbStructure.TIME + " TEXT,"
                    + DbStructure.DATE + " TEXT,"
                    + DbStructure.MONTH + " TEXT,"
                    + DbStructure.YEAR + " TEXT,"
                    + DbStructure.Notify + " TEXT"
                    + ")")
        var DROP_EVENT_TABLE = "DROP TABLE IF EXISTS " + DbStructure.EVENT_TABLE_NAME
    }
}