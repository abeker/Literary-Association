package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.request.FormFieldsDto;
import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import com.lu.literaryassociation.entity.*;
import com.lu.literaryassociation.repository.*;
import com.lu.literaryassociation.services.definition.IEditorService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.*;

@SuppressWarnings({"DuplicatedCode", "UnusedReturnValue"})
@Service
public class EditorService implements IEditorService {

    private final TaskService _taskService;
    private final RuntimeService _runtimeService;
    private final FormService _formService;
    private final IEditorRepository _editorRepository;
    private final IUserRepository _userRepository;
    private final IEditorCommentRepository _editorCommentRepository;
    private final IWriterRepository _writerRepository;
    private final IBookRequestRepository _bookRequestRepository;

    public EditorService(TaskService taskService, RuntimeService runtimeService, FormService formService, IEditorRepository editorRepository, IUserRepository userRepository, IEditorCommentRepository editorCommentRepository, IWriterRepository writerRepository, IBookRequestRepository bookRequestRepository) {
        _taskService = taskService;
        _runtimeService = runtimeService;
        _formService = formService;
        _editorRepository = editorRepository;
        _userRepository = userRepository;
        _editorCommentRepository = editorCommentRepository;
        _writerRepository = writerRepository;
        _bookRequestRepository = bookRequestRepository;
    }

    @Override
    public FormFieldsDto getFormFieldsForReview(String processInstanceId) {
        System.out.println("PROCES: " + processInstanceId);
        Task task = _taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);

        BookRequest bookRequest = getBookRequestFromProcess(processInstanceId);
        TaskFormData tfd = _formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            switch (fp.getId()) {
                case "title": fp.getProperties().put("value", bookRequest.getTitle());
                break;
                case "synopsis": fp.getProperties().put("value", bookRequest.getSynopsis());
                break;
                case "writer": fp.getProperties().put("value", getWriterNamesToString(bookRequest.getWriters()));
                break;
            }
        }

        return new FormFieldsDto(task.getId(),processInstanceId, properties);
    }

    @Override
    public void submitWriterBookRequest(List<FormSubmissionDto> submitedFields, String processInstanceId, String reason) {
        updateBookRequest(getBooleanFromFields(submitedFields, "isApproved"), processInstanceId, reason);
        HashMap<String, Object> map = mapListToDto(submitedFields);

        Task task = _taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
        _formService.submitTaskForm(task.getId(), map);
    }

    @Override
    public FormFieldsDto getPlagiatForm(String processInstanceId) {
        System.out.println("PROCES: " + processInstanceId);
        Object hasPlagiatFormRetrieved = _runtimeService.getVariable(processInstanceId, "isPlagiat");
        if(hasPlagiatFormRetrieved == null) {
            Task task = _taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);

            BookRequest bookRequest = getBookRequestFromProcess(processInstanceId);
            TaskFormData tfd = _formService.getTaskFormData(task.getId());
            List<FormField> properties = tfd.getFormFields();
            for(FormField fp : properties) {
                if(fp.getId().equals("writer")) {
                    fp.getProperties().put("writer", getWriterNameFromProccess(processInstanceId));
                }
            }

            return new FormFieldsDto(task.getId(),processInstanceId, properties);
        }
        return null;
    }

    @Override
    public void submitPlagiarismCheck(List<FormSubmissionDto> submitedFields, String processInstanceId, String reason, boolean sendToBetaReaders) {
        updateHandwrite(getBooleanFromFields(submitedFields, "isPlagiat"), processInstanceId, reason, sendToBetaReaders);
        HashMap<String, Object> map = mapListToDto(submitedFields);

        Task task = _taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
        _formService.submitTaskForm(task.getId(), map);
    }

    private BookRequest updateHandwrite(boolean isPlagiat, String processInstanceId, String reason, boolean sendToBetaReaders) {
        String requestBookId = (String)_runtimeService.getVariable(processInstanceId, "requestBookId");
        _runtimeService.setVariable(processInstanceId, "sendToBetaReaders", sendToBetaReaders);
        _runtimeService.setVariable(processInstanceId, "isPlagiat", isPlagiat);
        Optional<BookRequest> bookRequestOptional = _bookRequestRepository.findById(UUID.fromString(requestBookId));
        if(bookRequestOptional.isPresent()) {
            BookRequest bookRequest = bookRequestOptional.get();
            bookRequest.setApproved(!isPlagiat);
            if(!reason.equals("no_reason")) {
                EditorComment editorComment = createEditorComment(processInstanceId, reason);
                bookRequest.setEditorComment(editorComment);
            }
            return _bookRequestRepository.save(bookRequest);
        }
        return null;
    }

    private String getWriterNameFromProccess(String processInstanceId) {
        String writerUsername = (String)_runtimeService.getVariable(processInstanceId, "writerUsername");
        Writer writer = _writerRepository.findOneByUsername(writerUsername);
        if(writer != null &&
            !writer.isDeleted()) {
            return writer.getFirstName() + " " + writer.getLastName();
        }
        return null;
    }

    private boolean getBooleanFromFields(List<FormSubmissionDto> submitedFields, String fieldName) {
        for (FormSubmissionDto field : submitedFields) {
            if(field.getFieldId().equals(fieldName)) {
                return Boolean.parseBoolean(field.getFieldValue());
            }
        }
        return false;
    }

    private BookRequest updateBookRequest(boolean isApproved, String processInstanceId, String reason) {
        String requestBookId = (String)_runtimeService.getVariable(processInstanceId, "requestBookId");
        _runtimeService.setVariable(processInstanceId, "isApprovedBookRequest", isApproved);
        Optional<BookRequest> bookRequestOptional = _bookRequestRepository.findById(UUID.fromString(requestBookId));
        if(bookRequestOptional.isPresent()) {
            BookRequest bookRequest = bookRequestOptional.get();
            bookRequest.setApproved(isApproved);
            if(!reason.equals("no_reason")) {
                EditorComment editorComment = createEditorComment(processInstanceId, reason);
                bookRequest.setEditorComment(editorComment);
            }
            return _bookRequestRepository.save(bookRequest);
        }
        return null;
    }

    private EditorComment createEditorComment(String processInstanceId, String reason) {
        EditorComment editorComment = new EditorComment();
        editorComment.setCommentText(reason);
        editorComment.setEditor(getMainEditor(processInstanceId));
        return _editorCommentRepository.save(editorComment);
    }

    private Editor getMainEditor(String processInstanceId) {
        String editorUsername = (String)_runtimeService.getVariable(processInstanceId, "mainEditor");
        User userEditor = _userRepository.findOneByUsername(editorUsername);
        if(userEditor != null) {
            Optional<Editor> editor = _editorRepository.findById(userEditor.getId()).filter(editor1 -> !editor1.isDeleted());
            if(editor.isPresent()) {
                return editor.get();
            }
        }
        return null;
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> submitedFields) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : submitedFields){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }
        return map;
    }

    private BookRequest getBookRequestFromProcess(String processInstanceId) {
        String bookRequestId = (String)_runtimeService.getVariable(processInstanceId, "requestBookId");
        Optional<BookRequest> bookRequestOptional = _bookRequestRepository.findById(UUID.fromString(bookRequestId));
        return bookRequestOptional.orElse(null);
    }

    private String getWriterNamesToString(Set<Writer> writers) {
        StringBuilder retString = new StringBuilder();
        for (Writer writer : writers) {
            retString.append(writer.getFirstName()).append(" ").append(writer.getLastName());
        }
        return retString.toString();
    }

}
