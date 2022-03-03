package kr.co.lee.accoutproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import kr.co.lee.accoutproject.model.TypeEntity
import java.io.Serializable

// Entity 클래스 -> 하나의 테이블에 해당
@Entity(
    tableName = "accounts",
    foreignKeys = [
        ForeignKey(entity = TypeEntity::class, parentColumns = ["type_set_id"], childColumns = ["type_id"], onDelete = CASCADE)
    ]
)
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val accountId: Int = 0,
    val year: Int,
    val month: Int,
    val day: Int,
    val content: String,
    val money: Long,
    @ColumnInfo(name = "type_id") val typeId: Int,
): Serializable