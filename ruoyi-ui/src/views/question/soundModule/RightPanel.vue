<template>
  <div class="right-panel">
    <p>{{ imageText }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      imageText: ''
    };
  }
}
</script>

<style scoped>
.right-panel {
  width: 200px;
  height: 300px;
  border: 1px solid #ccc;
  text-align: center;
  padding: 10px;
}
</style>
