CREATE OR REPLACE FUNCTION public.fn_auth_user(loginid text, pwd text)
 RETURNS uuid
 LANGUAGE plpgsql
AS $function$
	DECLARE
		auth_user_id uuid;
	BEGIN
		select u.id into auth_user_id
			from public.user_01 u
			inner join public.user_credential_02 uc on u.id = uc.userid 
			where 
				uc.password = pwd and u.id::text = loginid or u.email = loginid
				;
			
		return auth_user_id;
	END;
$function$
;
