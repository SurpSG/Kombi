package com.sgnatiuk.encodable.decoders

internal class MaskDecoderList<T>(
        private val data: List<List<T>>
) : MaskDecoder<List<T>> {

    override fun decode(encoded: IntArray): List<T> {
        return List(data.size){
            val itemIndex = encoded[it]
            data[it][itemIndex]
        }
    }
}