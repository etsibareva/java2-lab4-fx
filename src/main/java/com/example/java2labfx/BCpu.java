package com.example.java2labfx;

public class BCpu {
    static ICpu build() {
        MCpu mcpu = new MCpu();
        mcpu.ram = new Ram(1024);
        return mcpu;
    }
}
