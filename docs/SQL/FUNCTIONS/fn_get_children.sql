
CREATE OR REPLACE FUNCTION public.fn_get_children(given_user_id uuid)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
	declare 
		ufm_title int4;
		user_record record;
		up_marital_status int4;
		ufm_headid uuid;
		children_id text;
	
	begin
		select ufm.title into ufm_title from user_family_map_15 ufm where ufm.userid = given_user_id;
	
		if ufm_title = 1 then 
			select ufm.family_head_id into given_user_id from user_family_map_15 ufm where ufm.userid = given_user_id;
		end if;
			
	
		for user_record in select u.email, u.id from user_01 u where u.id != given_user_id
		loop 
			select up.marital_status into up_marital_status from user_profile_08 up where up.userid = user_record.id;

			ufm_headid := null;
		
			if up_marital_status = 3 then -- SINGLE
				select ufm.family_head_id into ufm_headid from user_family_map_15 ufm where ufm.userid = user_record.id and ufm.family_head_id = given_user_id;
			else
				select ufm.parent_family_head_id into ufm_headid from user_family_map_15 ufm where ufm.userid = user_record.id and ufm.parent_family_head_id = given_user_id;
			end if;
		
			if ufm_headid is not null and ufm_headid = given_user_id then
				if length(children_id) > 0 then
					children_id := children_id || ', ' || user_record.id;
				else 
					children_id := user_record.id;
				end if;
			
			end if;
		
		end loop;
		raise notice 'Children_id: %', children_id;
				
		return children_id;

	END;
$function$
;
