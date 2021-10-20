package com.epam.esm.dto;

import com.epam.esm.entity.OrderType;
import com.epam.esm.entity.SortType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateQueryParamDto {

    private String[] tagNames;
    @Size(max=100)
    private String name;
    @Size(max=200)
    private String description;
    private OrderType orderType;
    private SortType sortType;
}
