package com.innovus;

import java.util.List;

public class QAndAContainer {

    private final String question;
    private final List<String> answers;

    public QAndAContainer(final String question, final List<String> answers) {
        this.answers = answers;
        this.question = question;
    }

    public String getQuestion() {
        return this.question;
    }

    public List<String> getAnswers() {
        return this.answers;
    }
}
