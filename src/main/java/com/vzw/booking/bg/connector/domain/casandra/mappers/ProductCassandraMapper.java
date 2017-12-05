package com.vzw.booking.bg.connector.domain.casandra.mappers;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.vzw.booking.bg.connector.utils.AbstractMapper;
import com.vzw.booking.bg.connector.domain.casandra.Product;

public class ProductCassandraMapper extends AbstractMapper<Product> {

	/* (non-Javadoc)
	 * @see com.vzw.services.cassandra.api.model.AbstractMapper#getMapper(com.datastax.driver.mapping.MappingManager)
	 */
	@Override
	protected Mapper<Product> getMapper(MappingManager manager) {
		return manager.mapper(Product.class);
	}

}
