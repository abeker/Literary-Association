package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.entity.Editor;
import com.lu.literaryassociation.entity.Writer;

public interface IEmailService {

    void sendNotificationToEditor(Editor editor, String subject, String title, String genres, String synopsis);

    void sendNotificationToWriter(Writer writer, String subject, String title, String synopsis);

    void sendHandwrite(Writer writer, String subject, String title, String synopsis);

}
