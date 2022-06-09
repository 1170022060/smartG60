package com.pingok.vocational.domain.field.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class FieldVo {

    private Integer type;

    private String typeName;

    private List<Map> fieldInfo;
}
