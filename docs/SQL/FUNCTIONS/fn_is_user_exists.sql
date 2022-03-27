CREATE OR REPLACE FUNCTION public.fn_is_user_exists(given_userid uuid)
 RETURNS boolean
 LANGUAGE plpgsql
AS $function$
	declare
		found_userid uuid;
	begin
		if given_userid is null then
			return false;
		end if;
	
		select u.id into found_userid from user_01 u 
		 inner join user_account_status_03 uas on u.id = uas.userid 
		where u.id = uas.userid 
		 and uas.userid = given_userid and uas.is_deleted = false
		;
	
		if found_userid is not null then
			return true;
		end if;
		
		return false;		

	END;
$function$
;
