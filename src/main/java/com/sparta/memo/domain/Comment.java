package com.sparta.memo.domain;

import com.sparta.memo.dto.CommentRequestDto;
import com.sparta.memo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private Long memoId;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String username;



    public Comment(CommentRequestDto requestDto,Long memoId) {
        this.memoId = memoId;
        this.comment = requestDto.getComment();
        this.username = requestDto.getUsername();
    }

    public void update(CommentRequestDto requestDto) {
        this.memoId = requestDto.getMemoId();
        this.comment = requestDto.getComment();
    }


}
