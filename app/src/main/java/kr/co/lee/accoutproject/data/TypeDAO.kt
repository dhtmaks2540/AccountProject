package kr.co.lee.accoutproject.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TypeDAO {
    // 모든 타입 추가
    @Insert
    fun insertTypes(typeList: List<TypeEntity>)

    // 타입 하나 추가
    @Insert
    fun insertTypes(type: TypeEntity)

    // 타입 획득
    @Query("SELECT * FROM types WHERE type_form = (:typeForm)")
    fun getTypes(typeForm: Int): List<TypeEntity>
}