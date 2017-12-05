package com.vzw.booking.bg.connector.domain.casandra.mappers;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.vzw.booking.bg.connector.utils.AbstractMapper;
import com.vzw.booking.bg.connector.domain.casandra.DataEvent;

public class DataEventCassandraMapper extends AbstractMapper<DataEvent> {

	/* (non-Javadoc)
	 * @see com.vzw.services.cassandra.api.model.AbstractMapper#getMapper(com.datastax.driver.mapping.MappingManager)
	 */
	@Override
	protected Mapper<DataEvent> getMapper(MappingManager manager) {
		return manager.mapper(DataEvent.class);
	}

}
