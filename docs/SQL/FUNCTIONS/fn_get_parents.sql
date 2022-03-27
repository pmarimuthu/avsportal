CREATE OR REPLACE FUNCTION public.fn_get_parents(given_userid uuid)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
	declare 
		the_userid uuid;
		the_user_email text;
		user_marital_status int4;
	
		arr_parent_head_id uuid array;
		arr_parent_spouse_id uuid array;
		arr_parents_id uuid array;

		i int4 := 0;
		n int4 := 0;
	
		zrecord record;
		csvResult text;
	begin
		if given_userid is null then
			return null;
		end if;
	
		select u.id into the_userid 
			from user_01 u 
			inner join user_account_status_03 uas on u.id = uas.userid 
			where uas.userid = given_userid and uas.is_deleted = false;
	
		if the_userid is null then
			return null;
		end if;
		
		select u2.email into the_user_email from user_01 u2 where u2.id = the_userid;
		raise notice 'To find Parents of : [%] [%]', the_user_email, the_userid;
	
		select up.marital_status into user_marital_status from user_01 u inner join user_profile_08 up on u.id = up.userid where u.id = the_userid;
	
		-- [ Appa ] -------------------------------------------------------------------------------------------------
		if user_marital_status = 3 then -- SINGLE
			for zrecord in select ufm1.family_head_id from user_family_map_15 ufm1 where ufm1.title > 1 and ufm1.userid = the_userid
			loop 
				if zrecord.family_head_id is not null then 
					arr_parent_head_id := array_append(arr_parent_head_id, zrecord.family_head_id); -- Appa --
				end if;
			end loop;
		else
			for zrecord in select ufm2.parent_family_head_id from user_family_map_15 ufm2 where ufm2.title < 2 and ufm2.userid = the_userid
			loop 
				if zrecord.parent_family_head_id is not null then
					arr_parent_head_id := array_append(arr_parent_head_id, zrecord.parent_family_head_id); -- Appa --
				end if;
			end loop;
		end if;
		
		if arr_parent_head_id is null or array_length(arr_parent_head_id, 1) = 0 then 
			raise notice 'No Parent Head Found. <eof>';
			return null;
		end if;
	
		-- [ Amma ] -------------------------------------------------------------------------------------------------
		n := array_length(arr_parent_head_id, 1);
		for i in 1..n
		loop
			for zrecord in select ufm3.userid from user_family_map_15 ufm3 where ufm3.title = 1 and ufm3.family_head_id = arr_parent_head_id[i]
			loop 
				if zrecord.userid is not null then 
					arr_parent_spouse_id := array_append(arr_parent_spouse_id, zrecord.userid); -- Amma --
				end if;
			end loop;		
		end loop;
	
		if arr_parent_spouse_id is null or array_length(arr_parent_spouse_id, 1) = 0 then 
			return arr_parent_head_id; -- No Spouse found. Only Heads.
		end if;
	
		-- [ Appa + Amma ] -------------------------------------------------------------------------------------------------
		n := array_length(arr_parent_head_id, 1);
		for i in 1..n
		loop
			if arr_parent_head_id[i] is not null then
				arr_parents_id := array_append(arr_parents_id, arr_parent_head_id[i]);
			end if;
		end loop;
	
		n := array_length(arr_parent_spouse_id, 1);
		for i in 1..n
		loop
			if arr_parent_spouse_id [i] is not null then
				arr_parents_id := array_append(arr_parents_id, arr_parent_spouse_id[i]);
			end if;
		end loop;
		
		if arr_parents_id is null or array_length(arr_parents_id, 1) = 0 then 
			return null;
		end if;
		
		-- raise notice '  Parents [%] + [%] = %', array_length(arr_parent_head_id, 1), array_length(arr_parent_spouse_id, 1), arr_parents_id;
		-- return arr_parents_id;
				
		csvResult := '';
	
		for i in 1..array_length(arr_parents_id, 1)
		loop 
			if length(csvResult) > 0 then 
				csvResult := csvResult || ',';
			end if;
		
			csvResult := csvResult || text(arr_parents_id[i]);
		end loop;
		
		return csvResult;
	
	end;
$function$
;
