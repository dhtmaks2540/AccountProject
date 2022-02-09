package kr.co.lee.accoutproject.data

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