package com.staxter.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UserRole implements Serializable {

    Long userRoleId;
    String userRoleName;
    private Collection<UserPermission> userPermission;
}
