package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInputDto {
    @Min(1)
    private Long userId;
    @Min(1)
    private Long certificateId;
    @Min(0)
    private BigDecimal price;
}
