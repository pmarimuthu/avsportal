CREATE OR REPLACE FUNCTION public.fn_get_grandparents(given_userid uuid)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
	declare
		lv_given_email text;
		lv_user_maritialstatus int4;
	
		lv_arr_head_userid text;
		lv_head_userid uuid;	
		lv_head_email text;
	
		lv_arr_spouse_userid uuid array;
		lv_arr_spouses_userid uuid array;
	
		i int4 := 0;
	
	begin		
		raise notice 'given_userid: %', given_userid;
	
		/* Does User Exists ? */
		if fn_is_user_exists(given_userid) = false then
			raise notice 'user not found. %', given_userid;
			return null;			
		else
			select u1.email into lv_given_email from user_01 u1 where u1.id = given_userid;	
			raise notice 'user found. %', lv_given_email;
		end if;
	
		/* To get all Heads */
		select ufm1.title into lv_user_maritialstatus from user_family_map_15 ufm1 where ufm1.userid = given_userid;
		if lv_user_maritialstatus = 3 then -- SINGLE
			raise notice 'maritalstatus: %', lv_user_maritialstatus;
			select ufm2.family_head_id into lv_arr_head_userid from user_family_map_15 ufm2 where ufm2.userid = given_userid;
		else
			raise notice 'maritalstatus: %', lv_user_maritialstatus;
			select ufm3.parent_family_head_id into lv_arr_head_userid from user_family_map_15 ufm3 where ufm3.userid = given_userid;
		end if;
	
		raise notice 'lv_arr_head_userid: %', lv_arr_head_userid;
	
			
		return null;
	end;
$function$
;
