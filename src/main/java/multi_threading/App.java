package multi_threading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class App {
    public static void main(String[] args) {

        // File Names and Number of Files
        System.out.println("Number of Files: " + args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }

        // Initializations
        BST b1 = new BST();
        List<ArrayList<String>> tokenize = new ArrayList<>();

        int option;
        while(true) {
            System.out.println();
            System.out.println(".....::::: MENU :::::.....");
            System.out.println("1) Displaying BST build from Vocabulary File.");
            System.out.println("2) Displaying Vectors build from Input files.");
            System.out.println("3) Viewing Match words and its frequency");
            System.out.println("4) Searching a query->It should display all the files query found in.");
            System.out.println("5) Enter 5 for Exiting");

            System.out.println();
            System.out.print("Input Option: ");
            Scanner opt = new Scanner(System.in);
            option = opt.nextInt();

            
            // --------------------------------------------------------------------------------------

            if (option == 1) {

                // File Reading and Storing Vocabulary words.

                try {
                    String line = "";
                    BufferedReader br = new BufferedReader(new FileReader(args[0]));
                    while ((line = br.readLine()) != null) {
                        String temp = line;
                        b1.insert(temp);
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Making BST tree
                System.out.println();
                System.out.println("Displaying BST build from Vocabulary File");
                System.out.println("-----------------------------------------");
                b1.show();
            }

            // --------------------------------------------------------------------------------------

            else if (option == 2) {

                // Vector<String> tokenize = new Vector<String>();

                for (int i = 1; i < args.length; i++) {
                    try {
                        String line = "";
                        BufferedReader br = new BufferedReader(new FileReader(args[i]));
                        while ((line = br.readLine()) != null) {
                            String[] temp = line.split(" ");
                            ArrayList<String> data = new ArrayList<String>();
                            for (int j = 0; j < temp.length; j++) {
                                data.add(temp[j]);
                            }
                            tokenize.add(data);
                        }
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println();
                System.out.println("Displaying Vectors build from Input files");
                System.out.println("-----------------------------------------");
                for (int i = 0; i < tokenize.size(); i++) {
                    for (int j = 0; j < tokenize.get(i).size(); j++) {
                        System.out.print(tokenize.get(i).get(j) + " ");
                    }
                    System.out.println();
                }
            }

            // --------------------------------------------------------------------------------------

            else if (option == 3) {
                word[] w1 = new word[100];
                for (int i = 0; i < 100; i++) {
                    w1[i] = new word();
                }
                int word_count = 0;

                for (int i = 0; i < tokenize.size(); i++) {
                    for (int j = 0; j < tokenize.get(i).size(); j++) {

                        Boolean check = false;
                        check = b1.compare(tokenize.get(i).get(j));
                        if (check == true) {
                            // Checking word repetition
                            Boolean check_word = false;
                            for (int k = 0; k <= word_count; k++) {
                                if (w1[k].word1.compareTo(tokenize.get(i).get(j)) == 0) {
                                    w1[k].frequency++;
                                    check_word = true;
                                }
                            }
                            if (check_word == false) {
                                w1[word_count].word1 = tokenize.get(i).get(j);
                                w1[word_count].frequency++;
                                word_count++;
                            }
                        }
                    }
                }

                System.out.println();
                System.out.println("Viewing Matched Words and Frequency");
                System.out.println("-----------------------------------");
                System.out.println("Word\tFrequency");
                for (int i = 0; i < word_count; i++) {
                    System.out.println(w1[i].word1 + "\t" + w1[i].frequency);
                }

            }
            // --------------------------------------------------------------------------------------

            else if (option == 4) {
                System.out.println();
                Scanner val = new Scanner(System.in);
                System.out.print("Input Query: ");
                String query = val.nextLine();

                String[] query_split = query.split(" ");

                for (int i = 0; i < tokenize.size(); i++) {
                    word[] w2 = new word[100];
                    for (int x = 0; x < 100; x++) {
                        w2[x] = new word();
                    }
                    int word_count_2 = 0;
                    for (int m = 0; m < query_split.length; m++) {
                        for (int j = 0; j < tokenize.get(i).size(); j++) {

                            if (query_split[m].compareTo(tokenize.get(i).get(j)) == 0) {
                                Boolean check_word = false;
                                for (int k = 0; k <= word_count_2; k++) {
                                    if (w2[k].word1.compareTo(tokenize.get(i).get(j)) == 0) {
                                        w2[k].frequency++;
                                        check_word = true;
                                    }
                                }
                                if (check_word == false) {
                                    w2[word_count_2].word1 = tokenize.get(i).get(j);
                                    w2[word_count_2].frequency++;
                                    word_count_2++;
                                }
                            }
                        }
                    }
                    int temp = i;
                    System.out.println();
                    System.out.println("inputfile" + ++temp + ".txt");
                    System.out.println("------------");
                    System.out.println("Word\tFrequency");
                    for (int z = 0; z < word_count_2; z++) {
                        System.out.println(w2[z].word1 + "\t" + w2[z].frequency);
                    }
                }
            }

            // --------------------------------------------------------------------------------------

            else if (option == 5) {
                System.exit(0);
            }
        }
    }
}
