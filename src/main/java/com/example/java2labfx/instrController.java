package com.example.java2labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.paint.Color;
import java.util.Collections;

public class instrController {
    @FXML
    Label instrName;

    @FXML
    Label arg1;

    @FXML
    Label arg2;

    Command command;

    MProgram m = BProgram.build();

    public void setInstruction(Command c){
        command = c;
        instrName.setText(command.getInstr());

        if (command.isRun) {
            instrName.setTextFill(Color.RED);
        }

        arg1.setText(command.getArg1());
        arg2.setText(command.getArg2());
    }

    public void removeInstruction() {  m.removeInstr(command);    }

    public void replaceLeft() { m.left(command); }

    public void replaceRight() { m.right(command); }
}
