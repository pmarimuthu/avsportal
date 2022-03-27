CREATE OR REPLACE FUNCTION public.fn_get_grandchildren(given_userid uuid)
 RETURNS text
 LANGUAGE plpgsql
AS $function$
	declare
		csv_result text := '';
		lv_given_email text;
		lv_user_maritialstatus int4;
	
		lv_arr_head_userid uuid array;
		
		
	
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
		if lv_user_maritialstatus = 3 then -- SINGLE
			select ufm2.family_head_id into lv_arr_head_userid from user_family_map_15 ufm2 where ufm2.family_head_id = given_userid;
		else
			select ufm3.parent_family_head_id into lv_arr_head_userid from user_family_map_15 ufm3 where ufm3.parent_family_head_id = given_userid;
		end if;
	
		raise notice 'lv_arr_head_userid: [%] [%]', array_length(lv_arr_head_userid, 1), lv_arr_head_userid; 
	
	
		return csv_result;
	end;
$function$
;
