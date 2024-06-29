package com.example.chatbotsolvisv1.api;

import com.example.chatbotsolvisv1.models.Message;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatbotService {
    @POST("api/chat")
    Call<ResponseBody> sendMessage(@Body Message message);
}