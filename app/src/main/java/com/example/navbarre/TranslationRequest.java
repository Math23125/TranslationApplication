package com.example.navbarre;

public class TranslationRequest {
    private String text; // Text to be translated
    private String model; // Model ID

    public TranslationRequest(String text, String model) {
        this.text = text;
        this.model = model;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
