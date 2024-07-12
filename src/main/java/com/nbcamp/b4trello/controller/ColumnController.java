package com.nbcamp.b4trello.controller;

import com.nbcamp.b4trello.dto.ColumnRequestDto;
import com.nbcamp.b4trello.dto.ColumnResponseDto;
import com.nbcamp.b4trello.dto.CommonResponse;
import com.nbcamp.b4trello.dto.ResponseEnum;
import com.nbcamp.b4trello.entity.Column;
import com.nbcamp.b4trello.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/columns")
public class ColumnController {

    @Autowired
    private ColumnService columnService;

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public ResponseEntity<CommonResponse<List<ColumnResponseDto>>> getColumns(@RequestParam Long boardId) {
        List<Column> columns = columnService.getColumns(boardId);
        List<ColumnResponseDto> response = columns.stream().map(ColumnResponseDto::new).toList();
        return ResponseEntity.ok(new CommonResponse<>(ResponseEnum.SUCCESS, response));
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<CommonResponse<ColumnResponseDto>> createColumn(@RequestBody ColumnRequestDto request) {
        Column column = columnService.createColumn(request.getBoardId(), request.getColumnTitle());
        ColumnResponseDto response = new ColumnResponseDto(column);
        return ResponseEntity.ok(new CommonResponse<>(ResponseEnum.CREATED, response));
    }

    @DeleteMapping("/{columnId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<CommonResponse<Void>> deleteColumn(@PathVariable Long columnId) {
        columnService.deleteColumn(columnId);
        return ResponseEntity.ok(new CommonResponse<>(ResponseEnum.NO_CONTENT, null));
    }

    @PutMapping("/sequence")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<CommonResponse<Void>> updateColumnSequence(@RequestParam Long boardId, @RequestBody ColumnRequestDto request) {
        columnService.updateColumnSequence(boardId, request.getColumnIds());
        return ResponseEntity.ok(new CommonResponse<>(ResponseEnum.SUCCESS, null));
    }
}