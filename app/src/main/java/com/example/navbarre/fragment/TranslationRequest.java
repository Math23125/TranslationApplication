package com.example.navbarre.fragment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

class TranslationRequest {
    String text;

    // Constructor
    public TranslationRequest(String text) {
        this.text = text;
    }
}

class TranslationResponse {
    String translatedText;

    // Getter
    public String getTranslatedText() {
        return translatedText;
    }
}

interface TranslationService {
    @POST("/translate")
    Call<TranslationResponse> translate(@Body TranslationRequest request);
}

