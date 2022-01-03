package com.bermuda.bermudacase.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("checkedGuids")
public class CheckedGuid {

    public CheckedGuid(String guid, String code, String description) {
        super();
        this.guid = guid;
        this.code = code;
        this.description = description;
    }

    @Id
    private String guid;

    private String code;
    private String description;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("CheckedGuid[guid=%s, code='%s', description='%s']", guid, code,
                             description);
    }

}
