package com.hjw.fundplan.net

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.hjw.fundplan.entity.FundHaveRecordBean
import com.hjw.fundplan.entity.FundSearchRecordBean
import com.hjw.fundplan.entity.MyFundBean
import com.hjw.fundplan.net.dao.FundHaveRecordBeanDao
import com.hjw.fundplan.net.dao.FundSearchRecordBeanDao
import com.hjw.fundplan.net.dao.MyFundBeanDao

class DbRepo(context: Context) {
    private val db: AppDatabase
    private val myFundBeanDao: MyFundBeanDao
    private val mFundSearchRecordBeanDao: FundSearchRecordBeanDao
    private val mFundHaveRecordBean: FundHaveRecordBeanDao

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
        mFundHaveRecordBean = db.fundHaveRecordBeanDao
        mFundSearchRecordBeanDao = db.fundSearchRecordBeanDao
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

    fun addFundSearchBean(fundSearchRecordBean: FundSearchRecordBean) {
        mFundSearchRecordBeanDao.insertOrUpdateTx(fundSearchRecordBean)
    }

    fun getFundSearchBean(code: String): FundSearchRecordBean {
        return mFundSearchRecordBeanDao.loadFundSearchByCode(code)
    }

    fun addFundHaveBean(fundHaveRecordBean: FundHaveRecordBean) {
        Log.d(TAG, "addFundHaveBean")
        mFundHaveRecordBean.insertOrUpdateTx(fundHaveRecordBean)
        val loadFundByCode = mFundHaveRecordBean.loadFundByCode(fundHaveRecordBean.code)
        val loadFundSearchByCode =
            mFundSearchRecordBeanDao.loadFundSearchByCode(fundHaveRecordBean.code)

        var money = 0.0
        var earn = 0.0
        var numCount = 0.0
        loadFundByCode.forEach {
            money += it.price * it.num
            numCount += it.num
        }
        earn = loadFundSearchByCode.value * numCount - money
        val fund: MyFundBean = MyFundBean(
            fundHaveRecordBean.code, loadFundSearchByCode.name, money,
            earn, earn * 100 / money
        )
        myFundBeanDao.insertOrUpdateTx(fund)
    }

    fun getAllFundHaveBeans(): MutableList<FundHaveRecordBean> {
        return mFundHaveRecordBean.loadAllNoMayBe()
    }

    fun getFundHaveBeansByCode(code: String): MutableList<FundHaveRecordBean> {
        return mFundHaveRecordBean.loadFundByCode(code)
    }

    fun getMyFundBeanBeans(): MutableList<MyFundBean> {
        Log.d(TAG, "getMyFundBeanBeans")
        return myFundBeanDao.loadAllNoMayBe()
    }


}
