package com.hjw.fundplan.net

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.hjw.fundplan.entity.*
import com.hjw.fundplan.net.dao.*

class DbRepo(context: Context) {
    private val db: AppDatabase
    private val myFundBeanDao: MyFundBeanDao
    private val mFundSearchRecordBeanDao: FundSearchRecordBeanDao
    private val mFundHaveRecordBean: FundHaveRecordBeanDao
    private val mFundPlanBeanDao: FundPlanBeanDao
    private val mFundPlanRecordBeanDao: FundPlanRecordBeanDao

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
        mFundPlanBeanDao = db.fundPlanBeanDao
        mFundPlanRecordBeanDao = db.fundPlanRecordBeanDao
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

//--------------------------------FundSearchRecordBean
    fun addFundSearchBean(fundSearchRecordBean: FundSearchRecordBean) {
        mFundSearchRecordBeanDao.insertOrUpdateTx(fundSearchRecordBean)
    }

    fun getFundSearchBean(code: String): FundSearchRecordBean {
        return mFundSearchRecordBeanDao.loadFundSearchByCode(code)
    }

    fun getAllFundSearchBeans(): MutableList<FundSearchRecordBean> {
        return mFundSearchRecordBeanDao.loadAll()
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
//--------------------------------FundPlanBean

    fun addFundPlanBean(fundSearchRecordBean: FundPlanBean) {
        mFundPlanBeanDao.insertOrUpdateTx(fundSearchRecordBean)
    }

    fun getFundPlanSize(code: String, type: Int, value: Int):Int {
        return mFundPlanBeanDao.getFundPlanSize(code, type, value)
    }

    fun getFundPlanBeans(code: String): MutableList<FundPlanBean> {
        return if (code.isEmpty()) mFundPlanBeanDao.loadAll() else mFundPlanBeanDao.loadFundPlanByCode(code)
    }

    //--------------------------------FundPlanRecordBean
    fun addFundPlanRecordBean(recordBean: FundPlanRecordBean) {
        mFundPlanRecordBeanDao.insertOrUpdateTx(recordBean)
    }

    fun loadFundPlanRecordByCode(code: String): MutableList<FundPlanRecordBean> {
        return mFundPlanRecordBeanDao.loadFundPlanRecordByCode(code)
    }
    fun loadAllFundPlanRecord(): MutableList<FundPlanRecordBean> {
        return mFundPlanRecordBeanDao.loadAllFundPlanRecord()
    }
}
