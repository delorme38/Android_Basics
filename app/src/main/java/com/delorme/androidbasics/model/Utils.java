package com.delorme.androidbasics.model;

import java.util.Arrays;

public class Utils {
    public static QuestionBank generateQuestionBank(){
        Question question1 = new Question(
                "Who is the creator of Android?",
                Arrays.asList(
                        "Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"
                ),
                0
        );

        Question question2 = new Question(
                "When did the first man land on the moon?",
                Arrays.asList(
                        "1958",
                        "1962",
                        "1967",
                        "1969"
                ),
                3
        );

        Question question3 = new Question(
                "What is the house number of The Simpsons?",
                Arrays.asList(
                        "42",
                        "101",
                        "666",
                        "742"
                ),
                3
        );

        return new QuestionBank(Arrays.asList(question1, question2, question3));
    }

//    ActivityResultLauncher<Intent> detailActivity = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        // There are no request codes
//                        Intent data = result.getData();
//                        if(data != null) {
//                            if (data.hasExtra(AMENDEDETAILS)) {
//                                Amende amende = (Amende) data.getParcelableExtra(AMENDEDETAILS);
//                                CustomAdapter adapter = (CustomAdapter) binding.rvAmandes.getAdapter();
//
//                                if (selectedAmende != -1) {
//                                    adapter.updateAmende(selectedAmende, amende);
//                                } else {
//                                    adapter.ajouterAmende(amende);
//                                }
//
//                            }
//                            if (data.hasExtra("actions")) {
//                                if (data.getSerializableExtra("actions") == Actions.Delete) {
//                                    ((CustomAdapter) Objects.requireNonNull(binding.rvAmandes.getAdapter())).supprimeAmendeSelectionner(selectedAmende);
//
//                                }
//                            }
//                        }
//                        selectedAmende = -1;
//                    }
//                }
//            });
}
