package org.example.studentsonic.exception;

import lombok.Getter;
import org.example.studentsonic.entity.ErrorCode;
import org.springframework.util.StringUtils;

import java.util.AbstractMap;
import java.util.Map;

// CustomException은 실행중의 예외 상황을 캐치할 수 있어야하니 Exception 중 가장 상위 클래스인 RuntimeException을 상속 받는다.

public class CustomException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;
    private String message;

    // data 필드를 Exception에 가져와서 Response 에 어떻게 설정하느냐 -> InputRestriction 이라는 특정 데이터를 나타내는 값을 가지고 있으니까
    // String Object

    @Getter
    private Map.Entry<String, Object> data;

    // message가 아예 들어오지 않을 경우 예외 메세지나 다른 예외처리를 할 수 있도록 처리
    @Override
    public String getMessage() {
        if (StringUtils.hasLength(errorCode.getMessage())) {
            return this.message;
        }
        return errorCode.getMessage();
    }

    public CustomException(ErrorCode errorCode, String message, Object data) {
        this.errorCode = errorCode;
        this.message = message;
//        this.data = new AbstractMap.SimpleEntry<>(data.getClass().getSimpleName(), data);

        // 하위과제 null 처리
        if (data != null) {
            this.data = new AbstractMap.SimpleEntry<>(data.getClass().getSimpleName(), data);
        } else {
            this.data = null;
        }
    }
}
