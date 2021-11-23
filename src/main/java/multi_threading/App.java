package multi_threading;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class App extends Thread {
    static BST b1 = new BST();
    static Vector<Vector<String>> tokenize = new Vector<Vector<String>>();

    public static int menu() {
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
        int option = opt.nextInt();
        if (option < 1 || option > 5) {
            option = 0;
            throw new MenuOptionException("Option input cannot be less than 1 or greater than 5");
        } else
            return option;
    }

    public void run() {

        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(currentThread().getName());
        if (currentThread().getName().compareTo("vocabulary.txt") == 0) {
            try {
                String line = "";
                BufferedReader br = Files.newBufferedReader(path, charset);
                while ((line = br.readLine()) != null) {
                    String temp = line;
                    b1.insert(temp);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < tokenize.size(); i++) {

            if (currentThread().getName().compareTo(tokenize.get(i).get(0)) == 0) {
                try {
                    String line = "";
                    BufferedReader br = Files.newBufferedReader(path, charset);
                    while ((line = br.readLine()) != null) {
                        String[] temp = line.split(" ");
                        for (int k = 0; k < temp.length; k++) {
                            tokenize.get(i).add(temp[k]);
                        }
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i < args.length; i++) {
            Vector test = new Vector();
            tokenize.add(test);
        }

        for (int i = 1; i < args.length; i++) {
            tokenize.get(i - 1).add(args[i]);
        }

        // File Names and Number of Files
        System.out.println("Number of Files: " + args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }

        for (int i = 0; i < args.length; i++) {
            App a1 = new App();
            a1.setName(args[i]);
            a1.start();

        }

        int option = 0;
        while (true) {
            Boolean check_option = false;
            while (check_option == false) {
                try {
                    option = menu();
                    if(option>0 && option <6)
                    {
                        check_option = true;
                    }
                } catch (MenuOptionException e) {
                    System.out.println(e);
                }
            }

            // --------------------------------------------------------------------------------------

            if (option == 1) {
                // Making BST tree
                System.out.println();
                System.out.println("Displaying BST build from Vocabulary File");
                System.out.println("-----------------------------------------");
                b1.show();
            }

            // --------------------------------------------------------------------------------------

            else if (option == 2) {
                System.out.println();
                System.out.println("Displaying Vectors build from Input files");
                System.out.println("-----------------------------------------");
                for (int i = 0; i < tokenize.size(); i++) {
                    for (int j = 1; j < tokenize.get(i).size(); j++) {
                        System.out.print(tokenize.get(i).get(j) + " ");
                    }
                    System.out.println();
                }
            }

            // --------------------------------------------------------------------------------------

            else if (option == 3) {
                Vector<word> w1 = new Vector<word>();
                for (int i = 0; i < tokenize.size(); i++) {
                    for (int j = 1; j < tokenize.get(i).size(); j++) {

                        Boolean check = false;
                        check = b1.compare(tokenize.get(i).get(j));
                        if (check == true) {
                            // Checking word repetition
                            Boolean check_word = false;
                            for (int k = 0; k < w1.size(); k++) {
                                if (w1.get(k).word1.compareTo(tokenize.get(i).get(j)) == 0) {
                                    w1.get(k).frequency++;
                                    check_word = true;
                                }
                            }

                            if (check_word == false) {
                                word temp = new word();
                                temp.word1 = tokenize.get(i).get(j);
                                temp.frequency++;
                                w1.add(temp);

                            }
                        }
                    }
                }

                System.out.println();
                System.out.println("Viewing Matched Words and Frequency");
                System.out.println("-----------------------------------");
                System.out.println("Word\tFrequency");
                for (int i = 0; i < w1.size(); i++) {
                    System.out.println(w1.get(i).word1 + "\t" + w1.get(i).frequency);
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
                    Vector<word> w2 = new Vector<word>();

                    for (int m = 0; m < query_split.length; m++) {
                        for (int j = 1; j < tokenize.get(i).size(); j++) {

                            if (query_split[m].compareTo(tokenize.get(i).get(j)) == 0) {
                                Boolean check_word = false;
                                for (int k = 0; k < w2.size(); k++) {
                                    if (w2.get(k).word1.compareTo(tokenize.get(i).get(j)) == 0) {
                                        w2.get(k).frequency++;
                                        check_word = true;
                                    }
                                }
                                if (check_word == false) {
                                    word temp = new word();
                                    temp.word1 = tokenize.get(i).get(j);
                                    temp.frequency++;
                                    w2.add(temp);
                                }
                            }
                        }
                    }
                    int temp = i;
                    System.out.println();
                    System.out.println("inputfile" + ++temp + ".txt");
                    System.out.println("------------");
                    System.out.println("Word\tFrequency");
                    for (int z = 0; z < w2.size(); z++) {
                        System.out.println(w2.get(z).word1 + "\t" + w2.get(z).frequency);
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
