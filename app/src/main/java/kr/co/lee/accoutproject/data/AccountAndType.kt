package kr.co.lee.accoutproject.data

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

data class AccountAndType(
    @Embedded
    val account: AccountEntity,

    @Embedded
    val type: TypeEntity
): Serializable