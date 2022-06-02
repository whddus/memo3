package com.sparta.memo.service;

import com.sparta.memo.domain.Comment;
import com.sparta.memo.dto.CommentRequestDto;
import com.sparta.memo.repository.CommentRepository;
import com.sparta.memo.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long update(Long id, CommentRequestDto requestDto){
        Optional<String> currentUsername = SecurityUtil.getCurrentUsername();
        if (currentUsername.isPresent() && currentUsername.get().equals("anonymousUser")) {
            throw new RuntimeException("로그인이 필요한 기능입니다.");
        }
        Comment comment = commentRepository.findByIdAndMemoId(id,requestDto.getMemoId());
        if(comment == null){
            throw new IllegalArgumentException("없는 메모입니다.");
        }
        if (!currentUsername.get().equals(comment.getUsername())) {
            throw new IllegalArgumentException("본인의 댓글만 수정할 수 있습니다.");
        }

        comment.update(requestDto);
        return id;
    }

    @Transactional
    public Long delete(Long id, CommentRequestDto requestDto){
        Optional<String> currentUsername = SecurityUtil.getCurrentUsername();
        if (currentUsername.isPresent() && currentUsername.get().equals("anonymousUser")) {
            throw new RuntimeException("로그인이 필요한 기능입니다.");
        }
        Comment comment = commentRepository.findByIdAndMemoId(id, requestDto.getMemoId());
        if(comment == null){
            throw new IllegalArgumentException("없는메모입니다.");
        }
        if (!currentUsername.get().equals(comment.getUsername())) {
            throw new IllegalArgumentException("본인의 댓글만 수정할 수 있습니다.");
        }
        commentRepository.deleteById(id);

        return id;
    }

}
