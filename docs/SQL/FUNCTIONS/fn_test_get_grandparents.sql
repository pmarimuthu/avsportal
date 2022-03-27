CREATE OR REPLACE FUNCTION public.fn_test_get_grandparents()
 RETURNS void
 LANGUAGE plpgsql
AS $function$
	declare
		zrecord record;
		arr_id uuid array;
	
	begin
		for zrecord in select u.* from user_01 u 
		loop 
			if zrecord.id is not null then
				arr_id := fn_get_grandparents(zrecord.id);
			end if;
		end loop;
		
	end
;
$function$
;
