package search;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] s = scanner.nextLine().split(" ");
        String[] s2 = scanner.nextLine().split(" ");
        boolean b = false;
        for (int i = 0; i < s.length; i++) {
            for (int i1 = 0; i1 < s2.length; i1++) {
                if (s[i].equals(s2[i1])){
                    System.out.println(i+1);
                    b = true;
                    break;
                }
            }
        }
        if (!b){

        System.out.println("Not found");
        }
//                s1.equals())
//        Arrays.stream(s).map(p ->{
//            for (int i = 0; i < s2.length; i++) {
//                if (p.equals(s2[i])){
//                    System.out.println(i);
//                    break;
//
//                }
//            }
//            System.out.println("Not found");
//            return null;
//        });

    }
}
