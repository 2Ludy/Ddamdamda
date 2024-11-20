// stores/board.js
import { defineStore } from 'pinia'
import api from '@/api'

export const useBoardStore = defineStore('board', {
 state: () => ({
   boards: [],
   board: null,
   totalPages: 0,
   currentPage: 1,
   comments: []
 }),


 actions: {
   // 게시글 관련
   async getBoards(pageRequest) {
     const response = await api.post('/board/page', pageRequest)
     this.boards = response.data.list
     this.totalPages = response.data.pages
     return response.data
   },

   async getBoard(id) {
     const response = await api.get(`/board/${id}`)
     this.board = response.data
     return response.data
   },

  async createBoard(boardData) {
    try {
      // JWT 토큰에서 추출한 userId를 명시적으로 포함
      const token = localStorage.getItem('token')
      const userId = JSON.parse(atob(token.split('.')[1])).id  // JWT payload에서 id 추출

      const requestData = {
        userId: userId,
        category: boardData.category,
        title: boardData.title,
        content: boardData.content
      }
      
      console.log('서버로 전송되는 데이터:', requestData)
      const response = await api.post('/board', requestData)
      return response.data
    } catch(error) {
      console.error('Create board error:', error)
      throw error
    }
  },

  async updateBoard(boardData) {
    try {
      const requestData = {
        id: boardData.id,
        title: boardData.title,
        content: boardData.content
      }

      console.log('게시글 수정 요청 데이터:', requestData)
      const response = await api.put('/board', requestData)
      return response.data
    } catch (error) {
      console.error('Update board error:', error)
      throw error
    }
  },

  async deleteBoard(boardId) {
    try {
      console.log('게시글 삭제 요청 ID:', boardId)
      const response = await api.delete(`/board/${boardId}`)
      return response.data
    } catch (error) {
      console.error('Delete board error:', error)
      throw error
    }
  },

  // 좋아요 관련
  async likeBoard(boardId) {
    try {
      // JWT 토큰에서 userId 추출
      const token = localStorage.getItem('token')
      const userId = JSON.parse(atob(token.split('.')[1])).id

      const requestData = {
        userId: userId,
        boardId: boardId
      }

      console.log('좋아요 요청 데이터:', requestData)
      const response = await api.post('/likes', requestData)
      await this.getBoard(boardId) // 게시글 정보 새로고침 (좋아요 수 업데이트)
      return response.data
    } catch (error) {
      console.error('Like board error:', error)
      throw error
    }
  },

  async unlikeBoard(boardId) {
    try {
      const response = await api.delete('/likes', { data: { boardId: boardId } })
      // 게시글 정보 새로고침
      await this.getBoard(boardId)
      return response.data
    } catch (error) {
      console.error('Unlike board error:', error)
      throw error
    }
  },

  async checkLikeStatus(boardId) {
    try {
      const token = localStorage.getItem('token')
      const userId = JSON.parse(atob(token.split('.')[1])).id

      const requestData = {
        userId: userId,
        boardId: boardId
      }

      console.log('좋아요 상태 체크 요청 데이터:', requestData)
      const response = await api.post('/likes/info', requestData)
      return response.data
    } catch (error) {
      if (error.response?.status === 404 || error.response?.status === 400) {
        return false
      }
      console.error('좋아요 상태 확인 실패:', error)
      return false
    }
  },

  // 댓글 관련
  async getComments(pageRequest) {
    this.comments = [];
    const response = await api.post('/comment/page', pageRequest)
    this.comments = response.data.list
    return response.data
  },

  clearComments() {
    this.comments = []
  },

  async addComment(commentData) {
    try {
      // JWT 토큰에서 userId 추출
      const token = localStorage.getItem('token')
      const userId = JSON.parse(atob(token.split('.')[1])).id

      const requestData = {
        userId: userId,
        boardId: commentData.boardId,
        content: commentData.content
      }
      
      console.log('댓글 전송 데이터:', requestData)
      const response = await api.post('/comment', requestData)
      return response.data
    } catch(error) {
      console.error('Add comment error:', error)
      throw error
    }
  },

   async updateComment(commentData) {
    try {
      const requestData = {
        boardId: commentData.boardId,
        content: commentData.content
      }
      
      console.log('댓글 수정 요청 데이터:', requestData)
      const response = await api.post('/comment', requestData)
      return response.data
    } catch(error) {
      console.error('Update comment error:', error)
      throw error
    }
   },

   async deleteComment(commentId) {
    try {
      console.log('댓글 삭제 요청 ID:', commentId)
      const response = await api.delete(`/comment/${commentId}`)
      return response.data
    } catch (error) {
      console.error('Delete comment error:', error)
      throw error
    }
   },

   // 이미지 관련
   async getBoardImages(boardId) {
     const response = await api.get(`/images/board/${boardId}`)
     return response.data
   },

   async deleteImage(imageId) {
     const response = await api.delete(`/images/${imageId}`)
     return response.data
   }
 }
})