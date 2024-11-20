<template>
  <div class="max-w-7xl mx-auto px-6 py-20">
    <div class="bg-white rounded-2xl shadow-lg overflow-hidden mb-8">
      <!-- 게시글 헤더 -->
      <div class="p-6 border-b border-gray-100">
        <div class="flex items-center gap-3 mb-4">
          <span class="px-3 py-1 rounded-full text-xs font-medium" 
                style="background-color: #dcff1f">
            {{ board?.category }}
          </span>
          <span class="text-sm text-gray-500">{{ board?.createdAt }}</span>
        </div>

        <h2 class="text-2xl font-bold text-gray-900 mb-4">{{ board?.title }}</h2>

        <div class="flex items-center justify-between">
          <div class="flex items-center gap-4">
            <span class="text-gray-700">{{ board?.username }}</span>
            <span class="text-sm text-gray-500">조회 {{ board?.viewCount }}</span>
          </div>

          <div class="flex gap-2" v-if="isAuthor">
            <button @click="editBoard" class="text-gray-600 hover:text-gray-900">
              수정
            </button>
            <button @click="deleteBoard" class="text-gray-600 hover:text-gray-900">
              삭제
            </button>
          </div>
        </div>
      </div>

      <!-- 게시글 내용 -->
      <div class="p-6">
        <div class="text-gray-800 leading-relaxed whitespace-pre-wrap">
          {{ board?.content }}
        </div>
      </div>

      <!-- 좋아요 & 목록 -->
      <div class="p-6 border-t border-gray-100">
        <div class="flex items-center justify-between">
          <button @click="toggleLike" class="flex items-center gap-2">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path :class="{ 'fill-current': hasLiked }" 
                    stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                    d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
            </svg>
            {{ board?.likesCount }}
          </button>
          <router-link to="/board" class="text-gray-600 hover:text-gray-900">
            목록으로
          </router-link>
        </div>
      </div>
    </div>

    <!-- 댓글 목록 -->
    <div class="bg-white rounded-2xl shadow-lg overflow-hidden">
    <!-- 댓글 작성 폼 -->
    <div class="p-6 border-b border-gray-100">
      <textarea
        v-model="commentContent"
        rows="3"
        class="w-full p-3 border rounded-lg"
        placeholder="댓글을 입력하세요"
      ></textarea>
      <div class="flex justify-end mt-3">
        <button @click="addComment" 
                class="px-4 py-2 bg-black text-[#dcff1f] rounded-full">
          등록
        </button>
      </div>
    </div>

    <!-- 댓글 목록 표시 -->
    <div class="divide-y divide-gray-100">
      <div v-if="boardStore.comments.length > 0">
        <div v-for="comment in boardStore.comments" 
             :key="comment.id" 
             class="p-6">
          <div class="flex justify-between">
            <div class="space-y-2">
              <div class="flex items-center gap-3">
                <span class="font-bold text-sm">{{ comment.username }}</span> 
                <span class="text-sm text-gray-500">{{ comment.createdAt }}</span>
              </div>
              <!-- 수정 모드가 아닐 때 -->
              <div v-if="!comment.isEditing">
                <div>{{ comment.content }}</div>
                <!-- 내 댓글일 경우에만 수정/삭제 버튼 표시 -->
                <div v-if="comment.userId === userStore.user?.id" class="mt-2">
                  <button @click="startEdit(comment)" 
                          class="text-sm text-gray-600 hover:text-gray-900 mr-2">
                    수정
                  </button>
                  <button @click="deleteComment(comment.id)" 
                          class="text-sm text-gray-600 hover:text-gray-900">
                    삭제
                  </button>
                </div>
              </div>
              <!-- 수정 모드일 때 -->
              <div v-else>
                <textarea
                  v-model="comment.editContent"
                  rows="3"
                  class="w-full p-2 border rounded-lg"
                ></textarea>
                <div class="mt-2">
                  <button @click="updateComment(comment)" 
                          class="text-sm text-gray-600 hover:text-gray-900 mr-2">
                    저장
                  </button>
                  <button @click="cancelEdit(comment)" 
                          class="text-sm text-gray-600 hover:text-gray-900">
                    취소
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="p-6 text-center text-gray-500">
        작성된 댓글이 없습니다.
      </div>
    </div>
  </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'
