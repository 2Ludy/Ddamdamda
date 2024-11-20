<template>
 <div class="min-h-screen bg-gray-50">
   <!-- Header -->
   <header class="fixed w-full z-50 shadow-sm bg-gray-50">
     <div class="max-w-7xl mx-auto px-6">
       <div class="flex items-center justify-between h-14">
         <!-- Logo -->
         <div class="flex items-center space-x-12">
           <router-link to="/" class="flex items-center space-x-2">
             <div class="w-8 h-8 rounded-full flex items-center justify-center bg-black">
               <span class="font-bold text-xl mb-2" style="color: #dcff1f">;</span>
             </div>
             <span class="font-bold text-lg">DdamDamDa</span>
           </router-link>

           <!-- Navigation -->
           <nav>
             <router-link 
               v-for="link in navLinks" 
               :key="link.path"
               :to="link.path"
               class="text-sm mx-3"
               :class="route.path.includes(link.path) ? 'text-gray-900 font-medium' : 'text-gray-600'"
             >
               {{ link.name }}
             </router-link>
           </nav>
         </div>

         <!-- Auth -->
         <div v-if="!userStore.isAuthenticated" class="flex items-center space-x-4">
           <router-link 
             to="/signup"
             class="px-4 py-1.5 text-gray-600 hover:text-gray-900 text-sm"
           >
             SIGN UP
           </router-link>
           <router-link 
             to="/login"
             class="px-4 py-1.5 bg-black text-white rounded-full hover:bg-gray-800 text-sm"
           >
             LOGIN
           </router-link>
         </div>
         <div v-else class="flex items-center space-x-6">
           <!-- Group -->
           <router-link to="/my-groups" class="text-gray-600 hover:text-gray-900">
             <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
               <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                     d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
             </svg>
           </router-link>
           <!-- Notifications -->
           <div class="relative">
             <!-- Notification Icon -->
             <button @click="toggleNotifications" class="relative">
               <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                 <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" 
                       d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
               </svg>
               <span v-if="notificationCount" 
                     class="absolute -top-1 -right-1 bg-red-500 text-white rounded-full text-xs px-1">
                 {{ notificationCount }}
               </span>
             </button>
             <!-- Notifications Dropdown -->
             <div v-if="showNotifications" 
                  class="absolute right-0 mt-2 w-80 bg-white rounded-lg shadow-lg py-2">
               <!-- Notification Items -->
             </div>
           </div>
           <!-- Profile -->
           <div class="relative">
             <button @click="toggleProfile" 
                     class="w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center">
               {{ userStore.user?.username?.[0] }}
             </button>
             <!-- Profile Dropdown -->
             <div v-if="showProfile" 
                  class="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg">
               <router-link to="/profile" 
                          class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                 프로필 설정
               </router-link>
               <div class="border-t border-gray-100"></div>
               <button @click="logout" 
                       class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                 로그아웃
               </button>
             </div>
           </div>
         </div>
       </div>
     </div>
   </header>

   <!-- Main Content -->
   <router-view></router-view>

   <!-- Footer -->
   <footer class="bg-gray-800 text-white py-8">
     <div class="container mx-auto text-center">
       <p>© 2024 DdamDamDa. All rights reserved.</p>
     </div>
   </footer>
 </div>
</template>

<script setup>
import { ref, computed, onMounted  } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const showNotifications = ref(false)
const showProfile = ref(false)
const notificationCount = ref(0)

const navLinks = [
 { path: '/board', name: '게시판' },
 { path: '/group-mate', name: '그룹 메이트' },
 { path: '/diary', name: '다이어리' }
]

const toggleNotifications = () => {
 showNotifications.value = !showNotifications.value
 showProfile.value = false
}

const toggleProfile = () => {
 showProfile.value = !showProfile.value
 showNotifications.value = false
}

const logout = async () => {
 await userStore.logout()
 router.push('/')
}

onMounted(() => {
  userStore.checkAuth()
})
</script>