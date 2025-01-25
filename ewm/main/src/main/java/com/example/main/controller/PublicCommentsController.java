package com.example.main.controller;

import com.example.api.dto.FullCommentDto;
import com.example.main.mapper.CommentMapper;
import com.example.main.service.CommentService;
import com.example.main.repository.OffsetBasedPageRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.api.Constant.PAGE_DEFAULT_FROM;
import static com.example.api.Constant.PAGE_DEFAULT_SIZE;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Validated
public class PublicCommentsController {
    private final CommentMapper commentMapper;
    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public FullCommentDto findById(@PathVariable @Positive Long commentId) {
        return commentMapper.toFullCommentDto(commentService.findById(commentId));
    }

    @GetMapping
    public List<FullCommentDto> findAllByEventId(@RequestParam @Positive Long eventId,
                                                 @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
                                                 @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size) {
        Pageable pageable = new OffsetBasedPageRequest(from, size);

        return commentMapper.toFullCommentDto(commentService.findAllByEventId(eventId, pageable));
    }
}
