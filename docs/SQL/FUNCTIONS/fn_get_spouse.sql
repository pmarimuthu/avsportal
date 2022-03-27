CREATE OR REPLACE FUNCTION public.fn_get_spouse(given_userid uuid)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
	declare
		user_title int4;
		csv_spouse_id text := '';
		spouse_rec record;
	
	begin
		if false = fn_is_user_exists(given_userid) then
			return null;
		end if;
	
		select ufm.title into user_title from user_family_map_15 ufm where ufm.userid = given_userid;
		
		if user_title = 0 then -- HEAD
			for spouse_rec in select ufm2.userid from user_family_map_15 ufm2 where ufm2.title = 1 and ufm2.family_head_id = given_userid
			loop
				if length(csv_spouse_id) > 0 then
					csv_spouse_id := csv_spouse_id || ',';
				end if;
				csv_spouse_id := csv_spouse_id || spouse_rec.userid;
				
			end loop;
		
		elsif user_title = 1 then -- SPOUSE
			for spouse_rec in select ufm3.userid, ufm3.family_head_id, ufm3.parent_family_head_id from user_family_map_15 ufm3 where ufm3.title = 1 and ufm3.userid = given_userid
			loop
				if length(csv_spouse_id) > 0 then
					csv_spouse_id := csv_spouse_id || ', ';
				end if;
				csv_spouse_id := csv_spouse_id || spouse_rec.family_head_id;
				
			end loop;
		end if;
	
		raise notice 'csv_spouse_id: %', csv_spouse_id;	
		return csv_spouse_id;
	end	
$function$
;
