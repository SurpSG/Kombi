package com.sgnatiuk.encodable.decoders

interface MaskDecoder<T> {
    fun decode(encoded: IntArray): T
}