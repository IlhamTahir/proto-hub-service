package com.bilitech.api.proto.vo;

import com.bilitech.api.core.vo.BaseVo;
import lombok.Data;

@Data
public class StageVo extends BaseVo {
    private String title;

    private String code;

    private Integer sort;
}
