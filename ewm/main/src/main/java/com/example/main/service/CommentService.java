package com.example.main.service;

import com.example.main.exception.NotFoundException;
import com.example.main.exception.ValidationException;
import com.example.main.repository.CommentRepository;
import com.example.main.repository.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment update(Long userId, Comment comment) {
        if (!userId.equals(comment.getAuthor().getId())) {
            throw new ValidationException(String.format("User with id=%s is not author of comment with id=%s", userId, comment.getId()));
        }
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%s was not found", id)));
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllByEventId(Long eventId, Pageable pageable) {
        return commentRepository.findAllByEventId(eventId, pageable);
    }

    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void deleteById(Long userId, Comment comment) {
        if (!userId.equals(comment.getAuthor().getId())) {
            throw new ValidationException(String.format("User with id=%s is not author of comment with id=%s", userId, comment.getId()));
        }
        deleteById(comment.getId());
    }
}
