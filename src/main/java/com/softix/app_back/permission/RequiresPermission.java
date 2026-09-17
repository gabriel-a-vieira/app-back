package com.softix.app_back.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a controller endpoint as gated by the role permission matrix.
 * Enforced by {@link PermissionAspect}. MASTER_ADMIN always bypasses it.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiresPermission {
    SystemModule module();
    CrudAction action();
}
