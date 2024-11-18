package com.ddam.damda.controller;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddam.damda.board.model.Board;
import com.ddam.damda.board.model.service.BoardService;
import com.ddam.damda.common.util.PageRequest;
import com.ddam.damda.jwt.model.ApiResponse;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/page")
	public ResponseEntity<?> selectAllBoard(@RequestBody PageRequest preq) {
		try {
			PageInfo<Board> board = boardService.selectAllBoards(preq);
			if(board != null && !board.getList().isEmpty()) return new ResponseEntity<PageInfo<Board>>(board, HttpStatus.OK
					);
			return new ResponseEntity<>(new ApiResponse("No content", "selectAllBoard", 400), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectAllBoard", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> selectBoard(@PathVariable int id) {
		try {
			Board board = boardService.selectBoard(id);
			if(board != null) return new ResponseEntity<Board>(board,HttpStatus.OK);
			return new ResponseEntity<>(new ApiResponse("Not Found", "selectBoard", 400), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectAllBoard", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<?> addBoard(@RequestBody Board board) {
		try {
			int isS = boardService.insertBoard(board);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "addBoard", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "addBoard", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "addBoard", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBoard(@PathVariable int id) {
		try {
			int isS = boardService.deleteBoard(id);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "deleteBoard", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "deleteBoard", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "deleteBoard", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("")
	public ResponseEntity<?> editBoard(@RequestBody Board board) {
		try {
			int isS = boardService.updateBoard(board);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "editBoard", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "editBoard", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "editBoard", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
