package com.sgnatiuk.combination;

class MathUtils {

    private MathUtils() {
    }

    static long binomial(int n, int k) {
        long res = 1;
        int iterations = Math.min(k, n - k);
        for (int i = 0; i < iterations; ++i) {
            res *= n - i;
            res /= i + 1;
        }
        return res;
    }
}
