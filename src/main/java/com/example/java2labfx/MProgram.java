package com.example.java2labfx;
import java.util.*;
import java.util.stream.Collectors;

public class MProgram implements Iterable<Command> {

    ArrayList<Command> arrayList = new ArrayList<>();
    ArrayList<IObserver> allObserver = new ArrayList<>();
    Iterator<Command> iterator = this.iterator();

    ArrayList<Integer> ram = new ArrayList<>();

    Executer executer;
    private int      ind      = 0;

    @Override
    public Iterator<Command> iterator() { return arrayList.iterator(); }

    void eventCall(){
        allObserver.forEach(action->action.event(this));
    }

    public void clear() {
        ind = 0;
        arrayList.clear();
        ram.clear();
        eventCall();
    }

    public void reload() {
        ind = 0;
        isRunNull();
        ram.clear();
        eventCall();
    }

    public void add(Command command) {
        arrayList.add(command);
        eventCall();
    }

    public void addObserver(IObserver obs){
        allObserver.add(obs);
        eventCall();
    }

    public void removeInstr(Command c) {
        arrayList.remove(c);
        eventCall();
    }

    public void left(Command c) {
        Collections.swap(arrayList, arrayList.indexOf(c), arrayList.indexOf(c)-1);
        eventCall();
    }

    public void right(Command c) {
        Collections.swap(arrayList, arrayList.indexOf(c), arrayList.indexOf(c)+1);
        eventCall();
    }

    public void setExec(Executer exec) {
        executer = exec;
    }

    private void isRunNull() {
        for (Command c: arrayList) c.clrRun();
    }

    Command runStep() {
        if (ind < arrayList.size()) {
            Command command = arrayList.get(ind);
            try {
                executer.run(command);

                if (ind > 0) { arrayList.get(ind - 1).clrRun(); }
                command.setRun();

                ind++;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (command.getInstr().equals("init")) ram.add(Integer.parseInt(command.getArg1()));
            if (command.getInstr().equals("st")) ram.add(Integer.parseInt(command.getArg2()));

            eventCall();
        }
        return arrayList.get(ind - 1);
    }

    private Set<String> getUniqueCommand() {
        Set<String> res = new HashSet<>();
        for (Command command : arrayList) res.add(command.str[0]);
        return res;
    }

    private Map <String, Integer> getCountCommands() {
        Set <String> uniqCommands          = getUniqueCommand();
        Map <String, Integer> mapCommands  = new HashMap<>();
        ArrayList<String> strings          = new ArrayList<>();

        for (Command c: arrayList) strings.add(c.str[0]);
        for (String s: uniqCommands) mapCommands.put(s, Collections.frequency(strings, s));

        return mapCommands;
    }

    // задача 1
    public void mostOftCommand() {
        Map <String, Integer> mapCommands             = getCountCommands();
        Comparator<Map.Entry<String, Integer>> compar = Map.Entry.comparingByValue();

        System.out.println("Most Often Command is " +
                Collections
                        .max(mapCommands.entrySet(), compar)
                        .getKey()
        );
    }

    // задача 2
    public void addressRange() {
        ArrayList<Integer> address = arrayList.stream()
                .filter((command) ->
                        switch (command.str[0]) {
                            case "ld", "st", "init" -> true;
                            default -> false;
                        })
                .map((command) -> {
                    int numOfStr = switch (command.str[0]) {
                        case "ld", "st" -> 2;
                        default -> 1;
                    };
                    return Integer.parseInt(command.str[numOfStr]);
                })
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("Address range is from " +
                Collections.min(address) + " to " + Collections.max(address)
        );
    }

    // задача 3
    public Map<String, Integer> commandList() {
        System.out.println("Command List: ");

        Map<String, Integer> mapCommands = getCountCommands();
        Comparator<Map.Entry<String, Integer>> compar = Map.Entry.comparingByValue(Comparator.reverseOrder());

        /*mapCommands
                .entrySet()
                .stream()
                .sorted(compar)
                .forEach(System.out::println);
        */

        LinkedHashMap<String, Integer> map = mapCommands
                .entrySet()
                .stream()
                .sorted(compar)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (x, y) -> y, LinkedHashMap::new
                ));

        return map;
//                .collect(Collectors.toMap(
//                        entry -> entry.getKey().substring(5),
//                        entry -> entry.getValue()));

    }
}


/*
public class MProgram implements Iterable<instrController> {

    ArrayList<instrController> arrayList = new ArrayList<>();
    ArrayList<IObserver> allObserver = new ArrayList<>();

    //ArrayList<instrController> arrayList = new ArrayList<>();

    //int actNum = 0; // когда команда выполняется +1
    //int maxNum = 0; // когда команда добавляется +1

    @Override
    public Iterator<instrController> iterator() {
        /*return new Iterator<Command>() {
            @Override
            public boolean hasNext() {
                return (actNum != maxNum);
            }

            @Override
            public Command next() {
                actNum++;
                return arrayList.get(actNum-1);
            }
        };
        return arrayList.iterator();
    }

    public void add(instrController ic) {
        arrayList.add(ic);
        //maxNum++;
        eventCall();
    }

    void eventCall(){
        allObserver.forEach(action->action.event(this));
    }

    public void addObserver(IObserver obs){
        allObserver.add(obs);
        eventCall();
    }

    public void removeInstr(instrController ic) {
        arrayList.remove(ic);
        eventCall();
    }

    public void left(instrController ic) {
        Collections.swap(arrayList, arrayList.indexOf(ic), arrayList.indexOf(ic)-1);
        eventCall();
    }

    public void right(instrController ic) {
        Collections.swap(arrayList, arrayList.indexOf(ic), arrayList.indexOf(ic)+1);
        eventCall();
    }


    private Set<String> getUniqueCommand() {
        Set<String> res = new HashSet<>();
        for (instrController ic : arrayList) res.add(ic.command.str[0]);
        return res;
    }

    private Map <String, Integer> getCountCommands() {
        Set <String> uniqCommands          = getUniqueCommand();
        Map <String, Integer> mapCommands  = new HashMap<>();
        ArrayList<String> strings          = new ArrayList<>();

        for (instrController ic: arrayList) strings.add(ic.command.str[0]);
        for (String s: uniqCommands) mapCommands.put(s, Collections.frequency(strings, s));

        return mapCommands;
    }

    // задача 1
    public void mostOftCommand() {
        Map <String, Integer> mapCommands             = getCountCommands();
        Comparator<Map.Entry<String, Integer>> compar = Map.Entry.comparingByValue();

        System.out.println("Most Often Command is " +
                Collections
                        .max(mapCommands.entrySet(), compar)
                        .getKey()
        );
    }

    // задача 2
    public void addressRange() {
        ArrayList<Integer> address = arrayList.stream()
                .map((c)->c.command)
                .filter((command) ->
                        switch (command.str[0]) {
                            case "ld", "st", "init" -> true;
                            default -> false;
                        })
                .map((command) -> {
                    int numOfStr = switch (command.str[0]) {
                        case "ld", "st" -> 2;
                        default -> 1;
                    };
                    return Integer.parseInt(command.str[numOfStr]);
                })
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("Address range is from " +
                Collections.min(address) + " to " + Collections.max(address)
        );
    }

    // задача 3
    public void commandList() {
        System.out.println("Command List: ");

        Map<String, Integer> mapCommands = getCountCommands();
        Comparator<Map.Entry<String, Integer>> compar = Map.Entry.comparingByValue();

        mapCommands
                .entrySet()
                .stream()
                .sorted(compar)
                .forEach(System.out::println);
    }
}
*/