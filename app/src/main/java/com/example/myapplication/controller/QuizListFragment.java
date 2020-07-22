package com.example.myapplication.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Question;
import com.example.myapplication.repository.QuestionRepository;

import java.util.List;


public class QuizListFragment extends Fragment {
   private RecyclerView mRecyclerViewQuestion;
    public QuizListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_quiz_list, container, false);
        mRecyclerViewQuestion = view.findViewById(R.id.recyclerViewQuestion);
        mRecyclerViewQuestion.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Question> questions = QuestionRepository.getInstance().getQuestions();
        QuestionAdapter adapter = new QuestionAdapter(questions);
        mRecyclerViewQuestion.setAdapter(adapter);
        return view;
    }

    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>{
        List<Question> mQuestions ;

        public QuestionAdapter(List<Question> mQuestions) {
            this.mQuestions = mQuestions;
        }

        public List<Question> getmQuestions() {
            return mQuestions;
        }

        public void setmQuestions(List<Question> mQuestions) {
            this.mQuestions = mQuestions;
        }

        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_row_question, parent, false);
            QuestionHolder questionHolder = new QuestionHolder(view);
            return questionHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
        Question question = mQuestions.get(position);
        holder.bindQuestion(question);
        }

        @Override
        public int getItemCount() {
            return mQuestions.size();
        }
    }

    private class QuestionHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewQuestionRow;
        private TextView mTextViewColorRow;
        private CheckBox mCheckBoxAnswerRow;
        private CheckBox mCheckBoxCheatRow;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewQuestionRow = itemView.findViewById(R.id.textViewQuestionRow);
            mTextViewColorRow=itemView.findViewById(R.id.textViewColorRow);
            mCheckBoxAnswerRow=itemView.findViewById(R.id.checkBoxAnswerRow);
            mCheckBoxCheatRow=itemView.findViewById(R.id.checkBoxCheatRow);
        }

        public void bindQuestion(Question question){
            mTextViewQuestionRow.setText(question.getmText());
            mTextViewColorRow.setText(String.valueOf(question.getmColor()));
            mCheckBoxAnswerRow.setChecked(question.ismAnswerTrue());
            mCheckBoxCheatRow.setChecked(question.ismIsCheat());
        }
    }
}