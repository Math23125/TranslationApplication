package com.example.navbarre.fragment.Homepower;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

public class SwitchLanguage {
    private ImageView frenchFlag;
    private ImageView britishFlag;
    private TextView frenchText;
    private TextView englishText;
    private TranslationCall translationCall;

    // Correct constructor
    public SwitchLanguage(TranslationCall translationCall, ImageView frenchFlag, ImageView britishFlag, TextView frenchText, TextView englishText) {
        this.translationCall = translationCall;  // Correctly assign the passed instance
        this.frenchFlag = frenchFlag;
        this.britishFlag = britishFlag;
        this.frenchText = frenchText;
        this.englishText = englishText;
    }

    public void switchLanguages() {
        if (translationCall.getCurrentLang().equals("fr-en")) {
            translationCall.setLanguage("en-fr");
        } else {
            translationCall.setLanguage("fr-en");
        }
        Drawable tempFlag = frenchFlag.getDrawable();
        frenchFlag.setImageDrawable(britishFlag.getDrawable());
        britishFlag.setImageDrawable(tempFlag);

        CharSequence tempText = frenchText.getText();
        frenchText.setText(englishText.getText());
        englishText.setText(tempText);
    }
}
