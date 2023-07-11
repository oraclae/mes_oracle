<template>
  <div class="container">
    <div class="left-panel" @drop="handleDrop" @dragover.prevent>
      <p>拖放图片到此处</p>
    </div>
    <div class="middle-panel">
      <button @click="recognizeText">识别文字</button>
    </div>
    <div class="right-panel">
      <p>{{ recognizedText }}</p>
    </div>
  </div>
</template>

<script>
import { createWorker } from 'tesseract.js';

export default {
  data() {
    return {
      image: null,
      recognizedText: ''
    };
  },
  methods: {
    handleDrop(event) {
      event.preventDefault();
      const file = event.dataTransfer.files[0];
      this.loadImage(file);
    },
    async loadImage(file) {
      this.image = await this.readFileAsDataURL(file);
    },
    readFileAsDataURL(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = (event) => {
          resolve(event.target.result);
        };
        reader.onerror = (error) => {
          reject(error);
        };
        reader.readAsDataURL(file);
      });
    },
    async recognizeText() {
      if (this.image) {
        const worker = createWorker();
        await worker.load();
        await worker.loadLanguage('eng');
        await worker.initialize('eng');

        const { data } = await worker.recognize(this.image);
        this.recognizedText = data.text;

        await worker.terminate();
      }
    }
  },
  mounted() {
    // 可以在这里初始化Tesseract.js工作器
  }
};
</script>

<style>
.container {
  display: flex;
  justify-content: space-between;
  height: 400px;
}

.left-panel,
.middle-panel,
.right-panel {
  flex: 1;
  border: 1px solid #ccc;
  padding: 20px;
  text-align: center;
}

.left-panel {
  background-color: #f2f2f2;
}

.middle-panel {
  background-color: #e0e0e0;
}

.right-panel {
  background-color: #f9f9f9;
}
</style>
