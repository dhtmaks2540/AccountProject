package kr.co.lee.accoutproject.model

import androidx.room.Embedded
import kr.co.lee.accoutproject.model.AccountEntity
import kr.co.lee.accoutproject.model.TypeEntity
import java.io.Serializable

data class AccountAndType(
    @Embedded
    val account: AccountEntity,

    @Embedded
    val type: TypeEntity
): Serializable