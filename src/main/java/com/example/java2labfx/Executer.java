package com.example.java2labfx;
import java.util.Iterator;

public class Executer {
    private final ICpu cpu;

    //Executer() { cpu = BCpu.build();}

    Executer(ICpu _cpu) {
        cpu = _cpu;
    }

    public void run(Command c) throws Exception {
        //for (String s: c.str) if (s.isEmpty()) throw new Exception("No func");
        try {
            cpu.exec(c);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}