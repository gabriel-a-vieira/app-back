package com.softix.app_back.permission;

/**
 * LIST covers both the paged listing and a single findById — each module's
 * controller exposes only one "read" concept today, so they share one action.
 */
public enum CrudAction {
    CREATE,
    UPDATE,
    LIST,
    DELETE
}
