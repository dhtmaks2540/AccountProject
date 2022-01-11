package kr.co.lee.accoutproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types")
data class TypeEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "type_set_id") val typeId: Int,
    @ColumnInfo(name = "type_form") val typeForm: Int,
    @ColumnInfo(name = "type_name") val typeName: String
)