CREATE OR REPLACE FUNCTION public.fn_arr_uuid_to_csv(given_arr uuid[])
 RETURNS text
 LANGUAGE plpgsql
AS $function$
	declare 
		csv_uuidtext text;
		i int4 := 0;
		n int4 := 0;
	begin
		if given_arr is null or array_length(given_arr, 1) = 0 then 
			return null;
		end if;
		
		n := array_length(given_arr, 1);
		for i in 1..n
		loop
			if length(csv_uuidtext) > 0 then 
				csv_uuidtext := csv_uuidtext || ', ';
			end if;		
			csv_uuidtext := csv_uuidtext || text(given_arr[i]);
		end loop;

		return csv_uuidtext;
	end;
$function$
;
