package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%s was not found", id)));
    }

    @Transactional(readOnly = true)
    public List<User> findAll(List<Long> ids, Pageable pageable) {
        return ids == null ? userRepository.findAll(pageable).getContent() : userRepository.findAllByIdIn(ids, pageable);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
