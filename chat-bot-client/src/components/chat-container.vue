<template>
    <div class="chat-container">
      <div class="messages-container">
        <div class="chat-box" v-for="message in messages" :key="message.id">
          <div :class="['message', message.sender]">{{ message.text }}</div>
        </div>
      </div>
      <div class="input-box">
        <input v-model="userMessage" @keyup.enter="sendMessage" placeholder="Type a message" />
        <button @click="sendMessage">Send</button>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        messages: [],
        userMessage: '',
      };
    },
    methods: {
      async sendMessage() {
        if (this.userMessage.trim() === '') return;
  
        const newMessage = {
          id: this.messages.length + 1,
          text: this.userMessage,
          sender: 'user',
        };
  
        this.messages.push(newMessage);
  
        try {
          const response = await axios.post('http://localhost:3000/api/chat', { queryText: this.userMessage, sessionId: '20230132182302' });
          this.messages.push({
            id: this.messages.length + 1,
            text: response.data,
            sender: 'bot',
          });
        } catch (error) {
          console.error('Error sending message:', error);
          this.messages.push({
            id: this.messages.length + 1,
            text: 'Error getting response from server.',
            sender: 'bot',
          });
        }
  
        this.userMessage = '';
        this.scrollToBottom();
      },
      scrollToBottom() {
        this.$nextTick(() => {
          const messagesContainer = this.$el.querySelector('.messages-container');
          messagesContainer.scrollTop = messagesContainer.scrollHeight;
        });
      },
    },
  };
  </script>
  
  <style scoped>
  .chat-container {
    width: 100%;
    max-width: 600px;
    height: 75vh;
    display: flex;
    flex-direction: column;
    border: 1px solid #ccc;
    border-radius: 8px;
    overflow: hidden;
  }
  
  .messages-container {
    flex: 1;
    overflow-y: auto;
    padding: 10px;
    background-color: #f5f5f5;
  }
  
  .chat-box {
    margin: 5px 0;
  }
  
  .message {
    padding: 10px;
    border-radius: 4px;
    margin-bottom: 5px;
  }
  
  .message.user {
    background-color: #e1f5fe;
    align-self: flex-end;
  }
  
  .message.bot {
    background-color: #f1f8e9;
    align-self: flex-start;
  }
  
  .input-box {
    display: flex;
    border-top: 1px solid #ccc;
    padding: 10px;
    background-color: #fff;
  }
  
  input {
    flex: 1;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  button {
    padding: 10px;
    margin-left: 10px;
    border: none;
    background-color: #2196f3;
    color: white;
    border-radius: 4px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #1976d2;
  }
  </style>
  