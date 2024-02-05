package org.example.studentsonic.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

// 들어오는 result의 값을 모르기 때문에 제너릭 사용
// 생성자로 apiResponse를 생성하면서 result 값 등을 생성해주기 때문에 Getter만 사용
@Getter
public class ApiResponse<T> {

    private final Status status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Metadata metadata;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<T> results;

    // error response
    // 객체 상위 클래스인 Object로 선언 -> 어떤 에러 데이터가 들어가야할지 가변적이기 때문에, 가장 바깥쪽에 예외가 던져지게, null이여도 문제가 없게끔
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object data;

    // 정상응답 생성자
    public ApiResponse(List<T> results) {
        this.status = new Status(2000, "OK");
        this.metadata = new Metadata(results.size());
        this.results = results;
    }

    // 에러응답 생성자

    public ApiResponse(int code, String message,Object data) {
        this.status = new Status(code, message);
        this.data = data;
    }


    // Status 와 Metadata는 static inner class 선언
    // 이유 : 필드값이 많지 않다, ApiResponse 안에만 담기는 클래스, 불필요한 파일 생성 X, 쉽게 관리할 수 있음,
    // static으로 선언하는 이유 : 외부 클래스에서 숨은 참조를 저장해야해서 주소값을 따로 저장해야하는데 메모리 누수, 시간 복잡도 같은 것이 늘어나 성능이 떨어질 수 있음,
    // 내부에서 외부에 접근방지

    @Getter
    @AllArgsConstructor
    private static class Status {
        private int code;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    private static class Metadata {
        private int resultCount = 0;
    }


}


