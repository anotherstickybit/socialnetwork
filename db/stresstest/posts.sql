do
$$
    declare temprow record;
    begin
        for temprow in
            select id from sn.public.users
            loop
                for i in 1..100
                    loop
                        insert into sn.public.post(user_id, post_text)
                        values (temprow.id, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lectus mauris ultrices eros in cursus turpis massa. In fermentum et sollicitudin ac orci. Faucibus ornare suspendisse sed nisi lacus sed. Vivamus at augue eget arcu dictum varius. Lobortis scelerisque fermentum dui faucibus in ornare quam. Feugiat in fermentum posuere urna nec tincidunt. Ullamcorper velit sed ullamcorper morbi tincidunt ornare. In hac habitasse platea dictumst quisque sagittis. Felis bibendum ut tristique et. Diam maecenas ultricies mi eget mauris pharetra et ultrices neque.');
                    end loop;
            end loop;
    end;
$$