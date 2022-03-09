package kr.co.lee.accoutproject.repository

import kr.co.lee.accoutproject.persistence.TypeDAO
import kr.co.lee.accoutproject.model.TypeEntity
import javax.inject.Inject
import javax.inject.Singleton

// TypeDAO를 관리하는 Repository
@Singleton
class TypeRepository @Inject constructor(
    private val typeDao: TypeDAO
) {
    // TypeEntity 생성
    fun createTypes(typeList: List<TypeEntity>) {
        typeDao.insertTypes(typeList)
    }

    // TypeEntity 획득
    fun getTypes(typeForm: Int) =
        typeDao.getTypes(typeForm)
}