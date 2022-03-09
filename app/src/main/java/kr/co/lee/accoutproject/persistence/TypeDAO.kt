package kr.co.lee.accoutproject.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.co.lee.accoutproject.model.TypeEntity

// TypeEntity DAO
@Dao
interface TypeDAO {
    // TypeEntities INSERT
    @Insert
    fun insertTypes(typeList: List<TypeEntity>)

    // TypeEntity INSERT
    @Insert
    fun insertTypes(type: TypeEntity)

    // TypeEntity SELECT
    @Query("SELECT * FROM types WHERE type_form = (:typeForm)")
    fun getTypes(typeForm: Int): List<TypeEntity>
}