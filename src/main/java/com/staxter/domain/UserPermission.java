package com.staxter.domain;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPermission implements Serializable {

    Long userPermissionId;
    String userPermissionName;

}
