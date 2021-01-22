package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.request.ReaderRegistration;
import com.lu.literaryassociation.dto.response.CreatedReader;
import com.lu.literaryassociation.entity.BetaReader;
import com.lu.literaryassociation.entity.BetaReaderComment;
import com.lu.literaryassociation.entity.Reader;
import com.lu.literaryassociation.entity.User;

import java.util.List;

public interface IReaderService {

    CreatedReader registration(ReaderRegistration request);

    Reader findReaderByUsername(String username);

    List<String> findBetaReaderInfoByGenre(String genreName);

    boolean isBetaReader(String username);

    BetaReaderComment saveBetaReaderComment(BetaReaderComment betaReaderComment);

    BetaReader updateBetaReader(BetaReader betaReader);

    List<BetaReaderComment> getBetaReaderCommentByProcess(String processId);

}
