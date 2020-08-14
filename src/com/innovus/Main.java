package com.innovus;

import com.innovus.customException.IncorrectAnswerException;
import com.innovus.customException.IncorrectExpressionException;

import java.util.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Set<QAndAContainer> qAndASet = new HashSet<>();

    public static void main(final String[] args) {
        boolean endProgram;

        do {
            endProgram = executeLogic();
        } while (!endProgram);
    }

    private static boolean executeLogic() {
        System.out.println("Choose an option.\n1. Ask question\n2. Add new question and it's answers");

        int option = 0;

        boolean correctInput = false;
        do {
            try {
                option = sc.nextInt();

                if (option != 1 && option != 2) {
                    throw new IncorrectAnswerException("Please use the given numbers as input!");
                }

                correctInput = true;
            } catch (final InputMismatchException e) {
                System.out.println("Please use numeric input!");
                sc.next();
            } catch (final IncorrectAnswerException e) {
                System.out.println(e.getMessage());
            }
        } while (!correctInput);

        if (option == 1) {
            askQuestion();
        } else {
            putNewQandAToSet();
        }

        System.out.println("\nTo end the program, type 'e'. To keep it running, press any character.");

        return "e".equals(sc.next());
    }

    private static void askQuestion() {
        System.out.println("Ask your question.");

        sc.nextLine();
        final String question = sc.nextLine();

        final Optional<QAndAContainer> optional = qAndASet.stream()
                .filter(el -> question.equals(el.getQuestion()))
                .findAny();

        if (optional.isPresent()) {
            printAnswers(optional.get());
        } else {
            System.out.println(Constants.BASIC_ANSWER);
        }
    }

    private static void printAnswers(final QAndAContainer cont) {
        for (String ans : cont.getAnswers()) {
            System.out.println(ans);
        }
    }

    private static void putNewQandAToSet() {
        System.out.println("Adding question looks like: <question>? “<answer1>” “<answer2>” “<answerX>”");
        sc.nextLine();

        boolean correctInput = false;
        do {
            try {
                final String expression = sc.nextLine();

                if (isExpressionIncorrect(expression)) {
                    throw new IncorrectExpressionException("Please use the given format and give at least one answer.");
                }

                final String[] pieces = expression.split("\\?");

                final String question = pieces[0];
                final String[] answers = pieces[1].split("\"");
                final List<String> answerList = createAnswerList(answers);

                if (!isLengthCorrect(question, answerList)) {
                    throw new IncorrectExpressionException("Question and the answers must not contain more than 255 characters");
                }

                qAndASet.add(new QAndAContainer(question + "?", answerList));

                correctInput = true;
            } catch (final IncorrectExpressionException e) {
                System.out.println(e.getMessage());
            }
        } while (!correctInput);
    }

    private static boolean isExpressionIncorrect(final String exp) {
        return !exp.matches(".+\\?\".+\"");
    }

    private static List<String> createAnswerList(final String[] answers) {
        final List<String> answerList = new ArrayList<>();

        for (int i = 0 ; i < answers.length ; i++) {
            if (!"".equals(answers[i])) {
                answerList.add(answers[i]);
            }
        }

        return answerList;
    }

    private static boolean isLengthCorrect(final String question, final List<String> answerList) {
        final Optional<String> optional = answerList.stream()
                .filter(s -> s.length() > 255)
                .findAny();

        return optional.isEmpty() && question.length() <= 255;
    }
}
