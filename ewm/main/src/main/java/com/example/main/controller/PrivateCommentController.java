package com.example.main.controller;

import com.example.main.dto.FullCommentDto;
import com.example.main.dto.NewCommentDto;
import com.example.main.dto.UpdateCommentDto;
import com.example.main.mapper.CommentMapper;
import com.example.main.repository.model.Comment;
import com.example.main.repository.model.Event;
import com.example.main.repository.model.User;
import com.example.main.service.CommentService;
import com.example.main.service.EventService;
import com.example.main.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
@Validated
public class PrivateCommentController {
    private final CommentMapper commentMapper;
    private final CommentService commentService;
    private final EventService eventService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FullCommentDto create(@PathVariable @Positive Long userId, @RequestBody @Valid NewCommentDto newCommentDto) {
        User author = userService.findById(userId);
        Event event = eventService.findByIdForPublic(newCommentDto.getEvent());
        Comment comment = commentMapper.toComment(author, event, newCommentDto);

        return commentMapper.toFullCommentDto(commentService.create(comment));
    }

    @PatchMapping
    public FullCommentDto update(@PathVariable @Positive Long userId, @RequestBody @Valid UpdateCommentDto updateCommentDto, @RequestParam @Positive Long commentId) {
        User user = userService.findById(userId);
        Comment comment = commentMapper.toComment(commentService.findById(commentId), updateCommentDto);

        return commentMapper.toFullCommentDto(commentService.update(user.getId(), comment));
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive Long userId, @PathVariable @Positive Long commentId) {
        User user = userService.findById(userId);
        Comment comment = commentService.findById(commentId);

        commentService.deleteById(user.getId(), comment);
    }
}
