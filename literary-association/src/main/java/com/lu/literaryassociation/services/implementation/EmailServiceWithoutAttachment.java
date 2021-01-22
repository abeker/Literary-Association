package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.config.EmailContext;
import com.lu.literaryassociation.entity.Editor;
import com.lu.literaryassociation.entity.Writer;
import com.lu.literaryassociation.services.definition.IEmailService;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceWithoutAttachment implements IEmailService {

    private final EmailContext _emailContext;

    public EmailServiceWithoutAttachment(EmailContext emailContext) {
        _emailContext = emailContext;
    }

    @Override
    public void sendNotificationToEditor(Editor editor, String subject, String title, String genres, String synopsis) {
        Context context = new Context();
        context.setVariable("name", String.format("%s %s", editor.getFirstName(), editor.getLastName()));
        context.setVariable("title", String.format("%s", title));
        context.setVariable("genres", String.format("%s", genres));
        context.setVariable("synopsis", String.format("%s", synopsis));
        _emailContext.send(editor.getEmail(), subject, "editorNotification", context);
    }

    @Override
    public void sendNotificationToWriter(Writer writer, String subject, String title, String synopsis) {
        Context context = new Context();
        context.setVariable("name", String.format("%s %s", writer.getFirstName(), writer.getLastName()));
        context.setVariable("title", String.format("%s", title));
        context.setVariable("synopsis", String.format("%s", synopsis));
        _emailContext.send(writer.getEmail(), subject, "writerNotification", context);
    }

    @Override
    public void sendHandwrite(Writer writer, String subject, String title, String synopsis) {
        Context context = new Context();
        context.setVariable("name", String.format("%s %s", writer.getFirstName(), writer.getLastName()));
        context.setVariable("title", String.format("%s", title));
        context.setVariable("synopsis", String.format("%s", synopsis));
        _emailContext.send(writer.getEmail(), subject, "sendHandwrite", context);
    }
}
