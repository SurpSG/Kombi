package com.sgnatiuk.cartesian.encodable.decoders

interface MaskDecoder<T> {
    fun decode(encoded: IntArray): T
}