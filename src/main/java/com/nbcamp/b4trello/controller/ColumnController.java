package com.nbcamp.b4trello.controller;

import com.nbcamp.b4trello.entity.Column;
import com.nbcamp.b4trello.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/columns")
public class ColumnController {

    @Autowired
    private ColumnService columnService;

    @GetMapping
    public ResponseEntity<List<ColumnResponse>> getColumns(@RequestParam Long boardId) {
        List<Column> columns = columnService.getColumns(boardId);
        return ResponseEntity.ok(columns.stream().map(ColumnResponse::new).toList());
    }
}
