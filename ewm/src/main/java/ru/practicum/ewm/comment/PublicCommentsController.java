package ru.practicum.ewm.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.FullCommentDto;
import ru.practicum.ewm.comment.model.CommentMapper;
import ru.practicum.ewm.util.OffsetBasedPageRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.util.Constant.PAGE_DEFAULT_FROM;
import static ru.practicum.ewm.util.Constant.PAGE_DEFAULT_SIZE;

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
