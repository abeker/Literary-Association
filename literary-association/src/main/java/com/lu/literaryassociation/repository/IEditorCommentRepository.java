package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.EditorComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface IEditorCommentRepository extends JpaRepository<EditorComment, UUID> {
    List<EditorComment> findByTitle(String title);
}
