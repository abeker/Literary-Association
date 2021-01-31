package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.entity.Editor;
import com.lu.literaryassociation.entity.EditorComment;
import com.lu.literaryassociation.repository.IEditorCommentRepository;
import com.lu.literaryassociation.repository.IEditorRepository;
import com.lu.literaryassociation.services.definition.IEditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorCommentService {
    @Autowired
    private IEditorRepository iEditorRepository;
    @Autowired
    private IEditorCommentRepository iEditorCommentRepository;

    public EditorComment saveEditorComment(String comment, String username, String title){
        Editor editor = iEditorRepository.findByUsername(username);
        System.out.println(editor.getUsername() + " " + editor.getEmail() + " " + editor.getFirstName());
        EditorComment editorComment = new EditorComment();
        editorComment.setEditor(editor);
        editorComment.setCommentText(comment);
        editorComment.setTitle(title);
       return  iEditorCommentRepository.save(editorComment);
    }
}
