package com.bilitech.api.proto.vo;

import com.bilitech.api.core.vo.BaseVo;
import com.bilitech.api.core.vo.FileVo;
import com.bilitech.api.core.vo.TraceableBaseVo;
import lombok.Data;

@Data
public class VersionVo extends TraceableBaseVo{
    private Integer number;

    private String log;

    private ProtoVo proto;

    private FileVo file;

    private String demoPath;


}
