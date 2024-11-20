<template>
 <div class="max-w-3xl mx-auto px-6 py-8">
   <div class="bg-white rounded-2xl shadow-lg overflow-hidden my-20">
     <div class="p-6 mb-6">
       <div class="mb-6">
         <span class="text-gray-700">카테고리: {{ board?.category }}</span>
       </div>

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
         
         <!-- 기존 이미지 -->
         <div v-if="existingFiles.length" class="mt-4">
           <h4 class="text-sm font-medium mb-2">기존 이미지</h4>
           <div class="flex flex-wrap gap-4">
             <div v-for="file in existingFiles" :key="file.id" class="flex items-center">
               <span class="text-sm text-gray-700">{{ file.fileName }}</span>
               <button @click="removeExistingFile(file.id)" class="ml-2 text-red-500">×</button>
             </div>
           </div>
         </div>

         <!-- 새로 추가된 이미지 -->
         <div v-if="newFiles.length" class="mt-4">
           <h4 class="text-sm font-medium mb-2">새로 추가된 이미지</h4>
           <div class="flex flex-wrap gap-4">
             <div v-for="(file, index) in newFiles" :key="index" class="flex items-center">
               <span class="text-sm text-gray-700">{{ file.name }}</span>
               <button @click="removeNewFile(index)" class="ml-2 text-red-500">×</button>
             </div>
           </div>
         </div>
       </div>

       <div class="flex justify-between">
         <button @click="cancel" class="px-4 py-2 text-gray-600">
           취소
         </button>
         <button @click="submit" class="px-4 py-2 bg-black text-[#dcff1f] rounded-full">
           수정
         </button>
       </div>
     </div>
   </div>
 </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useBoardStore } from '@/stores/board'
import { storeToRefs } from 'pinia'

const route = useRoute()
const router = useRouter()
const boardStore = useBoardStore()
const { board } = storeToRefs(boardStore)

const category = ref('')
const title = ref('')
const content = ref('')
const existingFiles = ref([])
const newFiles = ref([])
const boardId = parseInt(route.params.id)


const loadBoard = async () => {
 await boardStore.getBoard(boardId)
 category.value = board.value?.category
 title.value = board.value?.title
 content.value = board.value?.content
 // 기존 이미지 로드
 existingFiles.value = await boardStore.getBoardImages(boardId)
}

const handleFileChange = (event) => {
 const files = Array.from(event.target.files)
 newFiles.value = [...newFiles.value, ...files]
}

const removeExistingFile = async (fileId) => {
 if (confirm('이미지를 삭제하시겠습니까?')) {
   await boardStore.deleteImage(fileId)
   existingFiles.value = existingFiles.value.filter(f => f.id !== fileId)
 }
}

const removeNewFile = (index) => {
 newFiles.value.splice(index, 1)
}

const submit = async () => {
 if (!title.value || !content.value) {
   alert('제목과 내용을 입력해주세요')
   return
 }

 try {
  const boardData = {
      id: boardId,
      title: title.value,
      content: content.value
    }
    console.log('수정할 게시글 ID:', boardId)
    console.log('수정할 데이터:', boardData)
    await boardStore.updateBoard(boardData)
    router.push(`/board/${boardId}`)
 } catch (error) {
   console.error('Error updating board:', error)
   alert('게시글 수정에 실패했습니다.')
 }
}

const cancel = () => {
 router.push(`/board/${boardId}`)
}

onMounted(() => {
 loadBoard()
})
</script>