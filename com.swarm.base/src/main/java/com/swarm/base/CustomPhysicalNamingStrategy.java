package com.swarm.base;

import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

public class CustomPhysicalNamingStrategy extends SpringPhysicalNamingStrategy {
	
//	@Override
//	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
//		Identifier identifier = super.toPhysicalTableName(name, jdbcEnvironment);
//		return new Identifier("tb_" + identifier.getText(), identifier.isQuoted());
//	}
	
}
