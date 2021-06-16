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
        LinkedHashSet<Person> peopleB = new LinkedHashSet<>();
        searchQuery = searchQuery.toUpperCase().trim();
        if (map.get(searchQuery)==null) {
            System.out.println("No matching people found.");
        }else {
            String finalSearchQuery = searchQuery;
            map.get(searchQuery).forEach(p -> {
                Person person = peopleA.get(p);
                if (person.getEmail() != null) {
                    if (person.getEmail().trim().toUpperCase().contains(finalSearchQuery)) {
                        peopleB.add(person);
                    }
                }
                if (person.getSurName().trim().toUpperCase().contains(finalSearchQuery)) {
                    peopleB.add(person);
                }
                if (person.getUserName().trim().toUpperCase().contains(finalSearchQuery)) {
                    peopleB.add(person);
                }
            });
            for (Person person : peopleB) {
                System.out.println(person.toString());
            }
        }
    }

    private void exit() {
        view.exitMenu();
    }
}
