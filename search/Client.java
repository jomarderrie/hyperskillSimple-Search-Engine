package search;

import search.model.Person;
import search.view.Menu;
import search.view.MenuController;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Client {
    private final Scanner scanner;
    private final MenuController view;
    private int amountOfLoops;
    private ArrayList<Person> peopleA = new ArrayList<>();
    private String ok = "";
    private int amountOfSearchQueries;

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
        try {
             scanner2 = new Scanner(new File(file));
            while (scanner2.hasNext()) {
                String[] s = scanner2.nextLine().split(" ");
                if (s.length > 3) {
                    break;
                }
                if (s.length == 3) {
                    peopleA.add(new Person(s[0], s[1], s[2]));
                } else {
                    if (s.length < 2) {
                        break;
                    }
                    peopleA.add(new Person(s[0], s[1]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner2.close();
        }
//        FileReader fileReader = new FileReader("F:\\Simple Search Engine\\ok.txt");
//        System.out.println(new BufferedReader(new FileReader(file)));
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            reader.readLine();
////            file.
////            return reader.lines().map(Person::parsePerson).collect(Collectors.toList());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(file);
////        System.out.println("Enter the number of people:");
////        amountOfLoops = scanner.nextInt();
////        System.out.println("Enter all people:");
////        scanner.nextLine();
//        for (int i = 0; i < amountOfLoops; i++) {
//            String[] s = scanner.nextLine().split(" ");
//            if (s.length > 3) {
//                break;
//            }
//            if (s.length == 3) {
//                peopleA.add(new Person(s[0], s[1], s[2]));
//            } else {
//                if (s.length < 2) {
//                    break;
//                }
//                peopleA.add(new Person(s[0], s[1]));
//            }
//        }
//        System.out.println("Enter the number of search queries:");
//        amountOfSearchQueries = scanner.nextInt();
//
//        scanner.nextLine();
//        for (int i = 0; i < amountOfSearchQueries; i++) {
//            System.out.println("Enter data to search people:");
//            searchQueries();
//        }
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
        LinkedHashSet<Person> peopleB = new LinkedHashSet<>();
        searchQuery = searchQuery.toUpperCase().trim();
        for (Person person : peopleA) {
            if (person.getEmail() != null) {
                if (person.getEmail().trim().toUpperCase().contains(searchQuery)) {
                    peopleB.add(person);
                }
            }
            if (person.getSurName().trim().toUpperCase().contains(searchQuery)) {
                peopleB.add(person);

            }
            if (person.getUserName().trim().toUpperCase().contains(searchQuery)) {
                peopleB.add(person);
            }
        }
        for (Person person : peopleB) {
            System.out.println(person.toString());
        }
    }

    private void exit() {
        view.exitMenu();
    }
}
