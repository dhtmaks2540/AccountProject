package kr.co.lee.accoutproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "accounts",
    foreignKeys = [
        ForeignKey(entity = TypeEntity::class, parentColumns = ["type_set_id"], childColumns = ["type_id"], onDelete = CASCADE)
    ]
)
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val accountId: Int,
    val year: Int,
    val month: Int,
    val day: Int,
    val content: String,
    val money: Int,
    @ColumnInfo(name = "type_id") val typeId: Int,
)