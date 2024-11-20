<template>
 <div class="p-6">
   <div class="flex justify-between" v-if="!isEditing">
     <div class="space-y-2">
       <div class="flex items-center gap-3">
         <span class="font-medium">{{ comment.username }}</span>
         <span class="text-sm text-gray-500">{{ comment.createdAt }}</span>
       </div>
       {{ comment.content }}
     </div>
     <div class="flex gap-2" v-if="isAuthor">
       <button @click="startEdit" class="text-gray-600">수정</button>
       <button @click="$emit('delete', comment.id)" class="text-gray-600">삭제</button>
     </div>
   </div>

   <div v-else class="space-y-3">
     <textarea 
       v-model="editedContent"
       rows="3"
       class="w-full p-3 border rounded-lg"
     ></textarea>
     <div class="flex justify-end gap-2">
       <button @click="cancelEdit" class="px-3 py-1">취소</button>
       <button @click="saveEdit" class="px-3 py-1">등록</button>
     </div>
   </div>
 </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useUserStore } from '@/stores/user'

const props = defineProps({
 comment: {
   type: Object,
   required: true
 }
})

const emit = defineEmits(['update', 'delete'])
const userStore = useUserStore()

const isEditing = ref(false)
const editedContent = ref(props.comment.content)

const isAuthor = computed(() => {
 return props.comment.userId === userStore.user?.id
})

const startEdit = () => {
 isEditing.value = true
}

const cancelEdit = () => {
 isEditing.value = false
 editedContent.value = props.comment.content
}

const saveEdit = () => {
 emit('update', {
   id: props.comment.id,
   content: editedContent.value
 })
 isEditing.value = false
}
</script>