CREATE OR REPLACE FUNCTION public.fn_auth_user(given_loginid text, given_pwd text)
 RETURNS uuid
 LANGUAGE plpgsql
AS $function$
	DECLARE
		auth_user_id uuid;
	begin
		if given_loginid is null OR given_pwd is null then 
			return null;
		end if;
	
		select u.id into auth_user_id
			from public.user_01 u
			inner join public.user_credential_02 uc on u.id = uc.userid 
			where 
				uc.password = given_pwd 
				and (u.id::text = given_loginid OR u.email = given_loginid)
				;
			
		return auth_user_id;
	END;
$function$
;
