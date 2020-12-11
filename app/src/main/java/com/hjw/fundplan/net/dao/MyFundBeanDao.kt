package com.hjw.fundplan.net.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hjw.fundplan.entity.MyFundBean

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
@Dao
interface MyFundBeanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateTx(vararg messages: MyFundBean)

    @Query("DELETE FROM MyFundBean")
    fun deleteAll()

    @Query("SELECT * FROM MyFundBean")
    fun loadAllNoMayBe(): MutableList<MyFundBean>
//
//    @Query("SELECT * FROM MyFundBean WHERE triggerAtMills <= :ts AND straType =:straType")
//    fun loadExpiredAlarmInfos(ts: Long, straType: Int): MutableList<AlarmInfoBean>
//
//    @Query("DELETE FROM AlarmInfoBean WHERE straId =:straId AND straType =:straType")
//    fun deleteAlarmInfo(straId: Long, straType: Int)

    @Query("DELETE FROM MyFundBean WHERE id =:id")
    fun deleteAlarmInfo(id: Long)
}