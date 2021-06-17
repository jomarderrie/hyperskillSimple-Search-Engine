package search;

import search.model.Person;
import search.view.Menu;
import search.view.MenuController;

import java.io.*;
import java.util.*;

public class Client {
    private final Scanner scanner;
    private final MenuController view;
    private ArrayList<Person> peopleA = new ArrayList<>();
    HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();

    public Client(String file) throws FileNotFoundException {
        this.scanner = new Scanner(System.in);
        this.view = new MenuController();
        enterNumberOfPeople(file);
        view.run(new Menu.Builder().setScanner(scanner)
                .addItem(1, "Find a person", this::searchQueries)
                .addItem(2, "Print all people", this::printAllPeople)
                .addItem(0, "Exit", this::exit)
                .build());
    }


    private void enterNumberOfPeople(String file) throws FileNotFoundException {
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
        System.out.println("Enter a name or email to search all suitable people.");
        String searchQuery = scanner.nextLine();
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.nextLine();
        searchQuery = searchQuery.toUpperCase().trim();
        LinkedHashSet<Person> peopleB = null;
        switch (strategy) {
            case "ANY":
                peopleB = any(searchQuery);
                break;
            case "ALL":
                peopleB = all(searchQuery);
                break;
            case "NONE":
                peopleB = none(searchQuery);
                break;
            default:
                System.out.println("\nIncorrect option! Try again.");
                break;
        }
        if (peopleB == null) {
            System.out.println("\nNo matching people found.");
            return;
        } else if (map.isEmpty()) {
            System.out.println("\nNo matching people found.");
            return;
        } else {
            for (Person person : peopleB) {
                System.out.println(person.toString());
            }
        }


//        if (finder == null) {
//            throw new RuntimeException(
//                    "Unknown strategy type passed. Please, write to the author of the problem.");
//        }
//
//        System.out.println(finder.find(numbers));

//        if (map.get(searchQuery)==null) {
//            System.out.println("No matching people found.");
//        }else {
//            String finalSearchQuery = searchQuery;
//            map.get(searchQuery).forEach(p -> {
//                Person person = peopleA.get(p);
//                if (person.getEmail() != null) {
//                    if (person.getEmail().trim().toUpperCase().contains(finalSearchQuery)) {
//                        peopleB.add(person);
//                    }
//                }
//                if (person.getSurName().trim().toUpperCase().contains(finalSearchQuery)) {
//                    peopleB.add(person);
//                }
//                if (person.getUserName().trim().toUpperCase().contains(finalSearchQuery)) {
//                    peopleB.add(person);
//                }
//            });
//            for (Person person : peopleB) {
//                System.out.println(person.toString());
//            }
//        }
    }

    public LinkedHashSet<Person> none(String searchQuery) {
        LinkedHashSet<Person> peopleB = new LinkedHashSet<>();
        map.get(searchQuery).forEach(p->{
            Person person = peopleA.get(p);
            if (!person.toString().contains(searchQuery)) {
                peopleB.add(person);
            }
        });
        return peopleB;
    }

    public LinkedHashSet<Person> all(String searchQuery) {
        LinkedHashSet<Person> peopleB = new LinkedHashSet<>();

        map.get(searchQuery).forEach(p->{
            Person person = peopleA.get(p);
            if (person.toString().contains(searchQuery)) {
                peopleB.add(person);
            }
        });
        return peopleB;
    }

    public LinkedHashSet<Person> any(String search) {
        LinkedHashSet<Person> peopleB = new LinkedHashSet<>();
        String[] s = search.split(" ");
        for (int i = 0; i < s.length; i++) {
            int finalI = i;
            if (map.get(s[i])!=null) {
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
//        Arrays.stream(s).map(p ->{
//            System.out.println(p);
//            return null;
//        });
//
//        map.get(search).forEach(p -> {
//            Person person = peopleA.get(p);
//            if (person.getEmail() != null) {
//                if (person.getEmail().trim().toUpperCase().contains(search)) {
//                    peopleB.add(person);
//                }
//            }
//            if (person.getSurName().trim().toUpperCase().contains(search)) {
//                peopleB.add(person);
//            }
//            if (person.getUserName().trim().toUpperCase().contains(search)) {
//                peopleB.add(person);
//            }
//        });
        return peopleB;
    }

    private void exit() {
        view.exitMenu();
    }
}
