package com.hjw.fundplan.net.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hjw.fundplan.entity.FundHaveRecordBean
import com.hjw.fundplan.entity.FundPlanBean
import com.hjw.fundplan.entity.MyFundBean

/**
 * @author hejiangwei
 * Created at 2020/12/11.
 * @Describe
 */
@Dao
interface FundPlanBeanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateTx(vararg messages: FundPlanBean)

    @Query("DELETE FROM FundPlanBean")
    fun deleteAll()

    @Query("SELECT * FROM FundPlanBean")
    fun loadAllNoMayBe(): MutableList<FundPlanBean>
    @Query("SELECT * FROM FundPlanBean where code =:code ORDER BY time DESC")
    fun loadFundPlanByCode(code:String): MutableList<FundPlanBean>

    @Query("SELECT count(*) FROM FundPlanBean where code =:code AND cycle_type =:type AND cycle_value = :value")
    fun getFundPlanSize(code:String,type: Int,value: Int): Int

    @Query("DELETE FROM FundPlanBean WHERE id =:id")
    fun deletePlanInfo(id: Long)
}