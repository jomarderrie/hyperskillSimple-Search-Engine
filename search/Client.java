package search;

import search.model.Person;
import search.view.Menu;
import search.view.MenuController;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Client {
    private final Scanner scanner;
    private final MenuController view;
    private ArrayList<Person> peopleA = new ArrayList<>();
    HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();

    public Client(String file) throws FileNotFoundException {
        this.scanner = new Scanner(System.in);
        this.view = new MenuController();
        addUsers(file);
        view.run(new Menu.Builder().setScanner(scanner)
                .addItem(1, "Find a person", this::searchQueries)
                .addItem(2, "Print all people", this::printAllPeople)
                .addItem(0, "Exit", this::exit)
                .build());
    }


    private void addUsers(String file) throws FileNotFoundException {
        Scanner scanner2 = null;
        int index = 0;
        try {
            scanner2 = new Scanner(new File(file));
            while (scanner2.hasNext()) {
                String[] s = scanner2.nextLine().split(" ");
                if (s.length > 3) {
                    break;
                }
                if (s.length == 3) {
                    Person person = new Person(s[0], s[1], s[2]);
                    peopleA.add(person);
                    buildInverseMatrix(s[0], index);
                    buildInverseMatrix(s[1], index);
                    buildInverseMatrix(s[2], index);
                } else {
                    if (s.length < 2) {
                        break;
                    }
                    Person person = new Person(s[0], s[1]);
                    peopleA.add(person);
                    buildInverseMatrix(s[0], index);
                    buildInverseMatrix(s[1], index);
                }
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner2.close();
        }
    }


    private void buildInverseMatrix(String item, int index) {
        if (item != null) {
            item = item.toUpperCase().trim();
            if (map.containsKey(item.toUpperCase().trim())) {
                map.get(item).add(index);
            } else {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(index);
                map.put(item, arrayList);
            }
        }

    }

    private void printAllPeople() {
        System.out.println("=== List of people ===");
        for (Person person : peopleA) {
            System.out.println(person.toString());
        }
    }

    private void searchQueries() {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.nextLine();
        System.out.println("Enter a name or email to search all suitable people.");
        String searchQuery = scanner.nextLine();
        searchQuery = searchQuery.toUpperCase().trim();
        LinkedHashSet<Person> peopleB = null;
        boolean strategyFound = false;
        switch (strategy) {
            case "ANY":
                peopleB = any(searchQuery);
                strategyFound = true;
                break;
            case "ALL":
                peopleB = all(searchQuery);
                strategyFound = true;
                break;
            case "NONE":
                peopleB = none(searchQuery);
                strategyFound = true;
                break;
        }
        if (strategyFound) {
            if (peopleB == null) {
                System.out.println("\nNo matching people found.");
            } else if (peopleB.isEmpty()) {
                System.out.println("\nNo matching people found.");
            } else {
                System.out.println("Found " + peopleB.size() + " people:");
                for (Person person : peopleB) {
                    System.out.println(person.toString());
                }
                System.out.println(" ");
            }
        }else{
            System.out.println("Incorrect option! Try again.");
        }
    }

    public LinkedHashSet<Person> none(String searchQuery) {
        String[] s = searchQuery.split(" ");
        LinkedHashSet<Person> peopleB = new LinkedHashSet<>();
        for (int i = 0; i < s.length; i++) {
            if (map.get(s[i]) != null) {
                //so we know that the map i has a value
                map.get(s[i]).forEach(p -> {
                    String[] person = peopleA.get(p).toString().split(" ");
                    ArrayList<String> ok = new ArrayList<>();
                    for (int i1 = 0; i1 < s.length; i1++) {
                        for (int i2 = 0; i2 < s.length; i2++) {
                            if (person[i2].toUpperCase().contains(s[i1])) {
                                ok.add(person[i1]);
                            }
                        }
                    }
                    if (!(s.length == ok.size())) {
                        peopleB.add(peopleA.get(p));
                    }
                });
            }
        }
        LinkedHashSet<Person> peopleC = new LinkedHashSet<Person>(peopleA);
        peopleC.removeAll(peopleB);
//        List<Person> clonedDogs = peopleA.stream().map(Person::new).collect(toList());
        return peopleC;
    }

    public LinkedHashSet<Person> all(String searchQuery) {
        String[] s = searchQuery.split(" ");
        LinkedHashSet<Person> peopleB = new LinkedHashSet<>();
        for (int i = 0; i < s.length; i++) {
            if (map.get(s[i]) != null) {
                //so we know that the map i has a value
                map.get(s[i]).forEach(p -> {
                    String[] person = peopleA.get(p).toString().split(" ");
                    ArrayList<String> ok = new ArrayList<>();
                    for (int i1 = 0; i1 < s.length; i1++) {
                        for (int i2 = 0; i2 < s.length; i2++) {
                            if (person[i2].toUpperCase().contains(s[i1])) {
                                ok.add(person[i1]);
                            }
                        }
                    }
                    if (s.length == ok.size()) {
                        peopleB.add(peopleA.get(p));
                    }
                });
            }
        }
        return peopleB;
    }


    public LinkedHashSet<Person> any(String search) {
        LinkedHashSet<Person> peopleB = new LinkedHashSet<>();
        String[] s = search.split(" ");
        for (int i = 0; i < s.length; i++) {
            int finalI = i;
            if (map.get(s[i]) != null) {
                map.get(s[i]).forEach(p -> {
                    Person person = peopleA.get(p);
                    if (person.getEmail() != null) {
                        if (person.getEmail().trim().toUpperCase().contains(s[finalI])) {
                            peopleB.add(person);
                        }
                    }
                    if (person.getSurName().trim().toUpperCase().contains(s[finalI])) {
                        peopleB.add(person);
                    }
                    if (person.getUserName().trim().toUpperCase().contains(s[finalI])) {
                        peopleB.add(person);
                    }
                });
            }
        }
        return peopleB;
    }

    private void exit() {
        view.exitMenu();
    }
}
