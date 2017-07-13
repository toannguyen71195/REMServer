Select * from
(Select *,
	 (levenshtein_ratio(FullAddress, 'chung cư')) as Distance
FROM (select e.*, a.City, a.District, a.Ward, a.Address, t.TypeName, d.Bathroom, d.Bedroom,
			d.Cond, d.Description, d.Floor, d.Length, d.Width, d.Longitude, d.Latitude,
			concat(Address, ', ', Ward, ', ', District, ', ', City) as FullAddress
		from address a
			 left join estate e on a.AddressID = e.AddressID
			 left join estate_type t on e.EstateTypeID = t.EstateTypeID
			 left join estate_detail d on e.EstateID = d.EstateID) as tmp1) as tmp2
where (
     MATCH(Name)
	AGAINST('chung cư' IN NATURAL LANGUAGE MODE)
    or MATCH(TypeName)
	AGAINST('chung cư' IN NATURAL LANGUAGE MODE)
    or MATCH(Description)
	AGAINST('chung cư' IN NATURAL LANGUAGE MODE))
	and StatusID = 1
order by Distance desc

