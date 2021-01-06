package com.hjw.fundplan.net.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hjw.fundplan.entity.FundPlanRecordBean

/**
 * @author hejiangwei
 * Created at 2021/1/6.
 * @Describe
 */
@Dao
interface FundPlanRecordBeanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateTx(vararg messages: FundPlanRecordBean)

    @Query("DELETE FROM FundPlanRecordBean")
    fun deleteAll()

    @Query("SELECT * FROM FundPlanRecordBean")
    fun loadAllNoMayBe(): MutableList<FundPlanRecordBean>

    @Query("SELECT * FROM FundPlanRecordBean where code =:code ORDER BY time DESC")
    fun loadFundPlanRecordByCode(code: String): MutableList<FundPlanRecordBean>

    @Query("SELECT * FROM FundPlanRecordBean ORDER BY time DESC")
    fun loadAllFundPlanRecord(): MutableList<FundPlanRecordBean>

    @Query("SELECT count(*) FROM FundPlanRecordBean where code =:code AND cycle_type =:type AND cycle_value = :value")
    fun getFundPlanSize(code: String, type: Int, value: Int): Int

    @Query("DELETE FROM FundPlanRecordBean WHERE id =:id")
    fun deletePlanInfo(id: Long)
}