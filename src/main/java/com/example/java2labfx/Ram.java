package com.example.java2labfx;

public class Ram {
    private int[] mem;

    public Ram(int val) {
        mem = new int[val];
        for (int i: mem) i = 0;
    }

    public int getMem(int ind) {
        return mem[ind];
    }

    public void setMem(int ind, int val) {
        mem[ind] = val;
    }
}
