package com.hjw.fundplan.net.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hjw.fundplan.entity.FundHaveRecordBean
import com.hjw.fundplan.entity.FundSearchRecordBean
import com.hjw.fundplan.entity.MyFundBean

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
@Dao
interface FundSearchRecordBeanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateTx(vararg messages: FundSearchRecordBean)

    @Query("DELETE FROM FundSearchRecordBean")
    fun deleteAll()

    @Query("SELECT * FROM FundSearchRecordBean")
    fun loadAllNoMayBe(): MutableList<FundSearchRecordBean>

    @Query("SELECT * FROM FundSearchRecordBean")
    fun loadAll(): MutableList<FundSearchRecordBean>

    @Query("SELECT * FROM FundSearchRecordBean where code = :code LIMIT 1")
    fun loadFundSearchByCode(code: String): FundSearchRecordBean

    @Query("DELETE FROM FundSearchRecordBean WHERE id =:id")
    fun deleteAlarmInfo(id: Long)
}