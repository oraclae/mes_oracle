<template>
  <div class="left-panel" @drop="dropHandler" @dragover.prevent>
    <p>拖放图片到此处</p>
  </div>
</template>

<script>
export default {
  methods: {
    dropHandler(event) {
      event.preventDefault();
      const image = event.dataTransfer.files[0];
      // 处理拖放的图片，例如显示图片或其他操作
    }
  }
}
</script>

<style scoped>
.left-panel {
  width: 200px;
  height: 300px;
  border: 1px solid #ccc;
  text-align: center;
  padding: 10px;
}
</style>
