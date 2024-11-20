<template>
 <div class="max-w-3xl mx-auto px-6">
  
  <!-- 페이지 타이틀 섹션 -->
  <div class="max-w-7xl mx-auto px-8 pt-16">
    <div class="py-10">
      <h1 class="text-4xl font-bold text-gray-900">글쓰기</h1>
      <p class="mt-2 text-gray-600">새로운 게시글을 작성하세요</p>
    </div>
  </div>

   <div class="bg-white rounded-2xl shadow-lg overflow-hidden mb-12">
     <div class="p-6 mb-6">
       <select v-model="category" class="w-full p-3 border rounded-lg mb-6">
         <option value="" disabled>카테고리 선택</option>
         <option value="자유">자유</option>
         <option value="운동인증">운동인증</option>
       </select>

       <input
         v-model="title"
         type="text"
         class="w-full p-3 border rounded-lg mb-6"
         placeholder="제목을 입력하세요"
       />

       <textarea
         v-model="content"
         rows="10"
         class="w-full p-3 border rounded-lg mb-6"
         placeholder="내용을 입력하세요"
       ></textarea>

       <div class="mb-6">
         <input
           type="file"
           ref="fileInput"
           multiple
           accept="image/*"
           @change="handleFileChange"
           class="hidden"
         />
         <button @click="$refs.fileInput.click()" class="px-4 py-2 border rounded-lg">
           파일 업로드
         </button>
         <div class="flex mt-4">
           <div v-for="(file, index) in files" :key="index" class="flex items-center mb-2 mr-4">
             <span class="text-sm text-gray-700">{{ file.name }}</span>
             <button @click="removeFile(index)" class="ml-2 text-red-500">×</button>
           </div>
         </div>
       </div>

       <div class="flex justify-between">
         <button @click="cancel" class="px-4 py-2 text-gray-600">
           취소
         </button>
         <button @click="submit" class="px-4 py-2 bg-black text-[#dcff1f] rounded-full">
           등록
         </button>
       </div>
     </div>
   </div>
 </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useBoardStore } from '@/stores/board'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const boardStore = useBoardStore()
const userStore = useUserStore()

const category = ref('')
const title = ref('')
const content = ref('')
const files = ref([])

const handleFileChange = (event) => {
 const newFiles = Array.from(event.target.files)
 files.value = [...files.value, ...newFiles]
}

const removeFile = (index) => {
 files.value.splice(index, 1)
}

const submit = async () => {
 if (!category.value || !title.value || !content.value) {
   alert('모든 필드를 입력해주세요')
   return
 }

 try {
    const boardData = {
        category: category.value,
        title: title.value,
        content: content.value
    }
    
    console.log('전송할 데이터:', boardData)
    await boardStore.createBoard(boardData)

    // 파일이 있다면 별도로 처리
    if (files.value.length > 0) {
      const formData = new FormData()
      files.value.forEach(file => {
        formData.append('files', file)
      })
      // 파일 업로드 API 호출
    }

    router.push('/board')
   } catch(error) {
   console.error('Error creating board:', error)
   if (error.response?.data) {
      console.error('Server error details:', error.response.data)
    }
  }
}

const cancel = () => {
 router.push('/board')
}
</script>