package com.sgnatiuk.cartesian;

interface MaskDecoder<T> {
    T decode(int[] encoded);
}
