package com.patryklikus.ifjv.validators;

import com.patryklikus.ifjv.schemas.ObjectSchema;

public interface JsonObjectValidator {
    /**
     * STEPS: <br/>
     * 1. Should find { char <br/>
     * 2. If found } char checks that object have not any required parameters, otherwise extract key string.
     * 3. Check that the object does not have two fields with the same key <br/>
     * 4. Should find : char <br/>
     * 5. Validate JSON value <br/>
     * 6. If found , char then go to step 2. If found }, return index of next char. <br/>
     * In each step we skip empty chars. If we found something different from what we were looking for we throw exception
     *
     * @param json   which we validate
     * @param i      index from we should start validation
     * @param schema on the basis of which we validate
     * @return index of the first char after the object
     * @throws ValidationException if JSON is invalid
     */
    int validateObject(char[] json, int i, ObjectSchema schema) throws ValidationException;
}
