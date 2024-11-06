package com.example.java2labfx;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class cpuController implements IObserver{
    ICpu     cpu      = BCpu.build();      // один cpu
    Executer executer = new Executer(cpu); // один интерпретатор
    MProgram m        = BProgram.build();  // мб много программ
    Command  runCommand;

    @FXML
    GridPane allView;

    @FXML
    TextField instructName;

    @FXML
    TextField arg1;

    @FXML
    TextField arg2;

    @FXML
    Label Reg1;
    @FXML
    Label Reg2;
    @FXML
    Label Reg3;
    @FXML
    Label Reg4;

    //@FXML
    //ListView<String> allRam;

    @FXML
    GridPane allRam;

    @FXML
    GridPane allStatistic;

    @FXML
    void initialize() {
        m.addObserver(this);
        m.setExec(executer);
    }

    @FXML
    void runStep() {
        runCommand = m.runStep();
    }

    @FXML
    void addInstruction() {
        Command command = new Command(instructName.getText(), arg1.getText(), arg2.getText());
        m.add(command);
    }

    @FXML
    void resetProg() {
        allView.getChildren().clear();
        m.clear();
        allRam.getChildren().clear();
    }

    @FXML
    void reloadProg() {
        m.reload();
        allRam.getChildren().clear();
    }

    private void getReg() {
        Reg1.setText(String.valueOf(cpu.getReg(0)));
        Reg2.setText(String.valueOf(cpu.getReg(1)));
        Reg3.setText(String.valueOf(cpu.getReg(2)));
        Reg4.setText(String.valueOf(cpu.getReg(3)));
    }

    private void getRam() {
        allRam.getChildren().clear();
        for (int i=0; i < 1024; i++) {
            Label label = new Label();
            label.setText(i + "  :  " + cpu.getRam(i));
            if (m.ram.contains(i)) label.setTextFill(Color.RED);
            allRam.addColumn(0, label);
        }
    }

    private void getStatistic() {
        allStatistic.getChildren().clear();

        Map<String, Integer> map = m.commandList();

        for (String s: map.keySet()) {
            Label label1 = new Label();
            label1.setText(String.valueOf(map.get(s)));

            Label label2 = new Label();
            label2.setText(s);

            allStatistic.addColumn(0, label1);
            allStatistic.addColumn(1, label2);
        }
    }

    @Override
    public void event(MProgram p) {
        allView.getChildren().clear();
        getReg();
        getRam();
        getStatistic();

        for (Command c: p) {
            instrController ic = new instrController();
            FXMLLoader fxmlLoader = new FXMLLoader(
                    appMain.class.getResource("instruct.fxml"));
            fxmlLoader.setController(ic);
            try
            {
                Pane pane = fxmlLoader.load();
                ic.setInstruction(c);
                allView.addColumn(0, pane);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
