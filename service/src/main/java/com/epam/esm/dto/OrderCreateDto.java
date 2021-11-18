package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {
    private Long idOfUser;
    @NotEmpty
    private Long[] certificateId;
    @Min(0)
    private BigDecimal price;
}
