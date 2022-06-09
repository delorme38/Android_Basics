package com.delorme.androidbasics.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collections;
import java.util.List;

public class QuestionBank implements Parcelable {

    private List<Question> mQuestionList;
    private int mQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        // Shuffle the question list before storing it
        mQuestionList = questionList;
        Collections.shuffle(mQuestionList);
    }

    protected QuestionBank(Parcel in) {
        mQuestionList = in.createTypedArrayList(Question.CREATOR);
        mQuestionIndex = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mQuestionList);
        dest.writeInt(mQuestionIndex);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuestionBank> CREATOR = new Creator<QuestionBank>() {
        @Override
        public QuestionBank createFromParcel(Parcel in) {
            return new QuestionBank(in);
        }

        @Override
        public QuestionBank[] newArray(int size) {
            return new QuestionBank[size];
        }
    };

    public Question getCurrentQuestion() {
        return mQuestionList.get(mQuestionIndex);
    }

    public Question getNextQuestion() {
        mQuestionIndex++;
        return getCurrentQuestion();
    }

}


