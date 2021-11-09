package com.epam.esm.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Deprecated(since = "November 2021")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page {

    private int number;
    private int size;
}