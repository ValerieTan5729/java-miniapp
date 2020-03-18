package com.github.valerie.wx.miniapp.utils.response;

import lombok.Data;

import java.util.List;

@Data
public class RespPageBean {
    private Long total;
    private List<?> data;

    public RespPageBean(Long total, List<?> data) {
        this.total = total;
        this.data = data;
    }
}
