package com.hjw.fundplan.net.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hjw.fundplan.entity.FundHaveRecordBean
import com.hjw.fundplan.entity.MyFundBean

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
@Dao
interface FundHaveRecordBeanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateTx(vararg messages: FundHaveRecordBean)

    @Query("DELETE FROM FundHaveRecordBean")
    fun deleteAll()

    @Query("SELECT * FROM FundHaveRecordBean")
    fun loadAllNoMayBe(): MutableList<FundHaveRecordBean>
    @Query("SELECT * FROM FundHaveRecordBean where code =:code ORDER BY time DESC")
    fun loadFundByCode(code:String): MutableList<FundHaveRecordBean>

    @Query("DELETE FROM FundHaveRecordBean WHERE id =:id")
    fun deleteAlarmInfo(id: Long)
}