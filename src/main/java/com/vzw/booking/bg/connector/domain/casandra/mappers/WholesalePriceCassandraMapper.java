package com.vzw.booking.bg.connector.domain.casandra.mappers;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.vzw.booking.bg.connector.utils.AbstractMapper;
import com.vzw.booking.bg.connector.domain.casandra.WholesalePrice;

public class WholesalePriceCassandraMapper extends AbstractMapper<WholesalePrice> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vzw.services.cassandra.api.model.AbstractMapper#getMapper(com.
	 * datastax.driver.mapping.MappingManager)
	 */
	@Override
	protected Mapper<WholesalePrice> getMapper(MappingManager manager) {
		return manager.mapper(WholesalePrice.class);
	}

}
