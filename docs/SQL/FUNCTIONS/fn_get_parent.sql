CREATE OR REPLACE FUNCTION public.fn_get_parent(given_userid uuid)
 RETURNS uuid
 LANGUAGE plpgsql
AS $function$
	declare
		user_marital_status int4;
		parent_id UUID;
		
	begin
		select up.marital_status into user_marital_status from user_01 u inner join user_profile_08 up on u.id = up.userid where u.id = given_userid;
	
		if user_marital_status = 3 then
			select ufm.family_head_id into parent_id from user_family_map_15 ufm where ufm.userid = given_userid;
		else
			select ufm2.parent_family_head_id into parent_id from user_family_map_15 ufm2 where ufm2.userid = given_userid;
		end if;
	
		return parent_id;
	end;
$function$
;
