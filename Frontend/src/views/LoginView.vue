<template>
    <div class="flex-1 flex items-center justify-center px-6 pt-10">
      <div class="w-full max-w-md">
        <div class="bg-white p-8 rounded-xl shadow-lg my-12">
          <div class="text-center mb-8">
            <h1 class="text-2xl font-bold text-gray-900">로그인</h1>
            <p>DdamDamDa와 함께 건강한 습관을 만들어보세요!</p>
          </div>
  
          <form @submit.prevent="handleLogin">
            <div class="mb-6">
              <label class="block text-sm font-medium text-gray-700 mb-2">이메일</label>
              <input 
                v-model="email" 
                type="email" 
                required
                class="w-full p-3 border rounded-lg"
              />
            </div>
  
            <div class="mb-6">
              <label class="block text-sm font-medium text-gray-700 mb-2">비밀번호</label>
              <input 
                v-model="password" 
                type="password" 
                required
                class="w-full p-3 border rounded-lg"
              />
            </div>
  
            <button 
              type="submit"
              class="w-full py-2 px-4 bg-black text-[#dcff1f] rounded-full hover:bg-gray-800"
            >
              로그인
            </button>
          </form>
  
          <div class="mt-10 text-center">
            <p>아직 계정이 없으신가요? 
              <router-link to="/signup" class="text-blue-600 hover:underline">
                회원가입
              </router-link>
            </p>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import { useUserStore } from '@/stores/user'
  
  const router = useRouter()
  const userStore = useUserStore()
  
  const email = ref('')
  const password = ref('')
  
  const handleLogin = async () => {
    try {
      await userStore.login({
        email : email.value,
        password: password.value
      })
      router.push('/')  // 로그인 성공 시 홈으로 이동
    } catch (error) {
      console.error('Login failed:', error)
      alert('로그인에 실패했습니다.')
    }
  }
  </script>