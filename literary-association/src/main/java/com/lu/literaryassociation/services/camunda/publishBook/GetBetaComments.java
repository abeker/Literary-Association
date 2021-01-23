package com.lu.literaryassociation.services.camunda.publishBook;

import com.lu.literaryassociation.entity.BetaReaderComment;
import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.entity.Writer;
import com.lu.literaryassociation.repository.IUserRepository;
import com.lu.literaryassociation.repository.IWriterRepository;
import com.lu.literaryassociation.services.definition.IReaderService;
import com.lu.literaryassociation.services.mail.EmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetBetaComments implements JavaDelegate {

    private final IReaderService iReaderService;
    private final EmailService _emailService;
    private final IUserRepository _userRepository;
    private final IWriterRepository _writerRepository;

    public GetBetaComments(IReaderService iReaderService, EmailService emailService, IUserRepository userRepository, IWriterRepository writerRepository) {
        this.iReaderService = iReaderService;
        _emailService = emailService;
        _userRepository = userRepository;
        _writerRepository = writerRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("preuzimanje beta reader comentara" + delegateExecution.getProcessInstanceId());
        String writerUsername = (String)delegateExecution.getVariable("writerUsername");
        String processId = delegateExecution.getProcessInstanceId();
        List<BetaReaderComment> betaReaderCommentList = iReaderService.getBetaReaderCommentByProcess(processId);

        //kako slati komentare
        _emailService.generalSendEmail(getWriterEmailFromUsername(writerUsername), "Beta Reader Comments", getMailFromComments(betaReaderCommentList));
    }

    private String getMailFromComments(List<BetaReaderComment> betaReaderCommentList) {
        StringBuilder finalText = new StringBuilder();
        for (BetaReaderComment betaReaderComment : betaReaderCommentList) {
            finalText.append(betaReaderComment.getCommentText()).append("\n\n");
        }
        return finalText.toString();
    }

    private String getWriterEmailFromUsername(String writerUsername) {
        User user = _userRepository.findOneByUsername(writerUsername);
        if(user != null) {
            if(!user.isDeleted()) {
                Optional<Writer> retWriter = _writerRepository.findById(user.getId());
                if(retWriter.isPresent()) {
                    return retWriter.get().getEmail();
                }
            }
        }
        return null;
    }

}
