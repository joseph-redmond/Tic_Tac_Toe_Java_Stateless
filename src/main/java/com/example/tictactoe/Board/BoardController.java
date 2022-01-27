package com.example.tictactoe.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class BoardController {
    private final BoardService boardService;
    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    @GetMapping
    @ResponseBody
    public String getNextMove(@RequestParam String board) {
            char[][] inputMatrix = boardService.convertStringToSquareMatrix(board);
            return boardService.placePieceForPerfectMove(inputMatrix);
        }




}
