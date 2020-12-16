package com.hjw.fundplan.net

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hjw.fundplan.entity.FundHaveRecordBean
import com.hjw.fundplan.entity.FundSearchRecordBean
import com.hjw.fundplan.entity.MyFundBean
import com.hjw.fundplan.net.dao.FundHaveRecordBeanDao
import com.hjw.fundplan.net.dao.FundSearchRecordBeanDao
import com.hjw.fundplan.net.dao.MyFundBeanDao

@Database(entities = [
    MyFundBean::class,
    FundSearchRecordBean::class,
    FundHaveRecordBean::class
], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val myFundBean: MyFundBeanDao
    abstract val fundSearchRecordBeanDao: FundSearchRecordBeanDao
    abstract val fundHaveRecordBeanDao: FundHaveRecordBeanDao

    companion object {
//        val MIGRATION_15_16: Migration = object : Migration(15, 16) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE DocumentFile ADD COLUMN isRead INTEGER NOT NULL DEFAULT 0")
//            }
//        }
//        val MIGRATION_22_23: Migration = object : Migration(22, 23) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE FuncStraConfigBean ADD COLUMN workDate STRING")
//            }
//        }
    }
}