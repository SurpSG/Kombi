package com.sgnatiuk.cartesian;

import java.util.Iterator;

class CombinationMask implements Iterator<int[]> {

    private final int[] bases;
    private final int[] mask;
    private boolean hasNext = true;

    public CombinationMask(int[] bases) {
        if (bases.length == 0) {
            throw new IllegalArgumentException("Expected non empty array");
        }
        this.bases = bases;
        this.mask = new int[bases.length];
    }

    @Override
    public boolean hasNext() {
        return hasNext || (hasNext = !increment());
    }

    @Override
    public int[] next() {
        hasNext = false;
        return mask;
    }

    /**
     * Computes next encoded combination
     *
     * @return true if encoded was overflowed, otherwise false
     */
    private boolean increment() {
        int index = 0;
        do {
            int incrementedCell = mask[index] + 1;
            if (incrementedCell == bases[index]) {
                mask[index++] = 0;
            } else {
                mask[index] = incrementedCell;
                return false;
            }
        } while (index < bases.length);

        return true;
    }
}
