<template>
  <ChatContainer/>
</template>

<script>
import ChatContainer from './components/chat-container.vue';

export default {
  name: 'App',
  components: {
    ChatContainer
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  display: flex;
  justify-content: center; /* Center horizontally */
  align-items: center;     /* Center vertically */
  height: 100vh;           /* Full viewport height */
  width: 100vw;            /* Full viewport width (if needed) */
}
</style>
