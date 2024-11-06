package com.example.java2labfx;

public interface ICpu {
    void exec(Command command) throws Exception;

    int getReg(int ind);

    int getRam(int address);
}
