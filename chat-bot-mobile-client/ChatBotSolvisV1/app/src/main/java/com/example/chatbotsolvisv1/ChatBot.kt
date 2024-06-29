package com.example.chatbotsolvisv1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbotsolvisv1.api.ChatbotService
import com.example.chatbotsolvisv1.models.Message
import com.example.chatbotsolvisv1.models.MessageAdapter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatBot : AppCompatActivity() {

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var chatbotService: ChatbotService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageAdapter = MessageAdapter(ArrayList())
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(this@ChatBot)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        chatbotService = retrofit.create(ChatbotService::class.java)

        val sendButton = findViewById<Button>(R.id.sendButton)
        sendButton.setBackgroundResource(R.drawable.rounded_yellow)
        val inputMessage = findViewById<EditText>(R.id.inputMessage)
        sendButton.setOnClickListener {
            val userInput = inputMessage.text.toString().trim()
            if (userInput.isNotEmpty()) {
                val userMessage = Message(userInput, "sess123")
                displayMessage(userMessage)
                sendMessageToChatbot(userMessage)
                inputMessage.text.clear()
            }
        }
    }

    private fun sendMessageToChatbot(message: Message) {
        val call = chatbotService.sendMessage(message)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val chatbotMessage = response.body()?.string()
                chatbotMessage?.let  { displayMessage (Message(it, "bot") )}
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun displayMessage(message: Message) {
        Log.d("ChatBot", "Displaying message: ${message.content}")
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        messageAdapter.messages.add(message)
        messageAdapter.notifyItemInserted(messageAdapter.messages.size - 1)
        recyclerView.scrollToPosition(messageAdapter.messages.size - 1)
    }
}
