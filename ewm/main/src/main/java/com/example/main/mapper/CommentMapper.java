package com.example.main.mapper;

import com.example.main.dto.FullCommentDto;
import com.example.main.dto.NewCommentDto;
import com.example.main.dto.UpdateCommentDto;
import com.example.main.repository.model.Comment;
import com.example.main.repository.model.Event;
import com.example.main.repository.model.User;
import org.mapstruct.Mapper;

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