import { useBoardStore } from '@/stores/board'
import { useUserStore } from '@/stores/user'
import CommentList from './CommentList.vue'

const route = useRoute()
const router = useRouter()
const boardStore = useBoardStore()
const userStore = useUserStore()

const { board } = storeToRefs(boardStore)
const { user } = storeToRefs(userStore) 

const boardId = computed(() => parseInt(route.params.id))
const commentContent = ref('')
const hasLiked = ref(false)

const isAuthor = computed(() => {
  console.log('현재 게시글:', board.value)
  console.log('현재 사용자:', user.value)
  return board.value?.userId === user.value?.id
})

const loadBoard = async () => {
 await boardStore.getBoard(boardId.value)
 // 좋아요 상태 체크 로직 필요
}

const editBoard = () => {
 router.push(`/board/${boardId.value}/edit`)
}

const deleteBoard = async () => {
 if (confirm('정말 삭제하시겠습니까?')) {
   try {
     await boardStore.deleteBoard(boardId.value)
     router.push('/board')
   } catch (error) {
     console.error('Error deleting board:', error)
     alert('게시글 삭제에 실패했습니다.')
   }
 }
}

const toggleLike = async () => {
 try {
   if (hasLiked.value) {
     await boardStore.unlikeBoard(boardId.value)
   } else {
     await boardStore.likeBoard(boardId.value)
   }
   await checkLikeStatus()
   await loadBoard()
 } catch (error) {
   console.error('좋아요 토글 실패:', error)
 }
}

// 좋아요 상태 체크
const checkLikeStatus = async () => {
  try {
    const response = await boardStore.checkLikeStatus(boardId.value)
    hasLiked.value = response
  } catch (error) {
    console.error('좋아요 상태 체크 에러:', error)
    hasLiked.value = false
  }
}

const loadComments = async () => {
  try {
    await boardStore.getComments({
      boardId: boardId.value,
      pageNum: 1,
      pageSize: 20
    })
  } catch (error) {
    console.error('Error loading comments:', error)
  }
}

const addComment = async () => {
  if (!commentContent.value.trim()) return
  
  try {
    const commentData = {
      boardId: boardId.value,
      content: commentContent.value.trim()
    }
    
    await boardStore.addComment(commentData)
    commentContent.value = ''
    await loadComments()  // 댓글 목록 새로고침
  } catch (error) {
    console.error('Error adding comment:', error)
    alert('댓글 작성에 실패했습니다.')
  }
}

const startEdit = (comment) => {
  comment.isEditing = true
  comment.editContent = comment.content
}

const cancelEdit = (comment) => {
  comment.isEditing = false
  comment.editContent = ''
}

const updateComment = async (comment) => {
  try {
    await boardStore.updateComment({
      id: comment.id,
      content: comment.editContent
    })
    comment.isEditing = false
    await loadComments()
  } catch (error) {
    console.error('Error updating comment:', error)
    alert('댓글 수정에 실패했습니다.')
  }
}

const deleteComment = async (commentId) => {
  if (confirm('댓글을 삭제하시겠습니까?')) {
    try {
      await boardStore.deleteComment(commentId)
      await loadComments()
    } catch (error) {
      console.error('Error deleting comment:', error)
      alert('댓글 삭제에 실패했습니다.')
    }
  }
}

watch(() => route.params.id, async (newId) => {
  if (newId) {
    boardStore.clearComments()
    await loadBoard()
    await loadComments()
    await checkLikeStatus()
  }
}, { immediate: true })

</script>