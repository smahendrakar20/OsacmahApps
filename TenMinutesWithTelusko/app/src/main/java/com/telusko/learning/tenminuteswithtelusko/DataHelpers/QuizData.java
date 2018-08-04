package com.telusko.learning.tenminuteswithtelusko.DataHelpers;

import com.telusko.learning.tenminuteswithtelusko.Quiz;

public class QuizData {
    String question,option1,option2,option3,option4,answer,userOption,display;

    QuizData()
    {
        this.display="NoColor";
        this.question="";
        this.option1="";
        this.option2="";
        this.option3="";
        this.option4="";
        this.answer="";
        this.userOption="NotAnswered";
    }

    QuizData( String question,String option1,String option2,String option3,String option4,String answer)
    {
        this.question=question;
        this.option1=option1;
        this.option2=option2;
        this.option3=option3;
        this.option4=option4;
        this.answer=answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setUserOption(String userOption) {
        this.userOption = userOption;
    }

    public String getUserOption() {
        return userOption;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
