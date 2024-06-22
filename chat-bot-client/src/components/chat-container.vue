<template>
  <div class="chat-container">
    <div class="chat-header">
      <h2 style="margin: 0;">Chat Bot</h2>
      <h3 style="margin: 0;">SOLVIS</h3>
    </div>
    <div class="messages-container">
      <div class="chat-box" v-for="message in messages" :key="message.id">
        <div :class="['message-time', message.sender]">{{ message.time }}</div>
        <div :class="['message', message.sender]">
          <font-awesome-icon :icon="message.sender === 'user' ? ['fas', 'user'] : ['fas', 'robot']" class="icon" />
          <span>{{ message.text }}</span>
        </div>
      </div>
    </div>
    <div class="input-box">
      <input v-model="userMessage" @keyup.enter="sendMessage" placeholder="Type a message" />
      <button @click="sendMessage">Send</button>
    </div>
  </div>
</template>

<script>
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faRobot, faUser } from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';

library.add(faRobot, faUser);

export default {
  components: {
    'font-awesome-icon': FontAwesomeIcon,
  },
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
        time: new Date().toLocaleTimeString(),
      };

      this.messages.push(newMessage);

      try {
        const response = await axios.post('http://localhost:3000/api/chat', { queryText: this.userMessage, sessionId: '20230132182302' });
        this.messages.push({
          id: this.messages.length + 1,
          text: response.data,
          sender: 'bot',
          time: new Date().toLocaleTimeString(),
        });
      } catch (error) {
        console.error('Error sending message:', error);
        this.messages.push({
          id: this.messages.length + 1,
          text: 'Error getting response from server.',
          sender: 'bot',
          time: new Date().toLocaleTimeString(),
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

.chat-header {
  text-align: left;
  padding: 10px;
  background-color: #363636;
  color: white;
}

.messages-container {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
  background: linear-gradient(45deg, #00000060, #ffc90489);
  scrollbar-width: thin;
  scrollbar-color: #FFCA04 #363636;
}

.messages-container::-webkit-scrollbar {
  width: 8px;
}

.messages-container::-webkit-scrollbar-track {
  background: #363636;
}

.messages-container::-webkit-scrollbar-thumb {
  background-color: #FFCA04;
  border-radius: 10px;
  border: 2px solid #363636;
}

.chat-box {
  margin: 5px 0;
}

.message-time {
  font-size: 0.8em;
  color: #666;
  margin-bottom: 2px;
}

.message-time.user {
  text-align: right;
}

.message-time.bot {
  text-align: left;
}

.message {
  display: flex;
  align-items: center;
  padding: 10px;
  border-radius: 4px;
  margin-bottom: 5px;
}

.message.user {
  background-color: #3636366e;
  align-self: flex-end;
  flex-direction: row-reverse;
  color: white;
  width: max-content;
  max-width: 80%;
  float: right;
  margin-left: 100%;
}

.message.bot {
  background-color: #ffc904b1;
  align-self: flex-start;
  flex-direction: row;
  width: max-content;
  max-width: 80%;
  float: left;
  margin-right: 100%;
}

.icon {
  width: 20px;
  height: 20px;
  margin: 0 10px;
}

.input-box {
  display: flex;
  border-top: 1px solid #ccc;
  padding: 10px;
  background-color: #363636;
}

input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #FFCA04;
  color:black;
}

button {
  padding: 10px;
  margin-left: 10px;
  border: none;
  background-color: #FFCA04;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #ffc904b1;
}
</style>
