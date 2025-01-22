package ru.practicum.ewm.comment.model;

import org.mapstruct.Mapper;
import ru.practicum.ewm.comment.dto.FullCommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.dto.UpdateCommentDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class, UserMapper.class})
public abstract class CommentMapper {
    public Comment toComment(User author, Event event, NewCommentDto newCommentDto) {
        return new Comment(
                null,
                newCommentDto.getContent(),
                LocalDateTime.now(),
                null,
                event,
                author
        );
    }

    public Comment toComment(Comment comment, UpdateCommentDto updateCommentDto) {
        return new Comment(
                comment.getId(),
                updateCommentDto.getContent(),
                comment.getCreated(),
                LocalDateTime.now(),
                comment.getEvent(),
                comment.getAuthor()
        );
    }

    public abstract FullCommentDto toFullCommentDto(Comment comment);

    public abstract List<FullCommentDto> toFullCommentDto(List<Comment> comments);
}
