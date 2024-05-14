#!/bin/sh

export PGPASSWORD="postgres"
psql -U postgres -c "create database sn"
pg_restore -U postgres -d sn -1 /backup/backup

psql -U postgres -c "select create distributed_table('dialogue', 'id')"
psql -U postgres -c "select create distributed_table('dialogue_message', 'id')"