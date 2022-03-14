CREATE OR REPLACE FUNCTION public.fn_get_spouse(given_userid uuid)
 RETURNS uuid
 LANGUAGE plpgsql
AS $function$
	declare
		user_title int4;
		head_spouse_id uuid;
		message text;
	
	begin
		select ufm.title into user_title from user_family_map_15 ufm where ufm.userid = given_userid;
	
		if user_title = 0 then -- HEAD
			select ufm.userid into head_spouse_id from user_family_map_15 ufm where ufm.title = 1 and ufm.family_head_id = given_userid;
			message = 'Spouse for Given Head';
		
		elsif user_title = 1 then -- SPOUSE
			select ufm3.family_head_id into head_spouse_id from user_family_map_15 ufm3 where ufm3.userid = given_userid;
			message = 'Head for Given Spouse';
		end if;
	
		RETURN head_spouse_id;
	END;
$function$
;
