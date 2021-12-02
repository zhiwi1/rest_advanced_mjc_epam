package com.epam.esm.expression;


import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import com.epam.esm.util.UserCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;

@Slf4j
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private final UserService userService;

    public CustomMethodSecurityExpressionRoot(Authentication authentication, UserService userService) {
        super(authentication);
        this.userService = userService;
    }

    public boolean hasPermissionByUserId(Long userId) {
        return hasPermissionInOrders(userId);
    }

    private boolean hasPermissionInOrders(Long userId) {
        if (!ObjectUtils.isEmpty(authentication)) {
            UserDto userDto = UserCreator.createUser(authentication, userService);
            log.info(userDto.toString());
            return userId.equals(userDto.getId());
        }
        return false;
    }


    @Override
    public void setFilterObject(Object filterObject) {
        throw new UnsupportedOperationException("This operation not supported");
    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object returnObject) {
    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }
}