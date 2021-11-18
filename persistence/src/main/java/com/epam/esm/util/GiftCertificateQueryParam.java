package com.epam.esm.util;


import com.epam.esm.entity.OrderType;
import com.epam.esm.entity.SortType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Deprecated(since = "version 3")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateQueryParam {
    private String[] tagNames;
    private String name;
    private String description;
    private OrderType orderType;
    private SortType sortType;
}
