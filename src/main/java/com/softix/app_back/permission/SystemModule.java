package com.softix.app_back.permission;

/**
 * A registrable feature/module of the "cadastro" that role permissions can be
 * scoped to. Adding a new module = add a constant here + annotate the new
 * controller's endpoints with {@link RequiresPermission} — no other wiring.
 */
public enum SystemModule {
    CLIENT,
    PROFESSIONAL,
    SERVICE_OFFERING,
    AVAILABILITY,
    PRODUCT,
    USER,
    APPOINTMENT
}
