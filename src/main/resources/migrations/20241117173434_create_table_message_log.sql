create table message_log
(
    id           bigint,
    user_id      bigint,
    message_text varchar,
    date_time    timestamp without time zone
);