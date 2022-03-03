package kr.co.lee.accoutproject.repository

import kr.co.lee.accoutproject.persistence.TypeDAO
import kr.co.lee.accoutproject.model.TypeEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TypeRepository @Inject constructor(
    private val typeDao: TypeDAO
) {
    fun createTypes(typeList: List<TypeEntity>) {
        typeDao.insertTypes(typeList)
    }

    fun getTypes(typeForm: Int) =
        typeDao.getTypes(typeForm)
}