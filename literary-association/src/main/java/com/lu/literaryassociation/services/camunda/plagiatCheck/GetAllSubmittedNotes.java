package com.lu.literaryassociation.services.camunda.plagiatCheck;

import com.lu.literaryassociation.entity.Editor;
import com.lu.literaryassociation.entity.EditorComment;
import com.lu.literaryassociation.repository.IEditorCommentRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GetAllSubmittedNotes implements JavaDelegate {

    @Autowired
    private IEditorCommentRepository iEditorCommentRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // Retrive all editor comments for specific title
        String title = (String) delegateExecution.getVariable("title");
        ArrayList<EditorComment> editorComments = (ArrayList<EditorComment>) iEditorCommentRepository.findByTitle(title);
        String editorCommentsString = "";

        for(EditorComment ec : editorComments){
            System.out.println(
                    ec.getCommentText() + " " + ec.getTitle()
            );
            editorCommentsString = editorCommentsString.concat(ec.getCommentText().concat(";"));
        }

        delegateExecution.setVariable("submittedNotes", editorCommentsString);
    }
}
