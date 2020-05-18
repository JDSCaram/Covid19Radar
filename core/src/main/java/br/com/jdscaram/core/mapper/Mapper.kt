package br.com.jdscaram.core.mapper

interface Mapper<in E, T> {
    fun mapFrom(from: E): T
}
