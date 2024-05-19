package com.example.navbarre;

import com.example.navbarre.TranslationResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface HuggingFaceService {

    @FormUrlEncoded
    @Headers("Authorization: Bearer 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")
    @POST("models/Helsinki-NLP/opus-mt-fr-en")
    Call<List<TranslationResponse>> translateText(@Field("text") String text);



}



