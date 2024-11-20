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

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/board")
//@CrossOrigin("*")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Operation(summary = "board리스트", description = "board리스트를 페이지 네이션으로 가져오는 메서드, PageRequest DTO 에서 pageNum, pageSize를 기반으로 페이지 네이션을 반환하며, search(내용)이 null 이 아니라면 매칭되는 title, content로 정보를 찾고, orderBy(정렬 기준 테이블명)가 null이 아니라면 orderDir(DESC OR ASC)으로 정렬함")
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
	
	@Operation(summary = "board상세보기", description = "board의 id를 이용한 상세보기, id를 넘겨주면 board의 모든 정보를 리턴해줌, 해당 작업 수행 시 조회수+1 메서드도 실행됨")
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
	
	@Operation(summary = "board추가", description = "board DTO의 id, userId, category, title, content를 이용하여 board 생성")
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
	
	@Operation(summary = "board추가", description = "board의 id를 이용하여 board를 삭제하는 메서드")
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
	
	@Operation(summary = "board 수정", description = "board DTO의 title, content를 이용하여 Update하며 , created_at은 항상 now()로 설정되어있어 받을 필요 X")
	@PutMapping("")
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
