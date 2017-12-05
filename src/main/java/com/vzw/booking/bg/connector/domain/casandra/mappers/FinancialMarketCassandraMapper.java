package com.vzw.booking.bg.connector.domain.casandra.mappers;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.vzw.booking.bg.connector.utils.AbstractMapper;
import com.vzw.booking.bg.connector.domain.casandra.FinancialMarket;

public class FinancialMarketCassandraMapper extends AbstractMapper<FinancialMarket> {

	/* (non-Javadoc)
	 * @see com.vzw.services.cassandra.api.model.AbstractMapper#getMapper(com.datastax.driver.mapping.MappingManager)
	 */
	@Override
	protected Mapper<FinancialMarket> getMapper(MappingManager manager) {
		return manager.mapper(FinancialMarket.class);
	}

}
