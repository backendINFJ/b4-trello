package com.nbcamp.b4trello.controller;

import com.nbcamp.b4trello.dto.ColumnRequestDto;
import com.nbcamp.b4trello.dto.ColumnResponseDto;
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
    public ResponseEntity<List<ColumnResponseDto>> getColumns(@RequestParam Long boardId) {
        List<Column> columns = columnService.getColumns(boardId);
        return ResponseEntity.ok(columns.stream().map(ColumnResponseDto::new).toList());
    }

    @PostMapping
    public ResponseEntity<ColumnResponseDto> createColumn(@RequestBody ColumnRequestDto request) {
        Column column = columnService.createColumn(request.getBoardId(), request.getColumnTitle());
        return ResponseEntity.ok(new ColumnResponseDto(column));
    }

    @DeleteMapping("/{columnId}")
    public ResponseEntity<Void> deleteColumn(@PathVariable Long columnId) {
        columnService.deleteColumn(columnId);
        return ResponseEntity.noContent().build();
    }
}