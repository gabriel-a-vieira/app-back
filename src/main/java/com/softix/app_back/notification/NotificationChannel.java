package com.softix.app_back.notification;

/**
 * One delivery channel for a NotificationEvent (email today; WhatsApp/
 * Telegram/etc. later). Mirrors the ExternalAuthStrategy pattern used for
 * social login (com.softix.app_back.auth.external) -- one @Component per
 * provider, auto-discovered by Spring -- except every registered channel
 * runs for a given event instead of resolving to a single one, since a
 * notification can go out on more than one channel at once.
 */
public interface NotificationChannel {

    void send(NotificationEvent event);

}
