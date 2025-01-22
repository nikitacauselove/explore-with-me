package ru.practicum.ewm.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;

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
