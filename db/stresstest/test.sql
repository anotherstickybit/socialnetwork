do $$
    begin
        for i in 1..50000 loop
            insert into sn.public.users(enabled, password) values
                                                               (true, '12345');
            commit;
            end loop;
    end;
    $$