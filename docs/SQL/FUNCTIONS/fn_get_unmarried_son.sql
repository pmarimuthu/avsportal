CREATE OR REPLACE FUNCTION public.fn_get_unmarried_son()
 RETURNS text
 LANGUAGE plpgsql
AS $function$
	declare
		csv_unmarried_son text := '';
		ufm_record record;
		i int4 := 0;
		ufm_head_email text;
	
	begin
		raise notice 'To get all unmarried son in the data model:';
		
		for ufm_record 
			in select u.id, u.email, ufm.family_head_id, ufm.parent_family_head_id, ufm.live_status 
				from user_01 u 
				inner join user_family_map_15 ufm on u.id = ufm.userid 
				inner join user_profile_08 up on ufm.userid = up.userid
				inner join user_account_status_03 uas on ufm.userid = uas.userid
				where ufm.title = 2 -- SON
					and up.marital_status = 3 -- SINGLE
					and uas.is_deleted = false
		loop 
			select u2.email into ufm_head_email
			from user_01 u2
			where u2.id = ufm_record.family_head_id;
		
			i := i + 1;
			raise notice '%. % is an Unmarried Son of %', i, ufm_record.email, ufm_head_email;
		
			if length(csv_unmarried_son) > 0 then
				csv_unmarried_son := csv_unmarried_son || ', ';
			end if;
			csv_unmarried_son := csv_unmarried_son || text(ufm_record.id);			
		end loop;
		i := 0;
	
		-- raise notice 'csv_unmarried_son: %', csv_unmarried_son;
	
		return csv_unmarried_son;
		
	end;
$function$
;
