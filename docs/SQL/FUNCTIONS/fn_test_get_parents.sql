CREATE OR REPLACE FUNCTION public.fn_test_get_parents()
 RETURNS void
 LANGUAGE plpgsql
AS $function$
	declare
		zrecord record;
		-- arr_id uuid array;
		csvResult text;
		i int4;
	
	begin
		i := 0;
		for zrecord in select u.id from user_01 u 
		loop
			i := i + 1;
			if zrecord.id is not null then
				-- arr_id := fn_get_parents(zrecord.id);
				csvResult := fn_get_parents(zrecord.id);
				raise notice 'csvResult: %', csvResult;
			end if;
		end loop;
		
	end
;
$function$
;
