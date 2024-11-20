package com.ddam.damda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.board.model.Board;
import com.ddam.damda.board.model.service.BoardService;
import com.ddam.damda.common.util.PageRequest;
import com.ddam.damda.images.model.BoardImage;
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
            PageInfo<Board> page = boardService.selectAllBoards(preq);
            if (page != null && !page.getList().isEmpty()) {
                return ResponseEntity.ok(page);
            }
            return ResponseEntity.badRequest()
                .body(new ApiResponse("fail", "게시글이 없습니다.", 400));
        } catch (Exception e) {
            log.error("게시글 목록 조회 실패", e);
            return ResponseEntity.internalServerError()
                .body(new ApiResponse("error", "서버 오류가 발생했습니다.", 500));
        }
    }
	
	@Operation(summary = "board상세보기", description = "board의 id를 이용한 상세보기, id를 넘겨주면 board의 모든 정보를 리턴해줌 (List<BoardImage> 타입의 images 도 담긴당)")
	@GetMapping("/{id}")
    public ResponseEntity<?> selectBoard(@PathVariable int id) {
        try {
            Board board = boardService.selectBoard(id);
            if (board != null) {
                return ResponseEntity.ok(board);
            }
            return ResponseEntity.badRequest()
                .body(new ApiResponse("fail", "게시글을 찾을 수 없습니다.", 400));
        } catch (Exception e) {
            log.error("게시글 조회 실패", e);
            return ResponseEntity.internalServerError()
                .body(new ApiResponse("error", "서버 오류가 발생했습니다.", 500));
        }
    }
	
	@Operation(summary = "board추가", description = "board DTO의 id, userId, category, title, content, (List<BoardImage> 타입의 images도 이용)를 이용하여 board 생성")
	@PostMapping("")
    public ResponseEntity<?> addBoard(
            @RequestPart("board") Board board,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            int result = boardService.insertBoard(board, images);
            if (result > 0) {
                return ResponseEntity.ok()
                    .body(new ApiResponse("success", "게시글이 등록되었습니다.", 200));
            }
            return ResponseEntity.badRequest()
                .body(new ApiResponse("fail", "게시글 등록에 실패했습니다.", 400));
        } catch (Exception e) {
            log.error("게시글 등록 실패", e);
            return ResponseEntity.internalServerError()
                .body(new ApiResponse("error", "서버 오류가 발생했습니다.", 500));
        }
    }
	
	@Operation(summary = "board삭제", description = "board의 id를 이용하여 board를 삭제하는 메서드")
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable int id) {
        try {
            int result = boardService.deleteBoard(id);
            if (result > 0) {
                return ResponseEntity.ok()
                    .body(new ApiResponse("success", "게시글이 삭제되었습니다.", 200));
            }
            return ResponseEntity.badRequest()
                .body(new ApiResponse("fail", "게시글 삭제에 실패했습니다.", 400));
        } catch (Exception e) {
            log.error("게시글 삭제 실패", e);
            return ResponseEntity.internalServerError()
                .body(new ApiResponse("error", "서버 오류가 발생했습니다.", 500));
        }
    }
	
	@Operation(summary = "board 수정", description = "board DTO의 title, content, (List<BoardImage> 타입의 images도 이용)를 이용하여 Update하며 , created_at은 항상 now()로 설정되어있어 받을 필요 X")
	@PutMapping("")
    public ResponseEntity<?> editBoard(
            @RequestPart("board") Board board,
            @RequestPart(value = "images", required = false) List<MultipartFile> newImages,
            @RequestPart(value = "deleteImageIds", required = false) List<Integer> deleteImageIds) {
        try {
            int result = boardService.updateBoard(board, newImages, deleteImageIds);
            if (result > 0) {
                return ResponseEntity.ok()
                    .body(new ApiResponse("success", "게시글이 수정되었습니다.", 200));
            }
            return ResponseEntity.badRequest()
                .body(new ApiResponse("fail", "게시글 수정에 실패했습니다.", 400));
        } catch (Exception e) {
            log.error("게시글 수정 실패", e);
            return ResponseEntity.internalServerError()
                .body(new ApiResponse("error", "서버 오류가 발생했습니다.", 500));
        }
    }
	
	@Operation(summary = "board 조회수 증가", description = "board 의 id를 이용하여 조회수를 증가시키는 메서드")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateViewCount(@PathVariable int id) {
		try {
			int isS = boardService.updateViewCount(id);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "updateViewCount", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "updateViewCount", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "updateViewCount", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
