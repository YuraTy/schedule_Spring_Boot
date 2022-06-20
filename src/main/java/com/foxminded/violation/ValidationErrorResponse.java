package com.foxminded.violation;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    private List<Violation> list = new ArrayList<>();

    public List<Violation> getViolation() {
        return list;
    }

}
