package com.epam.esm.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @deprecated
 * because project use datajpa since 3 version
 *
 */
@Deprecated(since = "version 3")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page {

    private int number;
    private int size;
}