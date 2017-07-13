select * from (
	SELECT e.*, a.City, a.District, a.Ward, a.Address, t.TypeName, d.Bathroom, d.Bedroom,
			d.Cond, d.Description, d.Floor, d.Length, d.Width, d.Longitude, d.Latitude, 
            gpsDistance(d.Latitude, d.Longitude, 10.762639, 106.682027) as Distance
		FROM estate e 
			left join address a on e.AddressID = a.AddressID 
			left join estate_type t on e.EstateTypeID = t.EstateTypeID
			left join estate_detail d on e.EstateID = d.EstateID
		where e.StatusID = 1 
		order by Distance, PostTime desc) as tmp 
where Distance < 4000
limit 0, 6