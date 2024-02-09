package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.Schema;

public interface JsonValidator {
    boolean validate(char[] json, Schema schema);
}
