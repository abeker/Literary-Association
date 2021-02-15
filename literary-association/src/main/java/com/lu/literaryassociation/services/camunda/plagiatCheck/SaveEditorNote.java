package com.lu.literaryassociation.services.camunda.plagiatCheck;

import com.lu.literaryassociation.repository.IEditorCommentRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveEditorNote implements JavaDelegate {

    @Autowired
    private IEditorCommentRepository iEditorCommentRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String note = (String)delegateExecution.getVariable("notes");
        String editorUsername = (String)delegateExecution.getVariable("writerComparing");
        System.out.println(note + "" + editorUsername);
    }
}
