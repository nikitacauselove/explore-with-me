package ru.practicum.ewm.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.FullCommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.dto.UpdateCommentDto;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.model.CommentMapper;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.UserService;
import ru.practicum.ewm.user.model.User;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

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
