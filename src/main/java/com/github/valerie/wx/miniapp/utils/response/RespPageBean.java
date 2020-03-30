package com.github.valerie.wx.miniapp.utils.response;

import lombok.Data;

import java.util.List;

@Data
public class RespPageBean {
    private Object total;
    private List<?> data;

    public RespPageBean(Object total, List<?> data) {
        this.total = total;
        this.data = data;
    }
}
