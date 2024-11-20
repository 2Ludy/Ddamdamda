<template>
 <div class="pb-20">
   <div class="shadow-sm mb-3">
     <div class="max-w-7xl mx-auto px-8 pt-20">
       <div class="py-12">
         <h1 class="text-4xl font-bold text-gray-900">커뮤니티</h1>
         <p>함께 운동하는 즐거움, 자유롭게 소통하세요</p>
       </div>
     </div>
   </div>
   
   <div class="max-w-7xl mx-auto">
     <board-list />
   </div>
 </div>
</template>

<script setup>
import BoardList from '@/components/board/BoardList.vue'
import { onMounted } from 'vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

onMounted(async () => {
 // 사용자 인증 상태 체크
 if (!userStore.isAuthenticated) {
   const token = localStorage.getItem('token')
   if (token) {
     await userStore.checkAuth()
   }
 }
})
</script>

<style scoped>
.header-bg {
 background-color: #f9fafb;
}
</style>