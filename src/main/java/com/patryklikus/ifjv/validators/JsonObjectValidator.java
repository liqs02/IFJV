/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.models.ObjectSchema;

public interface JsonObjectValidator {
    /**
     * STEPS: <br/>
     * 1. Should find { char <br/>
     * 2. If found } char checks that object have not any required parameters.
     * 3. Extract key string.
     * 4. Check that the object does not have two fields with the same key <br/>
     * 5. Should find : char <br/>
     * 6. Validate JSON value <br/>
     * 7. If found , char then go to step 2. If found }, return index of next char. <br/>
     * In each step we skip empty chars. If we found something different from what we were looking for we throw exception
     *
     * @param json   which we validate
     * @param i      index from we should start validation
     * @param schema on the basis of which we validate
     * @return index of the first char after the object
     * @throws JsonValidationException if JSON is invalid
     */
    int validate(char[] json, int i, ObjectSchema schema) throws JsonValidationException;
}
