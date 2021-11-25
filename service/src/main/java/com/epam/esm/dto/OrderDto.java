package com.epam.esm.dto;

import com.epam.esm.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> {
    @Min(1)
    private Long id;
    @Min(0)
    private BigDecimal price;
    @JsonProperty("create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime createDate;
    @Min(1)
    private User user;
    @NotEmpty
    private List<Long> certificateId;
}
