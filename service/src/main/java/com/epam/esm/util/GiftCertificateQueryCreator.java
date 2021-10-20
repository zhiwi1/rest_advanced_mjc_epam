package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateQueryParamDto;
import lombok.experimental.UtilityClass;

import java.text.MessageFormat;

@UtilityClass
public class GiftCertificateQueryCreator {
    private static final String TAG_NAME = "tags.name LIKE ''%{0}%''";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String NAME = "certificates.name LIKE ''%{0}%''";
    private static final String DESCRIPTION = "description LIKE ''%{0}%''";
    private static final String GROUP_BY = " GROUP BY certificates.id";

    public static String createQuery(GiftCertificateQueryParamDto giftCertificateQueryParameters) {
        StringBuilder condition = new StringBuilder();
        addName(giftCertificateQueryParameters, condition);
        addDescription(giftCertificateQueryParameters, condition);
        addTagName(giftCertificateQueryParameters, condition);
        condition.append(GROUP_BY);
        addSortType(giftCertificateQueryParameters, condition);
        System.out.println(condition);
        return condition.toString();
    }

    private static void addTagName(GiftCertificateQueryParamDto giftCertificateQueryParameters, StringBuilder condition) {
        if (giftCertificateQueryParameters.getTagNames() != null) {
            for (String tagName:giftCertificateQueryParameters.getTagNames()){
            addOperator(condition);
            condition.append(MessageFormat.format(TAG_NAME, tagName));}
        }
    }


    private static void addName(GiftCertificateQueryParamDto giftCertificateQueryParameters, StringBuilder condition) {
        if (giftCertificateQueryParameters.getName() != null) {
            addOperator(condition);
            condition.append(MessageFormat.format(NAME, giftCertificateQueryParameters.getName()));
        }
    }

    private void addDescription(GiftCertificateQueryParamDto giftCertificateQueryParameters,
                                StringBuilder condition) {
        if (giftCertificateQueryParameters.getDescription() != null) {
            addOperator(condition);
            condition.append(MessageFormat.format(DESCRIPTION, giftCertificateQueryParameters.getDescription()));
        }
    }

    private static void addSortType(GiftCertificateQueryParamDto giftCertificateQueryParameters, StringBuilder condition) {
        if (giftCertificateQueryParameters.getSortType() != null) {
            condition.append(giftCertificateQueryParameters.getSortType().getSqlExpression());
            if (giftCertificateQueryParameters.getSortType() != null) {
                condition.append(giftCertificateQueryParameters.getSortType().getSqlExpression());
            }
        }
    }

    private static void addOperator(StringBuilder condition) {
        condition.append((condition.length() == 0) ? WHERE : AND);
    }
}