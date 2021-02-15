package com.lu.literaryassociation.services.camunda.plagiatCheck;

import com.lu.literaryassociation.entity.Editor;
import com.lu.literaryassociation.repository.IEditorRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllEditors implements JavaDelegate {

    @Autowired
    private IEditorRepository iEditorRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<Editor> editorList = iEditorRepository.findAll();
        List<String> editors = new ArrayList<>();
        String editorsString = "";
        for(Editor editor: editorList) {
            System.out.println(editor.getUsername());
            editorsString = editorsString.concat(editor.getUsername().concat(";"));
            editors.add(editor.getUsername());
        }

        delegateExecution.setVariable("editors", editors);
        delegateExecution.setVariable("editorsString", editorsString);
    }
}
