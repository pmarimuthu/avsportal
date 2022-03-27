CREATE OR REPLACE FUNCTION public.fn_get_heads()
 RETURNS text
 LANGUAGE plpgsql
AS $function$
	declare
		csv_heads text := '';
		ufm_record record;
		i int4 := 0;
		ufm_head_email text;
	
	begin
		raise notice 'To get all heads in the data model:';
		
		for ufm_record 
			in select u.id, u.email, ufm.family_head_id, ufm.live_status 
				from user_01 u 
				inner join user_family_map_15 ufm on u.id = ufm.userid 
				inner join user_profile_08 up on ufm.userid = up.userid
				inner join user_account_status_03 uas on ufm.userid = uas.userid
				where ufm.title = 0 -- HEAD
					and up.marital_status != 3 -- Not-Single
					and uas.is_deleted = false
		loop 
			select u2.email into ufm_head_email
			from user_01 u2
			where u2.id = ufm_record.family_head_id;
		
			i := i + 1;
			raise notice '%. % is an Head', i, ufm_record.email;
		
			if length(csv_heads) > 0 then
				csv_heads := csv_heads || ', ';
			end if;
			csv_heads := csv_heads || text(ufm_record.id);			
		end loop;
		i := 0;
	
		-- raise notice 'csv_heads: %', csv_heads;
	
		return csv_heads;
		
	end;
$function$
;
