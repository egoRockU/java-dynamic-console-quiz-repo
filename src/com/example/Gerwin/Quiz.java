package com.example.Gerwin;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Quiz {
    private final String fileName;
    private String question;
    private static List<String> choices;
    private static LinkedHashMap<String, List<String>> questions;
    private static final Scanner scanner = new Scanner(System.in);

    public Quiz(String fileName) {
        this.fileName = fileName;
    }

    private void getQuestions(){
        choices = new ArrayList<>();
        questions = new LinkedHashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            //creating pattern for matching
            Pattern qPat = Pattern.compile("\\d{1,3}[.] .*");
            Pattern cPat = Pattern.compile("[A-E][.] .*[^*]");
            Matcher Qm, Cm;

            while ((line = reader.readLine()) != null) {
                Qm = qPat.matcher(line);
                Cm = cPat.matcher(line);
                //if line matches..
                if (Qm.find()) {
                    question = line;
                    choices = new ArrayList<>();
                }
                if (Cm.find()) {
                    //line = line.replaceAll(".$",""); //using regex to remove - and *
                    choices.add(line);
                }
                if (line.equals("---")){
                    questions.put(question, choices);
                }

            }
            reader.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startQuiz(){
        int index, score = 0, count = 1, item;
        getQuestions();
        List<String> q = new ArrayList<>(questions.keySet()); //list of keys(questions)
        item = q.size();
        Random random = new Random();
        while (q.size() != 0){
            index = random.nextInt(q.size());//get random index from questions keyset
            //Pattern for correct answer
            String question, userAnswer;
            char correctAnswer = 'A';
            Pattern aPat = Pattern.compile("[A-E]. .*\\*");
            Matcher Am;
            //get the correct answer
            for (String a: questions.get(q.get(index))){
                Am = aPat.matcher(a);
                if (Am.find()){
                    correctAnswer = a.charAt(0); //get only the letter
                }
            }
            //show question
            System.out.println("----------------------------------");
            System.out.println("Item " + count + "/" + item);
            question = q.get(index).replaceAll("\\d{1,3}[.]\\s",""); //remove item numbers
            System.out.println(question);
            for (String c: questions.get(q.get(index))){
                c = c.replaceAll(".$",""); //remove - and *
                System.out.println(c);
            }
            System.out.print("Your answer: ");
            userAnswer = scanner.nextLine();
            if (userAnswer.equalsIgnoreCase(String.valueOf(correctAnswer))){
                score += 1;
                count += 1;
                System.out.println("Correct!");
            }else{
                count += 1;
                System.out.println("Wrong!");
            }
            System.out.println("----------------------------------");

            questions.remove(q.get(index));
            q.remove(index);
        }
        System.out.println("Score: " + score + "/" + item);
    }

    public void review(){
        getQuestions();
        List<String> questionList = new ArrayList<>(questions.keySet());
        Pattern aPat = Pattern.compile("[A-E]. .*\\*");
        Matcher Am;
        String correctAnswer = "";
        for (String question: questionList){
            System.out.println(question);
            //choices printing
            for (String choice: questions.get(question)){
                Am = aPat.matcher(choice);
                if (Am.find()){
                    correctAnswer = choice.replaceAll(".$", "");
                }
                choice = choice.replaceAll(".$", "");
                System.out.println(choice);
            }
            System.out.println("Correct answer: " + correctAnswer);
            System.out.println("-------------------------------------------------------");
        }
    }


}
