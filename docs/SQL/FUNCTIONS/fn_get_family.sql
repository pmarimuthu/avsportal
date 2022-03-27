CREATE OR REPLACE FUNCTION public.fn_get_family(given_userid uuid)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
	declare 
		csv_result text := '';
		lv_given_email text;
		lv_user_maritialstatus int4;
	
		lv_arr_headid uuid array;
		lv_arr_spouseid uuid array;
		lv_arr_parentsid uuid array;
	
		i int4 := 0;		
	
	begin
		if fn_is_user_exists(given_userid) = false then
			raise notice 'user not found. %', given_userid;
			return null;			
		else
			select u1.email into lv_given_email from user_01 u1 where u1.id = given_userid;
			raise notice 'user found. %', lv_given_email;
		end if;
		
		/* To get all Heads */
		select ufm1.title into lv_user_maritialstatus from user_family_map_15 ufm1 where ufm1.userid = given_userid;
		if lv_user_maritialstatus = 3 then --SINGLE
			select ufm2.family_head_id into lv_arr_headid from user_family_map_15 ufm2 where ufm2.title > 1 and ufm2.userid = given_userid;
		else 
			select ufm3.parent_family_head_id into lv_arr_headid from user_family_map_15 ufm3 where ufm3.title < 2 and ufm3.userid = given_userid;
		end if;
	
		raise notice 'Heads in Family: %', lv_arr_headid;
	
		for i in 1..array_length(lv_arr_headid, 1)
		loop
			select ufm3.parent_family_head_id into lv_arr_spouseid from user_family_map_15 ufm3 where ufm3.title = 1 and ufm3.userid = lv_arr_headid[i];
		end loop;
	
		raise notice 'Spouse in Family: %', lv_arr_spouseid;
	
		for i in 1..array_length(lv_arr_headid)	
		loop 
			lv_arr_parentsid := array_append(lv_arr_parentsid, lv_arr_headid[i]);
		end loop;
	
		for i in 1..array_length(lv_arr_spouseid)	
		loop 
			lv_arr_parentsid := array_append(lv_arr_parentsid, lv_arr_spouseid[i]);
		end loop;
		
		for i in 1..array_length(lv_arr_parentsid)
		loop 
			if length(csv_result) > 0 then 
				csv_result := csv_result || ', ';
			end if;
			csv_result := csv_result || lv_arr_parentsid[i]; 
		end loop;

		return csv_result;
	end;
$function$
;
