package com.lu.literaryassociation.dto.response;

import com.lu.literaryassociation.entity.LiteraryUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private LiteraryUser literaryUser;
}
