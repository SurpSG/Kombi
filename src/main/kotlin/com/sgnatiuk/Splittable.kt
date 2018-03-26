package com.sgnatiuk

interface Splittable<T : Splittable<T>> {
    fun split(n: Int) : List<T>
}