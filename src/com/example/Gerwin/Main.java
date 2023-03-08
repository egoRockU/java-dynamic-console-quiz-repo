package com.example.Gerwin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static List<String> Questions = new ArrayList<>();

    public static void main(String[] args) {
        int index;
        String input;
        boolean exit = false;
        while (!exit){
            System.out.println("******************************************");
            System.out.println("Lets Play");
            System.out.println("******************************************");
            System.out.println("1. Start Quiz");
            System.out.println("2. Add Quiz");
            System.out.println("3. Review Quiz");
            System.out.println("Enter any key to exit.");
            System.out.print(">");
            getQuizzes();
            switch (scanner.nextLine()){
                case "1":
                    System.out.println("Choose topics.");
                    for (String q: Questions){
                        index = Questions.indexOf(q) + 1;
                        System.out.println(index + "." + q.replaceAll(".txt", ""));
                    }
                    System.out.print(">");
                    input = scanner.nextLine();
                    for (String q: Questions){
                        index = Questions.indexOf(q) + 1;
                        if (input.equals(String.valueOf(index))){
                            Quiz quiz = new Quiz("QuizFolder/" + q);
                            quiz.startQuiz();
                            break;
                        }
                    }
                    break;
                case "2":
                    System.out.println("Enter the quiz subject/topic/title (Warning: Entering existing topic would overwrite it).");
                    System.out.print(">");
                    input = scanner.nextLine();
                    addQuiz(input + ".txt");
                    break;
                case "3":
                    System.out.println("Choose topics to review.");
                    for (String q: Questions){
                        index = Questions.indexOf(q) + 1;
                        System.out.println(index + "." + q.replaceAll(".txt", ""));
                    }
                    System.out.print(">");
                    input = scanner.nextLine();
                    for (String q: Questions){
                        index = Questions.indexOf(q) + 1;
                        if (input.equals(String.valueOf(index))){
                            Quiz quiz = new Quiz("QuizFolder/" + q);
                            quiz.review();
                            break;
                        }
                    }
                    break;
                default:
                    exit = true;
                    break;
            }

        }
        }


   public static void getQuizzes(){
       try {
           Questions.clear(); //need to be cleared so that when called again, it won't iterate
           BufferedReader reader = new BufferedReader(new FileReader("QuizFolder/-QUIZZES.txt"));
           String line;
           while ((line = reader.readLine()) != null ){
               Questions.add(line);
           }


       } catch (FileNotFoundException e) {
           throw new RuntimeException(e);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

    public static void addQuiz(String fileName){
        try {
            int items = 0, count;
            String question, choice = "";
            char[] letterChoice = {'A', 'B', 'C', 'D', 'E'};
            boolean end = false;
            BufferedWriter writer = new BufferedWriter(new FileWriter("QuizFolder/" + fileName));
            BufferedWriter addData = new BufferedWriter(new FileWriter("QuizFolder/-QUIZZES.txt", true));
            while(!end){
                count = 0;
                items += 1;
                System.out.print("Write the question (type [-end] to stop): ");
                question =  scanner.nextLine();
                if (question.equalsIgnoreCase("-end")){
                    end = true;
                } else {
                    writer.write(items + ". " +question + "\n");
                    while (!choice.equalsIgnoreCase("-end") && count != 5){
                        System.out.print("Enter choice (add '*' at the end if correct answer | type [-end] to stop): ");
                        choice = scanner.nextLine();
                        if (choice.equalsIgnoreCase("-end")){
                            choice = ""; //choice need to be cleared
                            break;
                        }else{
                            if (choice.substring(choice.length()-1).equals("*")){
                                writer.write( letterChoice[count]+ ". " + choice + "\n");
                            } else {
                                writer.write( letterChoice[count]+ ". " + choice + "-\n");
                            }
                            count += 1;
                        }
                    }
                    writer.write("---\n");
                }
            }
            writer.close();

            addData.write("\n"+fileName);
            addData.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

