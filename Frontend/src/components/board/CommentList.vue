<template>
 <div class="divide-y divide-gray-100">
   <div v-for="comment in comments" :key="comment.id" class="p-6">
     <div class="flex justify-between" v-if="!comment.isEditing">
       <div class="space-y-2">
         <div class="flex items-center gap-3">
           <span class="font-medium">{{ comment.username }}</span>
           <span class="text-sm text-gray-500">{{ comment.createdAt }}</span>
         </div>
         {{ comment.content }}
       </div>
       <div class="flex gap-2" v-if="isCommentAuthor(comment)">
         <button @click="startEdit(comment)" class="text-gray-600">수정</button>
         <button @click="deleteComment(comment.id)" class="text-gray-600">삭제</button>
       </div>
     </div>

     <div v-else class="space-y-3">
       <textarea 
         v-model="editContent"
         rows="3"
         class="w-full p-3 border rounded-lg"
       ></textarea>
       <div class="flex justify-end gap-2">
         <button @click="cancelEdit(comment)" class="px-3 py-1">취소</button>
         <button @click="updateComment(comment.id)" class="px-3 py-1">등록</button>
       </div>
     </div>
   </div>
 </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useBoardStore } from '@/stores/board'
import { useUserStore } from '@/stores/user'

const props = defineProps({
 boardId: {
   type: Number,
   required: true
 }
})

const boardStore = useBoardStore()
const userStore = useUserStore()
const comments = ref([])
const editContent = ref('')

const loadComments = async () => {
 try {
   const response = await boardStore.getComments({
     boardId: props.boardId,
     pageNum: 1,
     pageSize: 50
   })
   console.log(response.data)
   comments.value = response.data.list

 } catch (error) {
   console.error('Error loading comments:', error)
 }
}

const isCommentAuthor = (comment) => {
 return comment.userId === userStore.user?.id
}

const startEdit = (comment) => {
 comment.isEditing = true
 editContent.value = comment.content
}

const cancelEdit = (comment) => {
 comment.isEditing = false
 editContent.value = ''
}

const updateComment = async (commentId) => {
 try {
   await boardStore.updateComment({
     id: commentId,
     content: editContent.value
   })
   await loadComments()
 } catch (error) {
   console.error('Error updating comment:', error)
 }
}

const deleteComment = async (commentId) => {
 if (confirm('댓글을 삭제하시겠습니까?')) {
   try {
     await boardStore.deleteComment(commentId)
     await loadComments()
   } catch (error) {
     console.error('Error deleting comment:', error)
   }
 }
}

onMounted(() => {
 loadComments()
})
</script>