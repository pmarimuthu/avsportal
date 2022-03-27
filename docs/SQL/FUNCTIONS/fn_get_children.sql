CREATE OR REPLACE FUNCTION public.fn_get_children(given_user_id uuid)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
	declare 
		ufm_title int4;
		user_record record;
		up_marital_status int4;
		ufm_headid uuid;
		csv_children_id text := '';
		lv_head_userid uuid;
	
	begin
		select ufm.title into ufm_title from user_family_map_15 ufm where ufm.userid = given_user_id;
	
		if ufm.title > 1 then -- SON | DAUGHTER
			return null;
		end if;
	
		if ufm_title = 1 then -- SPOUSE
			select ufm.family_head_id into lv_head_userid from user_family_map_15 ufm where ufm.userid = given_user_id;
		end if;
			
	
		for user_record in select u.email, u.id from user_01 u where u.id != lv_head_userid
		loop 
			select up.marital_status into up_marital_status from user_profile_08 up where up.userid = user_record.id;

			ufm_headid := null;
		
			if up_marital_status = 3 then -- SINGLE
				select ufm.family_head_id into ufm_headid from user_family_map_15 ufm where ufm.userid = user_record.id and ufm.family_head_id = lv_head_userid;
			else
				select ufm.parent_family_head_id into ufm_headid from user_family_map_15 ufm where ufm.userid = user_record.id and ufm.parent_family_head_id = lv_head_userid;
			end if;
		
			if ufm_headid is not null and ufm_headid = lv_head_userid then
				if length(csv_children_id) > 0 then
					csv_children_id := csv_children_id || ', ' || user_record.id;
				else 
					csv_children_id := user_record.id;
				end if;
			
			end if;
		
		end loop;
		-- raise notice 'CSV Children_id: %', csv_children_id;
				
		return csv_children_id;

	END;
$function$
;
