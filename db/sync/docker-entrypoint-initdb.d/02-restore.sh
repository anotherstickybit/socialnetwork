#!/bin/sh

export PGPASSWORD="postgres"
psql -U postgres -c "create database sn"
pg_restore -U postgres -d sn -1 /backup/backup