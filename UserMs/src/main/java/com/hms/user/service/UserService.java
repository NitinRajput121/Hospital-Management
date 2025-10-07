package com.hms.user.service;

import com.hms.user.dto.UserLoginRequest;
import com.hms.user.dto.UserRequestDto;
import com.hms.user.dto.UserResposeDto;
import com.hms.user.dto.UserTokenResponse;

public interface UserService {

     UserTokenResponse registerUser(UserRequestDto userRequestDto);

     UserTokenResponse login(UserLoginRequest userLoginRequest);

     UserResposeDto getUser(Long id);


}
