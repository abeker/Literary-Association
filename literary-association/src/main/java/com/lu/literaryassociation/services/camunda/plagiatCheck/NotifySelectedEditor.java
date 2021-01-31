package com.lu.literaryassociation.services.camunda.plagiatCheck;

import com.lu.literaryassociation.entity.Editor;
import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.repository.IEditorRepository;
import com.lu.literaryassociation.repository.IUserRepository;
import com.lu.literaryassociation.services.implementation.EditorService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifySelectedEditor implements JavaDelegate {
    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String editorsToCompare = (String)delegateExecution.getVariable("editorToCompare");
        String[] editors = editorsToCompare.split(";");
        if(editors.length == 0) {
            return;
        }

        for(String e: editors){
            User editor = iUserRepository.findOneByUsername(e);
            if(editor!=null){
                System.out.println(editor.getEmail() + " " + editor.getUsername());
            }
        }
        delegateExecution.setVariable("editorsRequestedToReview",editors);
        delegateExecution.setVariable("editorsRequestedToReviewLength",editors.length);

    }
}
