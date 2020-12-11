package com.hjw.fundplan.net

import android.content.Context
import androidx.room.Room
import com.hjw.fundplan.net.dao.MyFundBeanDao

class DbRepo(context: Context) {
    private val db: AppDatabase
    private val myFundBeanDao: MyFundBeanDao

    companion object {
        private val TAG = DbRepo::class.java.simpleName
    }

    init {
        db =
            Room
                .databaseBuilder(context.applicationContext, AppDatabase::class.java, "mdm_db.db")
                .fallbackToDestructiveMigration()
//                    .addMigrations(AppDatabase.MIGRATION_15_16)
//                    .addMigrations(AppDatabase.MIGRATION_22_23)
                .build()
        myFundBeanDao = db.myFundBean
    }

    /**
     * 清库
     */
    fun clearDb() {
        db.clearAllTables()
    }

    /**
     * 删除所有FundBean信息
     */
    fun deleteAllMyFundBeans() {
        myFundBeanDao.deleteAll()

    }


}