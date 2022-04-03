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
	
		ufm_record record;
		child_email text;
	
	begin
		
		if fn_is_user_exists(given_user_id) = false then
			return null;
		end if;
	
		select ufm.title into ufm_title from user_family_map_15 ufm where ufm.userid = given_user_id;
		if ufm_title > 1 then -- SON | DAUGHTER
			return null;
		end if;
	
		if ufm_title = 1 then -- SPOUSE
			select ufm.family_head_id into given_user_id from user_family_map_15 ufm where ufm.userid = given_user_id;
		end if;
	
		for ufm_record in select ufm2.userid from user_family_map_15 ufm2 
			where ufm2.family_head_id = given_user_id and ufm2.userid != given_user_id and ufm2.title != 1
		loop
			-- unmarried(son | daughter)
			select u2.email into child_email from user_01 u2 where u2.id = ufm_record.userid;
			if length(csv_children_id) > 0 then
				csv_children_id := csv_children_id || ', ' || ufm_record.userid;
			else 
				csv_children_id := ufm_record.userid;
			end if;
		end loop;
	
		for ufm_record in select ufm3.userid from user_family_map_15 ufm3 
			where ufm3.parent_family_head_id = given_user_id and ufm3.userid != given_user_id
		loop 
			-- married(son | daughter)
			select u3.email into child_email from user_01 u3 where u3.id = ufm_record.userid;
			if length(csv_children_id) > 0 then
				csv_children_id := csv_children_id || ', ' || ufm_record.userid;
			else 
				csv_children_id := ufm_record.userid;
			end if;
		end loop;
	
		-- **********	
		raise notice 'CSV Children_id: %', csv_children_id;
				
		return csv_children_id;

	END;
$function$
;
